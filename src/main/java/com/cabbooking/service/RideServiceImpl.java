package com.cabbooking.service;

import com.cabbooking.domain.RideStatus;
import com.cabbooking.exception.DriverExcption;
import com.cabbooking.exception.RideExcption;
import com.cabbooking.model.Driver;
import com.cabbooking.model.Ride;
import com.cabbooking.model.User;
import com.cabbooking.repository.DriverRepository;
import com.cabbooking.repository.RideRepository;
import com.cabbooking.request.RideRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service

public class RideServiceImpl implements RideService {


    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private Calculaters calculaters;


    @Override
    public Ride requestRide(RideRequest rideRequest, User user) throws DriverExcption {


        double pickupLatitude = rideRequest.getPickupLatitude();
        double pickupLongitude = rideRequest.getPickupLongitude();

        double destinationLatitude = rideRequest.getDestinationLatitude();

        double destinationLongitude = rideRequest.getDestinationLongitude();

        String pickupArea = rideRequest.getPickupArea();
        String destinationArea = rideRequest.getDestinationArea();

        Ride existingRide = new Ride();

        List<Driver> availableDriver = driverService.getAvailableDrivers(pickupLatitude, pickupLongitude, existingRide);


        Driver nearestDriver = driverService.findNearestDrivers(availableDriver, pickupLatitude, pickupLongitude);

        if (nearestDriver == null) {
            throw new DriverExcption("driver Not Available");

        }

        System.out.println("Duration Before Ride:");

        Ride ride = createdRideRequest(user, nearestDriver, pickupLatitude, pickupLongitude, destinationLatitude, destinationLongitude, pickupArea, destinationArea);

        System.out.println("Duration After Ride:");

        return ride;
    }

    @Override
    public Ride createdRideRequest(User user, Driver nearestDriver, double pickupLatitude, double pickupLongitude, double destinationLatitude, double destinationLongitude, String pickupArea, String destinationArea) {

        Ride ride = new Ride();
        ride.setDriver(nearestDriver);
        ride.setUser(user);
        ride.setPickupLatitude(pickupLatitude);
        ride.setPickupLongitude(pickupLongitude);
        ride.setDestinationArea(destinationArea);
        ride.setDestinationLatitude(destinationLatitude);
        ride.setDestinationLongitude(destinationLongitude);
        ride.setStatus(RideStatus.REQUESTED);
        ride.setPickupArea(pickupArea);

        return rideRepository.save(ride);
    }

    @Override
    public void acceptRide(Integer rideId) throws RideExcption {

        Ride ride = findRideById(rideId);
        ride.setStatus(RideStatus.ACCEPTED);
        Driver driver = ride.getDriver();

        driver.setCurrentRide(ride);
        Random random = new Random();

        int otp = random.nextInt(9000) + 1000;
        ride.setOtp(otp);

        driverRepository.save(driver);
        rideRepository.save(ride);


    }

    @Override
    public void declineRide(Integer rideId, Integer driverId) throws RideExcption {


        Ride ride = findRideById(rideId);
        ride.getDeclinedDrivers().add(driverId);
        List<Driver> availableDrivers = driverService.getAvailableDrivers(ride.getPickupLatitude(), ride.getPickupLongitude(), ride);

        Driver nearestDriver = driverService.findNearestDrivers(availableDrivers, ride.getPickupLatitude(), ride.getPickupLongitude());

        ride.setDriver(nearestDriver);
        rideRepository.save(ride);

    }


    @Override
    public void startRide(Integer rideId, int otp) throws RideExcption {

        Ride ride = findRideById(rideId);
        if (otp != ride.getOtp()) {

            throw new RideExcption("please Provide a valid otp");
        }

        ride.setStatus(RideStatus.ACCEPTED);
        ride.setStartTime(LocalDateTime.now());
        rideRepository.save(ride);


    }

    @Override
    public void completeRide(Integer rideId) throws RideExcption {

        Ride ride = findRideById(rideId);
        ride.setStatus(RideStatus.COMPLETED);
        ride.setEndTime(LocalDateTime.now());

        double distance = calculaters.calculateDistance(ride.getDestinationLatitude(),
                ride.getDestinationLongitude(), ride.getPickupLatitude(), ride.getPickupLongitude());


        LocalDateTime start = ride.getStartTime();
        LocalDateTime end = ride.getEndTime();
        Duration duration = Duration.between(start, end);
        long miliSecond = duration.toMillis();

        double fare = calculaters.calculateFare(distance);

        ride.setDistance(Math.round(distance * 100.0) / 100.0);
        ride.setFare((int) Math.round(fare));
        ride.setDuration(miliSecond);
        ride.setEndTime(LocalDateTime.now());


        Driver driver = ride.getDriver();
        driver.getRides().add(ride);
        driver.setCurrentRide(null);

        //  80 % is for driver and 20% for company
        Integer driverRevenue = (int) (driver.getTotalRevenue() + Math.round(fare * 0.8));
        driver.setTotalRevenue(driverRevenue);

        driverRepository.save(driver);
        rideRepository.save(ride);


    }

    @Override
    public void cancelRide(Integer rideId) throws RideExcption {


        Ride ride = findRideById(rideId);
        ride.setStatus(RideStatus.CANCELLED);

        rideRepository.save(ride);

    }

    @Override
    public Ride findRideById(Integer rideId) throws RideExcption {

        Optional<Ride> ride = rideRepository.findById(rideId);

        if (ride.isPresent()) {
            return ride.get();
        }


        throw new RideExcption("ride not exist with id:" + rideId);


    }
}
