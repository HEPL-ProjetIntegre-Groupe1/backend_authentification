package com.example.demo.backend;

import com.example.demo.database.mongoDB.model.request;
import com.example.demo.database.mongoDB.service.requestService;
import com.example.demo.database.mySql.model.data;
import com.example.demo.database.mySql.model.utilisateur;
import com.example.demo.database.mySql.service.dataService;
import com.example.demo.database.mySql.service.utilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class backenLogicClass {
    // Classes de la couche Database
    @Autowired
    private utilisateurService utilisateurDB;
    @Autowired
    private dataService dataService;
    @Autowired
    private requestService requestService;

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

    /*public data searchDatabaseData(int userId) {
        data data = new data();
        data.setUtilisateurId(new utilisateur() {{
            setId(userId);
        }});
        data.setNotSoSecretData("This is a secret data");
        return data;
    }*/

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

    // Méthodes de la table "Request"
    public List<request> getAllrequests() {
        return requestService.getAllrequests().stream().toList();
    }

    public Optional<request> getrequestById(String id) {
        return requestService.getrequestById(id);
    }

    public request createrequest(request request) {
        return requestService.createrequest(request);
    }

    public request updaterequest(String id, request request) {
        return requestService.updaterequest(id, request);
    }

    public void deleterequest(String id) {
        requestService.deleterequest(id);
    }
}
