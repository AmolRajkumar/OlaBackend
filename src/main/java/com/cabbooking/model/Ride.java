package com.cabbooking.model;

import com.cabbooking.domain.RideStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Driver driver;

    @JsonIgnore
    private List<Integer> declinedDrivers = new ArrayList<>();

    private double pickupLatitude;

    private double pickupLongitude;

    private double destinationLatitude;

    private double destinationLongitude;

    private String pickupArea;

    private String destinationArea;

    private double distance;

    private Long duration;

    private RideStatus status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double fare;

    private int otp;

    @Embedded
    private PaymentDetails paymentDetails=new PaymentDetails();



}
