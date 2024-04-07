package com.example.demo.database.mySql.interfaceP;

import com.example.demo.database.mySql.model.utilisateur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface utilisateurRepository extends CrudRepository<utilisateur, Integer>  {
    @Query("SELECT u FROM utilisateur u WHERE u.username = ?1")
    utilisateur findByUsername(String username);
}