package com.cabbooking.dto.mapper;

import com.cabbooking.dto.DriverDto;
import com.cabbooking.dto.RideDto;
import com.cabbooking.dto.UserDto;
import com.cabbooking.model.Driver;
import com.cabbooking.model.Ride;
import com.cabbooking.model.User;

public class DtoMapper {


    public static DriverDto toDriverDto(Driver driver) {

        DriverDto driverDto = new DriverDto();

        driverDto.setEmail(driver.getEmail());
        driverDto.setId(driver.getId());
        driverDto.setLatitude(driver.getLatitude());
        driverDto.setLongitude(driver.getLongitude());
        driverDto.setMobile(driverDto.getMobile());
        driverDto.setName(driverDto.getName());
        driverDto.setRating(driverDto.getRating());
        driverDto.setRole(driver.getRole());
        driverDto.setVehicle(driver.getVehicle());

    return driverDto;
    }


    public static UserDto toUserDto(User user){


        UserDto userDto=new UserDto();

        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getFullName());
        userDto.setMobile(user.getMobile());

        return userDto;


    }


    public static RideDto toRideDto(Ride ride){

        DriverDto driverDto=toDriverDto(ride.getDriver());
        UserDto userDto=toUserDto(ride.getUser());

        RideDto rideDto=new RideDto();

        rideDto.setDestinationLatitude(ride.getDestinationLatitude());
        rideDto.setDestinationLongitude(ride.getDestinationLongitude());

        rideDto.setDistance(ride.getDistance());
        rideDto.setDriverDto(driverDto);
        rideDto.setDuration(ride.getDuration());
        rideDto.setEndTime(ride.getEndTime());
        rideDto.setFare(ride.getFare());
        rideDto.setId(ride.getId());
        rideDto.setPickupLatitude(ride.getPickupLatitude());
        rideDto.setPickupLongitude(ride.getPickupLongitude());
        rideDto.setStartTime(ride.getStartTime());
        rideDto.setRideStatus(ride.getStatus());
        rideDto.setUserDto(userDto);
        rideDto.setPickupArea(ride.getPickupArea());
        rideDto.setDestinationArea(ride.getDestinationArea());
        rideDto.setOtp(ride.getOtp());

        return rideDto;



    }



}
