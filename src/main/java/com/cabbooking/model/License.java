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
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String licenseNumber;
    private String licenseState;
    private String licenseExpirationDate;

       @JsonIgnore
       @OneToOne(cascade = CascadeType.ALL)
       private Driver driver;


}
