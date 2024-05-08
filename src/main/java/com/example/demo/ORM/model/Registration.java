package com.example.demo.ORM.model;

import java.util.Date;

public class Registration {

  private String id;
  private Date dateExpiration;
  private String registreNational;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public Date getDateExpiration() {
    return dateExpiration;
  }

  public void setDateExpiration(Date dateExpiration) {
    this.dateExpiration = dateExpiration;
  }


  public String getRegistreNational() {
    return registreNational;
  }

  public void setRegistreNational(String registreNational) {
    this.registreNational = registreNational;
  }

}
