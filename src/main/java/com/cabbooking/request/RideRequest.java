package com.cabbooking.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRequest {

    private double pickupLongitude;
    private double pickupLatitude;
    private double destinationLongitude;
    private double destinationLatitude;
    private String pickupArea;
    private String destinationArea;

}
