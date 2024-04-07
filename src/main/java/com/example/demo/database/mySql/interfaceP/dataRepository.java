package com.example.demo.database.mySql.interfaceP;

import com.example.demo.database.mySql.model.data;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface dataRepository extends CrudRepository<data, Integer> {
    @Query("SELECT d FROM data d JOIN d.utilisateurId u on u.id = d.utilisateurId.id WHERE u.username = ?1")
    Iterable<data> findByUsername(String username);
}
