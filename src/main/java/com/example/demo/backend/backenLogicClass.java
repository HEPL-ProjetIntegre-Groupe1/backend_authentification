package com.example.demo.backend;

import com.example.demo.database.mySql.model.data;
import com.example.demo.database.mySql.model.utilisateur;
import com.example.demo.database.mySql.service.utilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class backenLogicClass {
    @Autowired
    private utilisateurService utilisateurDB;

    public boolean verifyLogin(String username, String password) {
        System.out.println("Checking login : " + username + " " + password);

        utilisateur u = utilisateurDB.getUtilisateurByUsername(username);

        if(u != null && u.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    public boolean saveData(data notSoSecretData) {
        System.out.println("Saved : " + notSoSecretData);
        return true;
    }

    public data searchDatabaseData(int userId) {
        data data = new data();
        data.setUtilisateurId(new utilisateur() {{
            setId(userId);
        }});
        data.setNotSoSecretData("This is a secret data");
        return data;
    }
}
