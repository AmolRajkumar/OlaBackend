package com.cabbooking.service;

import com.cabbooking.config.JwtUtil;
import com.cabbooking.domain.RideStatus;
import com.cabbooking.domain.UserRole;
import com.cabbooking.exception.DriverExcption;
import com.cabbooking.model.Driver;
import com.cabbooking.model.License;
import com.cabbooking.model.Ride;
import com.cabbooking.model.Vehicle;
import com.cabbooking.repository.DriverRepository;
import com.cabbooking.repository.LicenseRepository;
import com.cabbooking.repository.RideRepository;
import com.cabbooking.repository.VehicleRepository;
import com.cabbooking.request.DriverSignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {


    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private Calculaters calculaters;

   @Autowired
   private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private RideRepository rideRepository;

    @Override
    public Driver registerDriver(DriverSignupRequest driverSignupRequest) {


        License license = driverSignupRequest.getLicense();
        Vehicle vehicle = driverSignupRequest.getVehicle();

        License createdLicense = new License();

        createdLicense.setLicenseState(license.getLicenseState());
        createdLicense.setLicenseNumber(license.getLicenseNumber());
        createdLicense.setLicenseExpirationDate(license.getLicenseExpirationDate());
        createdLicense.setId(license.getId());

        License saveLicense = licenseRepository.save(createdLicense);

        Vehicle createdVehicle = new Vehicle();

        createdVehicle.setCapacity(vehicle.getCapacity());
        createdVehicle.setColor(vehicle.getColor());
        createdVehicle.setId(vehicle.getId());
        createdVehicle.setLicensePlate(vehicle.getLicensePlate());
        createdVehicle.setMake(vehicle.getMake());
        createdVehicle.setModelName(vehicle.getModelName());
        createdVehicle.setYear(vehicle.getYear());

        Vehicle saveVehicle = vehicleRepository.save(createdVehicle);

        Driver driver = new Driver();

        String encodePassword = passwordEncoder.encode(driverSignupRequest.getPassword());


        driver.setEmail(driverSignupRequest.getEmail());
        driver.setName(driverSignupRequest.getName());
        driver.setMobile(driverSignupRequest.getMobile());
        driver.setPassword(encodePassword);
        driver.setLicense(saveLicense);
        driver.setVehicle(saveVehicle);
        driver.setRole(UserRole.DRIVER);

        driver.setLatitude(driverSignupRequest.getLatitude());
        driver.setLongitude(driverSignupRequest.getLongitude());

        Driver createdDriver = driverRepository.save(driver);

        saveLicense.setDriver(createdDriver);
        saveVehicle.setDriver(createdDriver);


        licenseRepository.save(saveLicense);
        vehicleRepository.save(saveVehicle);

        return createdDriver;

    }

    @Override
    public List<Driver> getAvailableDrivers(double pickupLatitude, double pickupLongitude, Ride ride) {


        List<Driver> allDrivers = driverRepository.findAll();
        List<Driver> availableDriver = new ArrayList<>();

        for (Driver driver : allDrivers) {
            if (driver.getCurrentRide() != null && driver.getCurrentRide().getStatus() != RideStatus.COMPLETED) {
                continue;
            }

            if (ride.getDeclinedDrivers().contains(driver.getId())) {
                System.out.println("its Contain");
                continue;
            }


            double driverLatitude = driver.getLatitude();
            double driverLongitude = driver.getLongitude();

            double distance = calculaters.calculateDistance(driverLatitude, driverLongitude,
                    pickupLatitude, pickupLongitude);

            availableDriver.add(driver);
        }


        return availableDriver;
    }

    @Override
    public Driver findNearestDrivers(List<Driver> availableDrivers, double pickupLatitude, double pickupLongitude) {

        double min = Double.MAX_VALUE;
        Driver nearestDriver = null;

        for (Driver driver : availableDrivers) {

            double driverLatitide = driver.getLatitude();
            double driverLongitude = driver.getLongitude();

            double distence = calculaters.calculateDistance(pickupLatitude, pickupLongitude, driverLatitide, driverLongitude);


            if (min > distence) {
                min = distence;
                nearestDriver = driver;
            }


        }


        return nearestDriver;
    }

    @Override
    public Driver getReqDriverProfile(String jwt) throws DriverExcption {

        String email = jwtUtil.getEmailFormJwtToken(jwt);

        Driver driver = driverRepository.findByEmail(email);

        if (driver == null) {
            throw new DriverExcption("driver not exist with email:" + email);
        }


        return driver;
    }

    @Override
    public Ride getDriversCurrentRide(Integer driverId) throws DriverExcption {

        Driver driver = findDriverById(driverId);

        return driver.getCurrentRide();
    }

    @Override
    public List<Ride> getAllocatedRides(Integer driverId) throws DriverExcption {

        List<Ride> allocatedRide = driverRepository.getAllocatedRides(driverId);


        return allocatedRide;
    }

    @Override
    public Driver findDriverById(Integer driverId) throws DriverExcption {

        Optional<Driver> opt = driverRepository.findById(driverId);

        if (opt.isPresent()) {
            return opt.get();
        }
        throw new DriverExcption("driver not exist with id:" + driverId);


    }

    @Override
    public List<Ride> completedRides(Integer driverId) throws DriverExcption {

        List<Ride> rides = driverRepository.getCompletedRides(driverId);

        return rides;


    }
}
