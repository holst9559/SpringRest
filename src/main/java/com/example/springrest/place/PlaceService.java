package com.example.springrest.place;

import com.example.springrest.exception.AuthorizationException;
import com.example.springrest.exception.InvalidCoordinatesException;
import com.example.springrest.exception.ResourceNotFoundException;
import com.example.springrest.category.Category;
import com.example.springrest.category.CategoryRepository;
import com.example.springrest.category.CategoryService;
import com.example.springrest.utility.CoordinateRequest;
import jakarta.transaction.Transactional;
import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Stream;

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
    }


    public Page<Place> getAllPlaces(Pageable pageable){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<Place> publicPlace = placeRepository.findAllByVisible(true, pageable);

        if(username.equals("anonymousUser")){
            return publicPlace;
        }

        Page<Place> userPlace = placeRepository.findAllByUserId(username, pageable);
        List<Place> mergedList = Stream.concat(userPlace.stream(), publicPlace.stream())
                .distinct()
                .toList();

        Page<Place> payload = PageableExecutionUtils.getPage(
                mergedList,
                pageable,
                () -> userPlace.getTotalElements() + publicPlace.getTotalElements()
        );

        return payload;
    }

    public Place getPlaceById(Long id){
        return placeRepository.findByIdAndVisible(id, true).orElseThrow(() ->
                new ResourceNotFoundException(id.toString()));
    }

    public Place getPlaceByName(String name){
        return placeRepository.findByNameAndVisible(name, true).orElseThrow(() ->
                new ResourceNotFoundException(name));
    }

    public Page<Place> getPlacesByCategoryName(String name, Pageable pageable){
        return placeRepository.findAllByCategoryNameAndVisible(name,true, pageable);
    }


    public Page<Place> getNearbyPlaces(float lon, float lat, double radius, Pageable pageable){
        var geo = Geometries.mkPoint(new G2D(lon, lat), WGS84);
        return placeRepository.findNearbyPlaces(geo, radius, pageable);
    }

    public Place addNewPlace(@Validated PlaceDto place){
        if(place.lat() < -90 || place.lat() > 90 || place.lon() < -180 || place.lon() > 180 ){
            throw new InvalidCoordinatesException(place.lat(), place.lon());
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username.equals("anonymousUser")){ //Redundant då detta fångas redan i SecurityConfig?
            new AuthorizationException(username);
        }

        placeRepository.findByIdAndUserId(id, username).orElseThrow(() ->
                new ResourceNotFoundException(id.toString()));
        categoryRepository.findByName(place.category()).orElseThrow(() ->
                new ResourceNotFoundException(id.toString()));
        Place updatedPlace = Place.of(place);

        return placeRepository.save(updatedPlace);
    }

    @Transactional
    public Place updateCoordinates(@Validated @RequestBody CoordinateRequest coordinateRequest, Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(username.equals("anonymousUser")){ //Redundant då detta fångas redan i SecurityConfig?
            new AuthorizationException(username);
        }

        if(coordinateRequest.lat() < -90 || coordinateRequest.lat() > 90 || coordinateRequest.lon() < -180 || coordinateRequest.lon() > 180 ){
            throw new InvalidCoordinatesException(coordinateRequest.lat(), coordinateRequest.lon());
        }
        var geo = Geometries.mkPoint(new G2D(coordinateRequest.lon(), coordinateRequest.lat()), WGS84);

        Place place = placeRepository.findByIdAndUserId(id, username).orElseThrow(() ->
                new ResourceNotFoundException(id.toString()));
        place.setCoordinate(geo);

        return placeRepository.save(place);
    }

    @Transactional
    public void deletePlace(Long id){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        placeRepository.findByIdAndUserId(id, username).orElseThrow(() ->
                new ResourceNotFoundException(id.toString()));
        placeRepository.deleteById(id);

    }
}
