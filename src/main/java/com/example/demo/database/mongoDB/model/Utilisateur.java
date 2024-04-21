package com.example.demo.database.mongoDB.model;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Document(collection = "utilisateur")
public class Utilisateur {
    @Id
    private String _id;

    @Field(name = "Nom")
    private String nom;
    @Field(name = "Prenom")
    private String prenom;
    @Field(name = "adresse")
    private String adresse;
    @Field(name = "dateNaissance")
    private String dateNaissance;

    @Field(name = "numeroTelephone")
    private String numeroTelephone;

    @Field(name = "registreNational")
    private String registreNational;

    @DBRef
    private Credentials credentialsRef;
    @Transient
    private String credentialsId;

    @Field(name = "email")
    private String email;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getRegistreNational() {
        return registreNational;
    }

    public void setRegistreNational(String registreNational) {
        this.registreNational = registreNational;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return Objects.equals(_id, that._id) && Objects.equals(nom, that.nom) && Objects.equals(adresse, that.adresse) && Objects.equals(dateNaissance, that.dateNaissance) && Objects.equals(numeroTelephone, that.numeroTelephone) && Objects.equals(registreNational, that.registreNational) && Objects.equals(prenom, that.prenom) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, nom, adresse, dateNaissance, numeroTelephone, registreNational, prenom, email);
    }

    public Credentials getCredentials() {
        return credentialsRef;
    }

    public void setCredentials(Credentials credentialsRef) {
        this.credentialsRef = credentialsRef;
    }

    public String getCredentialsId() {
        return credentialsId;
    }

    public void setCredentialsId(String credentialsId) {
        this.credentialsId = credentialsId;
    }
}
