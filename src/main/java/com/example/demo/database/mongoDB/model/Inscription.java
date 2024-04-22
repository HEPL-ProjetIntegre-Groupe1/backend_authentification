package com.example.demo.database.mongoDB.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Document(collection = "inscription")
public class Inscription {
    @Id
    private String _id;

    @Field(name = "codeVerification")
    private String codeVerification;

    @DBRef
    private Utilisateur utilisateurRef;

    @Field(name = "dateExpiration")
    private String dateExpiration;

    @Transient
    private String utilisateurId;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCodeVerification() {
        return codeVerification;
    }

    public void setCodeVerification(String codeVerification) {
        this.codeVerification = codeVerification;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Utilisateur getUtilisateur() {
        return utilisateurRef;
    }

    public void setUtilisateur(Utilisateur utilisateurIdUtilisateur) {
        this.utilisateurRef = utilisateurIdUtilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inscription that = (Inscription) o;
        return Objects.equals(_id, that._id) && Objects.equals(codeVerification, that.codeVerification) && Objects.equals(utilisateurRef, that.utilisateurRef) && Objects.equals(dateExpiration, that.dateExpiration) && Objects.equals(utilisateurRef, that.utilisateurRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, codeVerification, utilisateurRef, dateExpiration, utilisateurRef);
    }


    public String getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(String utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
}
