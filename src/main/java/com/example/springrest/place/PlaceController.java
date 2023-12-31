package com.example.springrest.place;

import com.example.springrest.utility.CoordinateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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

    @GetMapping("/{id:\\d+}")
    public Place getPlaceById(@PathVariable Long id){
        return placeService.getPlaceById(id);
    }

    @GetMapping("/{name:.*\\D.*}")
    public Place getPlaceByName(@PathVariable String name){
        return placeService.getPlaceByName(name);
    }

    @GetMapping("/category/{categoryName}")
    public Page<Place> getPlacesByCategoryName(@PathVariable String categoryName, Pageable pageable){
        return placeService.getPlacesByCategoryName(categoryName, pageable);
    }

    @GetMapping("/nearby") //Get all nearby places
    public Page<Place> getNearbyPlaces(@RequestParam float lon,
                                       @RequestParam float lat,
                                       @RequestParam double radius,
                                       Pageable pageable){
        return placeService.getNearbyPlaces(lon, lat, radius, pageable);
    }

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

    @PutMapping("/{id:\\d+}") //Update Place
    public ResponseEntity<Place> updatePlace(@PathVariable Long id, @RequestBody @Validated PlaceDto place){
        Place updatedPlace = placeService.updatePlace(place, id);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedPlace.getId())
                .toUri();
        return ResponseEntity.created(location).body(updatedPlace);
    }

    @PatchMapping("/{id:\\d+}") //Update coordinates
    public ResponseEntity<Place> updateLocation(@PathVariable Long id, @RequestBody @Validated CoordinateRequest coordinateRequest){
        Place updatedCoordinates = placeService.updateCoordinates(coordinateRequest, id);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(updatedCoordinates.getId())
                .toUri();
        return ResponseEntity.created(location).body(updatedCoordinates);

    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> deletePlace(@PathVariable Long id){
        placeService.deletePlace(id);
        return ResponseEntity.noContent().build();
    }
}
