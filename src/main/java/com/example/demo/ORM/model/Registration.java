package com.example.demo.ORM.model;

public class Registration {

  private String id;
  private java.sql.Date dateExpiration;
  private String registreNational;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public java.sql.Date getDateExpiration() {
    return dateExpiration;
  }

  public void setDateExpiration(java.sql.Date dateExpiration) {
    this.dateExpiration = dateExpiration;
  }


  public String getRegistreNational() {
    return registreNational;
  }

  public void setRegistreNational(String registreNational) {
    this.registreNational = registreNational;
  }

}
