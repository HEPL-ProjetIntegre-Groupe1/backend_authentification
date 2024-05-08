package com.example.demo.ORM.service;

import com.example.demo.ORM.interfaceP.ChallengeRepository;
import com.example.demo.ORM.model.Challenge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeServ {
    @Autowired
    private ChallengeRepository challengeRepository;

    public List<Challenge> getAllChallenges() {
        return challengeRepository.findAll();
    }
    public boolean insertChallenge(Challenge challenge) {
        try {
            challengeRepository.save(challenge);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteChallenge(Challenge challenge) {
        try {
            challengeRepository.delete(challenge);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
