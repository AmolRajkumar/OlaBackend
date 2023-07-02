package com.cabbooking.service;

import com.cabbooking.exception.UserException;
import com.cabbooking.model.Ride;
import com.cabbooking.model.User;

import java.util.List;

public interface UserService {


//public User createdUser(User user) throws UserException;

public User getReqUserProfile(String token) throws UserException;

public User findUserById(Integer id)throws UserException;

//public User findUserByEmail(String email) throws UserException;

public List<Ride>completedRides(Integer userId) throws UserException;





}
