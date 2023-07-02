package com.cabbooking.repository;

import com.cabbooking.model.Ride;
import com.cabbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    public User findByEmail(String email);

    @Query("Select R from Ride R where R.status=COMPLETED AND R.user.id=:userId")
    public List<Ride>getCompletedRide(@Param("userId") Integer userId);


}
