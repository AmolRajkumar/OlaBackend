package com.cabbooking.controller;

import com.cabbooking.dto.RideDto;
import com.cabbooking.dto.mapper.DtoMapper;
import com.cabbooking.exception.DriverExcption;
import com.cabbooking.exception.RideExcption;
import com.cabbooking.exception.UserException;
import com.cabbooking.model.Driver;
import com.cabbooking.model.Ride;
import com.cabbooking.model.User;
import com.cabbooking.request.RideRequest;
import com.cabbooking.request.StartRideRequest;
import com.cabbooking.response.MessageResponse;
import com.cabbooking.service.DriverService;
import com.cabbooking.service.RideService;
import com.cabbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rides")
public class RideController {


    @Autowired
    private RideService rideService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private UserService userService;


    @PostMapping("/request")
    public ResponseEntity<RideDto> userRequestHandler(@RequestBody RideRequest rideRequest, @RequestHeader("Authorization") String jwt) throws UserException, DriverExcption {

        User user = userService.getReqUserProfile(jwt);

        Ride ride = rideService.requestRide(rideRequest, user);

        RideDto rideDto = DtoMapper.toRideDto(ride);

        return new ResponseEntity<>(rideDto, HttpStatus.ACCEPTED);

    }

    @PutMapping("/{rideId}/accept")
    public ResponseEntity<MessageResponse> acceptRideHandler(@PathVariable("rideId") Integer rideId) throws RideExcption {


        rideService.acceptRide(rideId);

        MessageResponse messageResponse = new MessageResponse("Ride Accepted By Driver");

        return new ResponseEntity<>(messageResponse, HttpStatus.ACCEPTED);


    }


    @PutMapping("/{rideId}/decline")
    public ResponseEntity<MessageResponse> declineRideHandler(@RequestHeader("Authorization") String jwt, @PathVariable("rideId") Integer rideId) throws DriverExcption, RideExcption {

        Driver driver = driverService.getReqDriverProfile(jwt);

        rideService.declineRide(rideId, driver.getId());

        MessageResponse response = new MessageResponse("Ride Decline By Driver");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);


    }


    @PutMapping("/{rideId}/start")
    public ResponseEntity<MessageResponse> rideStartHandler(@PathVariable("rideId") Integer rideId, @RequestBody StartRideRequest req) throws RideExcption {

        rideService.startRide(rideId, req.getOtp());

        MessageResponse response = new MessageResponse("Ride is Started");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    }


    @PutMapping("/{rideId}/complete")
    public ResponseEntity<MessageResponse> rideCompleteHandler(@PathVariable("rideId") Integer rideId) throws RideExcption {


        rideService.completeRide(rideId);
        MessageResponse response = new MessageResponse("Ride is Completed Thank You For Booking Cab");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);


    }

    @PutMapping("/{rideId}/cancel")
    public ResponseEntity<MessageResponse> rideCancleHandler(@PathVariable("rideId") Integer rideId) throws RideExcption {

        rideService.cancelRide(rideId);

        MessageResponse response = new MessageResponse("Cancel Ride SuccessFully");

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);

    }

    @GetMapping("/{rideId}")
    public ResponseEntity<RideDto> findRideByIdHandler(@PathVariable("rideId") Integer rideId, @RequestHeader("Authorization") String jwt) throws UserException, RideExcption {

        User user = userService.getReqUserProfile(jwt);

        Ride ride = rideService.findRideById(rideId);

        RideDto rideDto = DtoMapper.toRideDto(ride);

        return new ResponseEntity<>(rideDto, HttpStatus.ACCEPTED);

    }

}
