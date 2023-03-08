package com.springbooot.blog.service.impl;

import com.springbooot.blog.entity.Role;
import com.springbooot.blog.entity.User;
import com.springbooot.blog.exception.BlogApiException;
import com.springbooot.blog.payload.LoginDto;
import com.springbooot.blog.payload.RegisterDto;
import com.springbooot.blog.repository.RoleRepository;
import com.springbooot.blog.repository.UserRepository;
import com.springbooot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager; // it provides authentication() method

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(LoginDto loginDto) {
       Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),loginDto.getPassword()));

       // Once we do the authentication next, we need to store this authentication object into the spring
        // security context holder.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "user logged-in successfully!.";

        // we have implemented the login method, Now, let's build login Api ,
        // Go to Controller , create AuthController class
    }

    @Override
    public String register(RegisterDto registerDto) {

        // validate user name exists in the database or not
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw  new BlogApiException(HttpStatus.BAD_REQUEST, " Username already exists!" );

        }

        // check whether email exist in DB or Not
        if(userRepository.existsByEmail((registerDto.getEmail()))){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "email already exists!");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode( registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role  userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);


        userRepository.save(user);

        return "User registered Successfully!";
    }
}
