package com.cabbooking.controller;

import com.cabbooking.config.JwtUtil;
import com.cabbooking.domain.UserRole;
import com.cabbooking.exception.UserException;
import com.cabbooking.model.Driver;
import com.cabbooking.model.User;
import com.cabbooking.repository.DriverRepository;
import com.cabbooking.repository.UserRepository;
import com.cabbooking.request.DriverSignupRequest;
import com.cabbooking.request.LoginRequest;
import com.cabbooking.request.SignupRequest;
import com.cabbooking.response.JwtResponse;
import com.cabbooking.service.CustomUserDetialsService;
import com.cabbooking.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetialsService userDetialsService;

    @Autowired
    private DriverService driverService;


    @PostMapping("/user/signup")
    public ResponseEntity<JwtResponse> signupHandler(@RequestBody SignupRequest request) throws UserException {

        String email = request.getEmail();
        String fullName = request.getFullName();
        String mobile = request.getMobile();
        String password = request.getPassword();

        User user = userRepository.findByEmail(email);

        if (user != null) {
            throw new UserException("User AllReady Exist with email :" + email);
        }

        String encodePassword = passwordEncoder.encode(password);

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setFullName(fullName);
        createdUser.setMobile(mobile);
        createdUser.setPassword(encodePassword);
        createdUser.setRole(UserRole.USER);

        User saveUser = userRepository.save(createdUser);

        Authentication authentication = new
                UsernamePasswordAuthenticationToken(
                saveUser.getEmail(), saveUser.getPassword());


        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        JwtResponse response = new JwtResponse();

        response.setJwt(jwt);
        response.setAuthenticated(true);
        response.setError(false);
        response.setErrorDetails(null);
        response.setRole(UserRole.USER);
        response.setMessage("Account Created Successfully:" + saveUser.getFullName());


        return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
    }

    @PostMapping("/driver/signup")

    public ResponseEntity<JwtResponse> driverSignupHandler(@RequestBody DriverSignupRequest request) {


        Driver driver = driverRepository.findByEmail(request.getEmail());

        JwtResponse jwtResponse = new JwtResponse();

        if (driver != null) {
            jwtResponse.setAuthenticated(false);
            jwtResponse.setErrorDetails("email already with another account");
            jwtResponse.setError(true);
            return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.BAD_REQUEST);

        }
        Driver createdDriver = driverService.registerDriver(request);

        Authentication authentication = new UsernamePasswordAuthenticationToken(createdDriver.getEmail(), createdDriver.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwtToken(authentication);

        jwtResponse.setJwt(jwt);
        jwtResponse.setAuthenticated(true);
        jwtResponse.setError(false);
        jwtResponse.setErrorDetails(null);
        jwtResponse.setRole(UserRole.DRIVER);
        jwtResponse.setMessage("Account Created Successfully:" + createdDriver.getName());


        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.ACCEPTED);
    }


    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signIn(@RequestBody LoginRequest request) {
        String userName = request.getEmail();
        String password = request.getPassword();
        Authentication authentication = authenticate(userName, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtil.generateJwtToken(authentication);

        JwtResponse response = new JwtResponse();

        response.setJwt(jwt);
        response.setAuthenticated(true);
        response.setError(false);
        response.setErrorDetails(null);
        response.setRole(UserRole.USER);
        response.setMessage("Account Login Successfully:");


        return new ResponseEntity<JwtResponse>(response, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String userName, String passWord) {

        UserDetails userDetails = userDetialsService.loadUserByUsername(userName);

        if (userDetails == null) {

            throw new BadCredentialsException("Invalid userName or Password from authenticate method..!");

        }
        if (!passwordEncoder.matches(passWord, userDetails.getPassword())) {
            throw new BadCredentialsException("invalid username or password");

        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
