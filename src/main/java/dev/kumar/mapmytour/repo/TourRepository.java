package dev.kumar.mapmytour.repo;

import dev.kumar.mapmytour.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer> {
    List<Tour> findByLocationContainingIgnoreCase(String location);
}
