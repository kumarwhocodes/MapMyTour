package dev.kumar.mapmytour.config;

import dev.kumar.mapmytour.service.ApiKeyService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
@RequiredArgsConstructor
public class ApiKeyAuthFilter implements Filter {
    
    private final ApiKeyService apiKeyService;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Skip authentication for certain endpoints
        String requestURI = httpRequest.getRequestURI();
        if (requestURI.equals("/api/generate-key") || requestURI.startsWith("/swagger")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Extract API key from X-API-KEY header
        String apiKey = httpRequest.getHeader("X-API-KEY");
        
        if (apiKey == null || apiKey.trim().isEmpty()) {
            sendUnauthorizedResponse(httpResponse, "Missing X-API-KEY header");
            return;
        }
        
        if (!apiKeyService.isValidApiKey(apiKey)) {
            sendUnauthorizedResponse(httpResponse, "Invalid API key");
            return;
        }
        
        // API key is valid, continue with the request
        chain.doFilter(request, response);
    }
    
    private void sendUnauthorizedResponse(HttpServletResponse response, String message) 
            throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
