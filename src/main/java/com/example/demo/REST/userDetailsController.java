package com.example.demo.REST;


import com.example.demo.database.mongoDB.service.JwtUtil;
import com.example.demo.database.mongoDB.service.userDetailServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class userDetailsController {
    @Autowired
    userDetailServ service;
    @Autowired
    JwtUtil jwt;
    @GetMapping("/userDetails")
    public UserDetails getUserDetails(@RequestParam(name = "username") String username) {

        return service.loadUserByUsername(username);
    }

    @GetMapping("/getUserToken")
    public String getUserToken(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password) {
        UserDetails userDetails = service.loadUserByUsername(username);
        if (userDetails.getPassword().equals(password))
            return jwt.generateToken(userDetails.getUsername());
        return "Invalid Credentials";
    }

    @GetMapping("/verifyToken")
    public String verifyToken(@RequestParam(name = "token") String token) {
        var username = jwt.extractUsername(token);
        if (username != null)
            return "valid : " + username;
        return "Invalid Token";
    }
}
