package com.example.demo.ORM.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "utilisateur")
public class Utilisateur {
  @Field("Nom")
  private String nom;
  @Field("Prenom")
  private String prenom;
  @Field("dateNaissance")
  private String dateNaissance;
  @Field("numeroTelephone")
  private String numeroTelephone;
  @Id
  @Field("registreNational")
  private String registreNational;
  @Field("username")
  private String username;
  @Field("email")
  private String email;
  @Field("password")
  private String password;
  @Field("adresse")
  private String adresse;
  @Field("certificat")
  private String certificat;
  @Field
  private Date dateExpirationInscription;


  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
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


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }


  public String getCertificat() {
    return certificat;
  }

  public void setCertificat(String certificat) {
    this.certificat = certificat;
  }

  public Date getDateExpirationInscription() {
    return dateExpirationInscription;
  }

  public void setDateExpirationInscription(Date dateExpirationInscription) {
    this.dateExpirationInscription = dateExpirationInscription;
  }
}
