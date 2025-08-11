package dev.kumar.mapmytour.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tours")
public class Tour {
    @Id
    private Integer id;
    private String image;
    private String title;
    private String description;
    private String duration;
    private String actualPrice;
    private String discountedPrice;
    private String discountInPercentage;
    private String location;
}
