package com.quizify.examserver.controller;

import com.quizify.examserver.config.JwtUtil;
import com.quizify.examserver.exception.CustomException;
import com.quizify.examserver.model.auth.JwtRequest;
import com.quizify.examserver.model.auth.JwtResponse;
import com.quizify.examserver.model.user.User;
import com.quizify.examserver.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) {
        try {
            this.authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new CustomException("User not found");
        }

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println(token);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new CustomException("User Disabled" + e.getMessage());
        } catch (BadCredentialsException e) {
            throw new CustomException("Bad Credentials" + e.getMessage());
        }
    }


    /**
     *
     * Returns the UserDetails of current user
     */
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal) {
        return (User)this.userDetailsService.loadUserByUsername(principal.getName());
    }
}
