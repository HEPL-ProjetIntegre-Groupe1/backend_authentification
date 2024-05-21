package Inpres.masi.backend_Authentication.ORM.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

public class Registration {
  @Id
  private String id;
  @Field("dateExpiration")
  private Date dateExpiration;
  @Field("registreNational")
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
