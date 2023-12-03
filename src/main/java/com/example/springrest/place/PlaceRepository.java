package com.example.springrest.place;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    Page<Place> findAllByCategoryNameAndVisible(String categoryName,boolean visible, Pageable pageable);
    Page<Place> findAllByVisible(boolean visible, Pageable pageable);
    Page<Place> findAllByUserId(String username, Pageable pageable);
    Optional<Place> findByIdAndVisible(Long aLong, boolean visible);
    Optional<Place> findByIdAndUserId(Long aLong, String username);
    Optional<Place> findByNameAndVisible(String name, boolean visible);


    @Query(value = """
            SELECT * FROM place
            WHERE ST_Distance_Sphere(coordinate, :place) < :radius
                """, nativeQuery = true)
    Page<Place> findNearbyPlaces(@Param("place") Point<G2D> place, @Param("radius") double radius, Pageable pageable);

}
