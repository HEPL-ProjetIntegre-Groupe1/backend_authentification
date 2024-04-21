package com.example.demo.util;

import com.example.demo.database.mySql.model.data;
import com.example.demo.database.mySql.model.utilisateur;
import com.example.demo.database.mySql.service.dataService;
import com.example.demo.database.mySql.service.utilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class mySqlRequests {
    // Classes de la couche Database
    @Autowired
    private utilisateurService utilisateurDB;
    @Autowired
    private dataService dataService;

    // Méthodes de la table "Utilisateur"
    public boolean verifyLogin(String username, String password) {
        System.out.println("Checking login : " + username + " " + password);

        utilisateur u = utilisateurDB.getUtilisateurByUsername(username);

        return u != null && u.getPassword().equals(password);
    }

    public boolean saveData(data notSoSecretData) {
        System.out.println("Saved : " + notSoSecretData);
        return true;
    }


    // Méthodes de la table "Data"
    public Iterable<data> getAllData() {
        return dataService.getAllData();
    }

    public data getDataById(int id) {
        return dataService.getDataById(id);
    }

    public data addData(data d) {
        return dataService.addData(d);
    }

    public boolean deleteData(int id) {
        if(dataService.getDataById(id) != null) {
            dataService.deleteData(id);
            return true;
        }
        return false;
    }
}
