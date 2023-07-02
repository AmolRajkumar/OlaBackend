package com.cabbooking.repository;

import com.cabbooking.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository extends JpaRepository<License,Integer> {
}
