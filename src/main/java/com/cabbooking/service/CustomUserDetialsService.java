package com.cabbooking.service;


import com.cabbooking.model.Driver;
import com.cabbooking.model.User;
import com.cabbooking.repository.DriverRepository;
import com.cabbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetialsService  implements UserDetailsService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {


        List<GrantedAuthority>authorities=new ArrayList<>();

        User user=userRepository.findByEmail(username);

        if(user!=null){

            return new org.springframework.security.core.userdetails
                    .User(user.getEmail(),user.getPassword(),authorities);
        }

        Driver driver=driverRepository.findByEmail(username);

        if(driver!=null){

            return new org.springframework.security.core.userdetails
                    .User(driver.getEmail(),driver.getPassword(),authorities);
        }

        throw  new UsernameNotFoundException("User Not Found with Email :--"+username);
    }
}
