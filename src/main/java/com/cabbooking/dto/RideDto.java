package com.cabbooking.dto;

import com.cabbooking.domain.RideStatus;
import com.cabbooking.model.PaymentDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideDto {


private Integer id;
private UserDto userDto;
private DriverDto driverDto;
private double pickupLatitude;
private double pickupLongitude;
private double destinationLatitude;
private double destinationLongitude;
private String pickupArea;
private String destinationArea;
private double distance;
private long duration;
private RideStatus rideStatus;
private LocalDateTime startTime;
private LocalDateTime endTime;
private double fare;
private int otp;


}
