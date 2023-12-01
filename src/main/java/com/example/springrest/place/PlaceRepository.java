package com.example.springrest.place;

import org.geolatte.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Page<Place> findAllByCategoryName(String categoryName, Pageable pageable);
    Optional<Place> findByName(String name);
    // Page<Place> findNearbyPlaces(Point coordinates, double radius, Pageable pageable);



}
