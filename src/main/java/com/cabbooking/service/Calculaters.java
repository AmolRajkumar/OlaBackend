package com.cabbooking.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class Calculaters {



    private static final int EARTH_RADIUS=6371;


    public double calculateDistance(double sourceLatitude,double sourceLongitude,
                                    double destLat,double destLong){

        double dlat=Math.toRadians(destLat-sourceLatitude);
        double dlng=Math.toRadians(destLong-sourceLongitude);

        double a=Math.sin(dlat/2)* Math.sin(dlng/2)
                +Math.cos(Math.toRadians(sourceLatitude))*Math.cos(Math.toRadians(destLat))
                +Math.sin(dlng/2)*Math.sin(dlng/2);

        double c=2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));

        double distance=EARTH_RADIUS*c;



        return distance;

    }

public long calculateDuration(LocalDateTime startTime,LocalDateTime endTime){

    Duration duration=Duration.between(startTime,endTime);

    return duration.getSeconds();
}


public double calculateFare(double distance){

     double baseFare=11;  // per km 11
     double totalFare=baseFare*distance;
     return totalFare;
}

}
