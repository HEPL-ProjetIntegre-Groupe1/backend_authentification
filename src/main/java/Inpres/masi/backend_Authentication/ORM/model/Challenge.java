package Inpres.masi.backend_Authentication.ORM.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "challenge")
public class Challenge {
  @Id
  private String id;
  @Field("CODE")
  private String code;
  @Field("uncryptedMessage")
  private String uncryptedMessage;
  @Field("Images")
  private List<String> images;
  @Field("rightImage")
  private String rightImage;
  @Field("masiIdValidated")
  private boolean masiIdValidated;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }


  public String getUncryptedMessage() {
    return uncryptedMessage;
  }

  public void setUncryptedMessage(String uncryptedMessage) {
    this.uncryptedMessage = uncryptedMessage;
  }


  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  public String getRightImage() {
    return rightImage;
  }

  public void setRightImage(String rightImage) {
    this.rightImage = rightImage;
  }

  public boolean isMasiIdValidated() {
    return masiIdValidated;
  }

  public void setMasiIdValidated(boolean masiIdValidated) {
    this.masiIdValidated = masiIdValidated;
  }
}
