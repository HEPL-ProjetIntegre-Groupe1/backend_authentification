package com.example.demo.database.mySql.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "data", schema = "masi", catalog = "")
public class DataEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "utilisateurId")
    private Integer utilisateurId;
    @Basic
    @Column(name = "notSoSecretData")
    private String notSoSecretData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Integer utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getNotSoSecretData() {
        return notSoSecretData;
    }

    public void setNotSoSecretData(String notSoSecretData) {
        this.notSoSecretData = notSoSecretData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataEntity that = (DataEntity) o;
        return id == that.id && Objects.equals(utilisateurId, that.utilisateurId) && Objects.equals(notSoSecretData, that.notSoSecretData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, utilisateurId, notSoSecretData);
    }
}
