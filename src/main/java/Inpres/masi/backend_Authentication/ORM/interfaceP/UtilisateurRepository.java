package Inpres.masi.backend_Authentication.ORM.interfaceP;

import Inpres.masi.backend_Authentication.ORM.model.Utilisateur;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {
    Utilisateur findUtilisateurByNom(String nom);
    Utilisateur findUtilisateurByRegistreNational(String registreNational);
    Utilisateur findUtilisateurByUsername(String username);
}
