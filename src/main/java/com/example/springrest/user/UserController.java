package com.example.springrest.user;

import com.example.springrest.place.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final PlaceService placeService;
    private final UserService userService;

    @Autowired
    public UserController(PlaceService placeService, UserService userService) {
        this.placeService = placeService;
        this.userService = userService;
    }

    @GetMapping
    public Page<UserEntity> getAllUsers(Pageable pageable){
        return userService.getAllUsers(pageable);
    }


    @GetMapping("/{username}")
    public User getUserByName(String username){
        return userService.getUserByName(username);
    }
    /*
    @GetMapping("/places")
    public Page<PlaceDto> getAllUserPlaces(Pageable pageable) {
        return placeService.getAllUserPlaces(pageable);
    }


     */

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable String username){
        userService.deleteUser(username);
    }

}
