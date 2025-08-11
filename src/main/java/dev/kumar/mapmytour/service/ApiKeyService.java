package dev.kumar.mapmytour.service;

import dev.kumar.mapmytour.model.ApiKey;
import dev.kumar.mapmytour.repo.ApiKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiKeyService {
    
    private final ApiKeyRepository apiKeyRepository;
    
    public String generateApiKey() {
        // Generate random 32-character API key
        String keyValue = UUID.randomUUID().toString().replace("-", "") +
                UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        
        ApiKey apiKey = new ApiKey();
        apiKey.setKeyValue(keyValue);
        
        apiKeyRepository.save(apiKey);
        return keyValue;
    }
    
    public boolean isValidApiKey(String keyValue) {
        return apiKeyRepository.findByKeyValue(keyValue).isPresent();
    }
}
