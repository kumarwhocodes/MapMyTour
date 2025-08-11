package dev.kumar.mapmytour.service;

import dev.kumar.mapmytour.dto.TourDTO;
import dev.kumar.mapmytour.mapper.TourMapper;
import dev.kumar.mapmytour.model.Tour;
import dev.kumar.mapmytour.repo.TourRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourService {
    
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;
    
    @Transactional(readOnly = true)
    public List<TourDTO> getAllPackages() {
        return tourRepository.findAll().stream()
                .map(tourMapper::toDTO)
                .toList();
    }
    
    @Transactional(readOnly = true)
    public TourDTO getById(Integer id) {
        Tour tour = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + id));
        return tourMapper.toDTO(tour);
    }
    
    @Transactional(readOnly = true)
    public List<TourDTO> searchByLocation(String location) {
        return tourRepository.findByLocationContainingIgnoreCase(location).stream()
                .map(tourMapper::toDTO)
                .toList();
    }
    
    @Transactional
    public TourDTO create(TourDTO dto) {
        Tour entity = tourMapper.toEntity(dto);
        Tour saved = tourRepository.save(entity);
        return tourMapper.toDTO(saved);
    }
    
    @Transactional
    public TourDTO update(Integer id, TourDTO dto) {
        Tour existing = tourRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tour not found with id: " + id));
        
        // Update fields
        existing.setImage(dto.getImage());
        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setDuration(dto.getDuration());
        existing.setActualPrice(dto.getActualPrice());
        existing.setDiscountedPrice(dto.getDiscountedPrice());
        existing.setDiscountInPercentage(dto.getDiscountInPercentage());
        existing.setLocation(dto.getLocation());
        
        Tour saved = tourRepository.save(existing);
        return tourMapper.toDTO(saved);
    }
    
    @Transactional
    public void delete(Integer id) {
        if (!tourRepository.existsById(id)) {
            throw new RuntimeException("Tour not found with id: " + id);
        }
        tourRepository.deleteById(id);
    }
}
