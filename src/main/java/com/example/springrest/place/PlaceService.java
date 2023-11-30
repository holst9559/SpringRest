package com.example.springrest.place;

import com.example.springrest.category.CategoryRepository;
import org.geolatte.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final CategoryRepository categoryRepository;


    @Autowired
    public PlaceService(PlaceRepository placeRepository, CategoryRepository categoryRepository){
        this.placeRepository = placeRepository;
        this.categoryRepository = categoryRepository;
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

}
