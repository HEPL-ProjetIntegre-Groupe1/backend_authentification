package com.example.demo.REST;

import com.example.demo.ORM.model.Challenge;
import com.example.demo.ORM.service.ChallengeServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mongoDB/challenge")
public class challengeAPIController {
    @Autowired
    private ChallengeServ challengeServ;

    @GetMapping
    public List<Challenge> getAllChallenges() {
        var t =challengeServ.getAllChallenges();
        return t;
    }
}
