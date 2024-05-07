package com.example.demo.ORM.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "utilisateur")
public class Utilisateur {

  @Id
  private String id;
  @Field("nom")
  private String nom;
  @Field("dateNaissance")
  private String dateNaissance;
  @Field("numeroTelephone")
  private String numeroTelephone;
  @Field("registreNational")
  private String registreNational;
  @Field("username")
  private String username;
    @Field("prenom")
  private String prenom;
    @Field("email")
  private String email;
    @Field("password")
  private String password;
    @Field("adresse")
  private String adresse;
    @Field("certificat")
  private String certificat;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


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

}
