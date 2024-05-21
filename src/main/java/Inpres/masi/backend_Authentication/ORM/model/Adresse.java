package Inpres.masi.backend_Authentication.ORM.model;

import org.springframework.data.mongodb.core.mapping.Field;

public class Adresse {
    @Field("codePostal")
    private String codePostal;
    @Field("ville")
    private String ville;
    @Field("rue")
    private String rue;

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }
}
