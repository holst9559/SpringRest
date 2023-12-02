package com.example.springrest.place;

import com.example.springrest.category.Category;
import com.example.springrest.category.CategoryRepository;
import com.example.springrest.category.CategoryService;
import com.example.springrest.utility.Point2DSerializer;
import jakarta.transaction.Transactional;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.geolatte.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.sql.SQLOutput;
import java.util.Optional;

import static org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;


    @Autowired
    public PlaceService(PlaceRepository placeRepository, CategoryRepository categoryRepository, CategoryService categoryService){
        this.placeRepository = placeRepository;
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    };

    public Page<Place> getAllPlaces(Pageable pageable){
        return placeRepository.findAll(pageable);
    }

    public Place getPlaceById(long id){
        return placeRepository.findById(id).orElseThrow();
    }

    public Page<Place> getPlacesByCategoryName(String name, Pageable pageable){
        return placeRepository.findAllByCategoryName(name, pageable);
    }

    /*
    public Page<Place> getNearbyPlaces(Point coordinates, Pageable pageable){
        double radius = coordinates.getPosition().getCoordinateDimension();
        System.out.println(radius);


        return placeRepository.findNearbyPlaces(coordinates, radius, pageable);
    }
    */
    public Place addNewPlace(@Validated PlaceDto place){
        if(place.lat() < -90 || place.lat() > 90 || place.lon() < -180 || place.lon() > 180 ){
            throw new IllegalArgumentException("Invalid coordinates");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        System.out.println(categoryService.getCategoryByName(place.category()));
        Category category = categoryService.getCategoryByName(place.category());

        var geo = Geometries.mkPoint(new G2D(place.lon(), place.lat()), WGS84);

        Place placeEntity = new Place();
        placeEntity.setName(place.name());
        placeEntity.setUserId(userName);
        placeEntity.setVisible(place.visible());
        placeEntity.setDescription(place.description());
        placeEntity.setCoordinate(geo);
        category.addPlace(placeEntity);

        return placeRepository.save(placeEntity);
    }

    @Transactional
    public Place updatePlace(@Validated PlaceDto place, Long id){
        placeRepository.findById(id).orElseThrow();
        categoryRepository.findByName(place.category()).orElseThrow();
        Place updatedPlace = Place.of(place);

        return placeRepository.save(updatedPlace);
    }

    @Transactional
    public Place updateCoordinates(@Validated float lon,@Validated  float lat, Long id){
        Place place = placeRepository.findById(id).orElseThrow();
        System.out.println("THIS PLACE");
        System.out.println(place);
        var geo = Geometries.mkPoint(new G2D(lon, lat), WGS84);
        place.setCoordinate(geo);

        return placeRepository.save(place);
    }
}
