package dev.kumar.mapmytour.controller;

import dev.kumar.mapmytour.dto.TourDTO;
import dev.kumar.mapmytour.service.ApiKeyService;
import dev.kumar.mapmytour.service.S3Service;
import dev.kumar.mapmytour.service.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Tour Package Management", description = "APIs for managing tour packages")
@SecurityRequirement(name = "X-API-Key")
@RequiredArgsConstructor
public class MainController {
    
    private final ApiKeyService apiKeyService;
    private final TourService tourService;
    private final S3Service s3Service;
    
    @PostMapping("/generate-key")
    public ResponseEntity<Map<String, String>> generateApiKey() {
        String apiKey = apiKeyService.generateApiKey();
        
        Map<String, String> response = new HashMap<>();
        response.put("apiKey", apiKey);
        response.put("message", "API key generated successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Create tour package", description = "Create a new tour package")
    @PostMapping("/tours")
    public ResponseEntity<TourDTO> createTour(@Valid @RequestBody TourDTO tourDTO) {
        TourDTO created = tourService.create(tourDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @Operation(summary = "Get all tours", description = "Get all tour packages or search by location")
    @GetMapping("/tours")
    public ResponseEntity<List<TourDTO>> getAllPackages(
            @RequestParam(value = "location", required = false) String location) {
        List<TourDTO> tours = (location == null || location.isBlank())
                ? tourService.getAllPackages()
                : tourService.searchByLocation(location);
        return ResponseEntity.ok(tours);
    }
    
    @Operation(summary = "Get tour by ID", description = "Get a specific tour package by ID")
    @GetMapping("/tours/{id}")
    public ResponseEntity<TourDTO> getTourById(@PathVariable Integer id) {
        TourDTO tour = tourService.getById(id);
        return ResponseEntity.ok(tour);
    }
    
    @Operation(summary = "Update tour", description = "Update an existing tour package")
    @PutMapping("/tours/{id}")
    public ResponseEntity<TourDTO> updateTour(@PathVariable Integer id,
                                              @Valid @RequestBody TourDTO tourDTO) {
        TourDTO updated = tourService.update(id, tourDTO);
        return ResponseEntity.ok(updated);
    }
    
    @Operation(summary = "Delete tour", description = "Delete a tour package")
    @DeleteMapping("/tours/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable Integer id) {
        tourService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Upload tour image", description = "Upload an image to AWS S3 and get public URL")
    @PostMapping(value = "/upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadImage(
            @Parameter(description = "Image file (JPEG, PNG, GIF only)")
            @RequestParam("file") MultipartFile file) {
        
        String imageUrl = s3Service.uploadImage(file);
        Map<String, String> response = new HashMap<>();
        response.put("imageUrl", imageUrl);
        response.put("message", "Image uploaded successfully");
        
        return ResponseEntity.ok(response);
    }
}
