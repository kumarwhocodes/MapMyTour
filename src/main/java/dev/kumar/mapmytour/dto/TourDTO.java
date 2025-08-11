package dev.kumar.mapmytour.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourDTO {
    
    private Integer id;
    
    private String image;
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Description is required")
    private String description;
    
    @NotBlank(message = "Duration is required")
    private String duration;
    
    @NotBlank(message = "Actual price is required")
    private String actualPrice;
    
    @NotBlank(message = "Discounted price is required")
    private String discountedPrice;
    
    @NotBlank(message = "Discount percentage is required")
    private String discountInPercentage;
    
    @NotBlank(message = "Location is required")
    private String location;
}
