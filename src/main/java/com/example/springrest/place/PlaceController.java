package com.example.springrest.place;


import org.geolatte.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/places")
public class PlaceController {

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService){
        this.placeService = placeService;
    }

    @GetMapping
    public Page<Place> getAllPlaces(Pageable pageable){
        return placeService.getAllPlaces(pageable);
    }

    @GetMapping("/{id}")
    public Place getPlaceById(@PathVariable long id){
        return placeService.getPlaceById(id);
    }

    @GetMapping("/category/{categoryName}")
    public Page<Place> getPlacesByCategoryName(@PathVariable String categoryName, Pageable pageable){
        return placeService.getPlacesByCategoryName(categoryName, pageable);
    }

    /*
    @GetMapping("/nearby")
    public Page<Place> getNearbyPlaces(@Validated Point coordinates , Pageable pageable){
        return placeService.getNearbyPlaces(coordinates, pageable);
    }
    */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Place> addNewPlace(@RequestBody @Validated PlaceDto place){
        Place createdPlace = placeService.addNewPlace(place);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPlace.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdPlace);
    }

    @PutMapping("/{id}")
    public void updatePlace(@PathVariable Long id, @RequestBody @Validated Place place){
        placeService.updatePlace(place, id);
    }
}
