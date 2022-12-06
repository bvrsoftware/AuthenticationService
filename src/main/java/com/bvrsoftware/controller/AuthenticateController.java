package com.bvrsoftware.controller;

import java.security.Principal;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bvrsoftware.config.JwtUtils;
import com.bvrsoftware.model.JwtRequest;
import com.bvrsoftware.model.JwtResponse;
import com.bvrsoftware.model.User;
import com.bvrsoftware.service.impl.UserDetailsServiceImpl;

@RestController
@CrossOrigin("*")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl detailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/generate-token")
    public ResponseEntity<?> getToken(@RequestBody JwtRequest jwtRequest) throws Exception {

        try {
            authencate(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Username Not Found");
        }

        UserDetails userDetails = this.detailsService.loadUserByUsername(jwtRequest.getUsername());

        String generateToken = this.jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(generateToken));

    }

    // authencation method
    private void authencate(String username, String password) throws Exception {
        try {
            System.out.println(username);
            System.out.println(password);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        } catch (DisabledException e) {
            e.getMessage();
            throw new Exception("User Is Disabled");

        } catch (BadCredentialsException e) {
            e.getMessage();
            throw new Exception("Invalid Credentials");
        }
    }


    //	get current user details
    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentLoggedInUser(Principal principal) {
        try {
            User user = (User) this.detailsService.loadUserByUsername(principal.getName());
            user.setPassword("");
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // to validate token
    @GetMapping("/validity-token/{token}")
    public ResponseEntity<?> validateToken(@PathVariable("token") String token, Principal principal) {

        try {
            UserDetails loadUserByUsername = this.detailsService.loadUserByUsername(principal.getName());
            if (this.jwtUtils.validateToken(token, loadUserByUsername)) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
