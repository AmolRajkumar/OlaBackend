package com.cabbooking.model;

import com.cabbooking.domain.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String email;
    private String mobile;
    private String rating;
    private double latitude;
    private double longitude;
    private UserRole role;
    private String password;

    @OneToOne(mappedBy = "driver",cascade = CascadeType.ALL)
    private License license;

   @JsonIgnore
   @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL,orphanRemoval = true)
   private List<Ride>rides;

   @OneToOne(mappedBy = "driver",cascade = CascadeType.ALL,orphanRemoval = true)
   private Vehicle vehicle;

   @JsonIgnore
   @OneToOne(cascade = CascadeType.ALL)
   private Ride currentRide;

   private Integer totalRevenue=0;
}
