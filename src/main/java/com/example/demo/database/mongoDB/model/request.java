package com.example.demo.database.mongoDB.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "requests")
public class request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String userId;
    private String identificationToken;
    private String websiteTarget;
    private Map<String, Object> additionalFields;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdentificationToken() {
        return identificationToken;
    }

    public void setIdentificationToken(String identificationToken) {
        this.identificationToken = identificationToken;
    }

    public String getWebsiteTarget() {
        return websiteTarget;
    }

    public void setWebsiteTarget(String websiteTarget) {
        this.websiteTarget = websiteTarget;
    }

    public Map<String, Object> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(Map<String, Object> additionalFields) {
        this.additionalFields = additionalFields;
    }

    // Constructors, getters, setters
}
