package com.cabbooking.dto;

import com.cabbooking.domain.UserRole;
import com.cabbooking.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {

private Integer id;
private String name;
private String mobile;
private String email;
private double rating;
private double latitude;
private double longitude;
private UserRole role;
private Vehicle vehicle;




}
