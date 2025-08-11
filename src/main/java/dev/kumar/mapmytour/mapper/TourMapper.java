package dev.kumar.mapmytour.mapper;

import dev.kumar.mapmytour.dto.TourDTO;
import dev.kumar.mapmytour.model.Tour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TourMapper {
    
    TourDTO toDTO(Tour tour);
    
    Tour toEntity(TourDTO tourDTO);
    
}