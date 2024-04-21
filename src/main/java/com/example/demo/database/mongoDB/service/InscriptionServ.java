package com.example.demo.database.mongoDB.service;

import com.example.demo.database.mongoDB.interfaceP.InscriptionRepository;
import com.example.demo.database.mongoDB.model.Inscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscriptionServ {
    @Autowired
    private InscriptionRepository inscriptionRepository;

    public List<Inscription> getAllInscriptions() {
        return inscriptionRepository.findAll();
    }

    public Inscription getInscriptionById(String id) {
        return inscriptionRepository.findById(id).orElse(null);
    }

    public boolean insertInscription(Inscription inscription) {
        try {
            inscriptionRepository.save(inscription);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteInscription(Inscription inscription) {
        try {
            inscriptionRepository.delete(inscription);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
