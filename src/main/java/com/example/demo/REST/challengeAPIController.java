package com.example.demo.REST;

import com.example.demo.ORM.model.Challenge;
import com.example.demo.ORM.service.ChallengeServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/challenge")
public class challengeAPIController {
    @Autowired
    private ChallengeServ challengeServ;

    @GetMapping
    public ResponseEntity<List<Challenge>> getAllChallenges() {
        var challenges = challengeServ.getAllChallenges();
        if(challenges.isEmpty()) {
            return ResponseEntity.badRequest().body(challenges);
        }
        return ResponseEntity.ok(challenges);
    }
}
