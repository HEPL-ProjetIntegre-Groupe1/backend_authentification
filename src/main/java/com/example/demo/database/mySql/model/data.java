package com.example.demo.database.mySql.model;

import jakarta.persistence.*;

@Entity
public class data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "utilisateurid", referencedColumnName = "id")
    private utilisateur utilisateurId;
    @Column(name = "notsosecretdata")
    private String notSoSecretData;

    public utilisateur getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(utilisateur utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getNotSoSecretData() {
        return notSoSecretData;
    }

    public void setNotSoSecretData(String notSoSecretData) {
        this.notSoSecretData = notSoSecretData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
