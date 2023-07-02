package com.cabbooking.controller;

import com.cabbooking.exception.DriverExcption;
import com.cabbooking.model.Driver;
import com.cabbooking.model.Ride;
import com.cabbooking.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;


    @GetMapping("/profile")
    public ResponseEntity<Driver> getReqDriverProfileHandler(@RequestHeader("Authorization") String jwt) throws DriverExcption {


        Driver driver = driverService.getReqDriverProfile(jwt);


        return new ResponseEntity<Driver>(driver, HttpStatus.OK);
    }


    @GetMapping("/{driverId}/current_ride")
    public ResponseEntity<Ride> getDriversCurrentRideHandler(

            @PathVariable("driverId") Integer driverId) throws DriverExcption {

        Ride ride = driverService.getDriversCurrentRide(driverId);

        return new ResponseEntity<Ride>(ride, HttpStatus.ACCEPTED);


    }


    @GetMapping("/{driverId}/allocated")
    public ResponseEntity<List<Ride>> getAllocatedRidesHandler(@PathVariable("driverId") Integer driverId) throws DriverExcption {


        List<Ride> rides = driverService.getAllocatedRides(driverId);

        return new ResponseEntity<>(rides, HttpStatus.ACCEPTED);


    }

    @GetMapping("/rides/completed")
    public ResponseEntity<List<Ride>> getCompletedRidesHandler(@RequestHeader("Authorization") String jwt) throws DriverExcption {


        Driver driver = driverService.getReqDriverProfile(jwt);

        List<Ride> rides = driverService.completedRides(driver.getId());


        return new ResponseEntity<>(rides, HttpStatus.ACCEPTED);

    }

}
