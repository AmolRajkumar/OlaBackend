package com.cabbooking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private String make;

    private String modelName;

    private String year;

    private String color;

    private String licensePlate;

    private int capacity;

    @JsonIgnore
    @OneToOne
    private Driver driver;
}
