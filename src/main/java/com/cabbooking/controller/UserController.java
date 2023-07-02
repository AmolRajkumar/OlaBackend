package com.cabbooking.controller;

import com.cabbooking.exception.UserException;
import com.cabbooking.model.Ride;
import com.cabbooking.model.User;
import com.cabbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping("/{userId}")
    public ResponseEntity<User> findUserByIdHandler(@PathVariable("userId") Integer userId) throws UserException {

        User user = userService.findUserById(userId);

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getReqProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.getReqUserProfile(jwt);

        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/rides/completed")
    public ResponseEntity<List<Ride>> getcompletedRideHandler(@RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.getReqUserProfile(jwt);
        List<Ride> rides = userService.completedRides(user.getId());

        return new ResponseEntity<>(rides, HttpStatus.ACCEPTED);
    }


}
