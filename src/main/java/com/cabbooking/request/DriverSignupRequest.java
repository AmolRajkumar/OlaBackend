package com.cabbooking.request;

import com.cabbooking.model.License;
import com.cabbooking.model.Vehicle;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverSignupRequest {


    private String email;
    private String name;
    private String mobile;
    private String password;
    private double latitude;
    private double longitude;
    private License license;
    private Vehicle vehicle;









}
