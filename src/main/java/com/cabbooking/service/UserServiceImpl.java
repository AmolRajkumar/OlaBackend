package com.cabbooking.service;

import com.cabbooking.config.JwtUtil;
import com.cabbooking.exception.UserException;
import com.cabbooking.model.Ride;
import com.cabbooking.model.User;
import com.cabbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

//    @Override
//    public User createdUser(User user) throws UserException {
//
//
//        return null;
//    }

    @Override
    public User getReqUserProfile(String token) throws UserException {

        String email = jwtUtil.getEmailFormJwtToken(token);

        User user = userRepository.findByEmail(email);

        if (user != null) {
            return user;
        }

        throw new UserException("invalid Token");
    }

    @Override
    public User findUserById(Integer id) throws UserException {

       Optional<User> user=userRepository.findById(id);
       if(user.isPresent()){
           return user.get();
       }

       throw new UserException("user not found with id:"+id);
    }

    @Override
    public List<Ride> completedRides(Integer userId) throws UserException {

       List<Ride>rides=userRepository.getCompletedRide(userId);

       return rides;

    }
}
