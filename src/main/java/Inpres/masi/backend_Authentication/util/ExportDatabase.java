package Inpres.masi.backend_Authentication.util;

import Inpres.masi.backend_Authentication.ORM.service.AuthenticationServ;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ExportDatabase {
    @Autowired
    private AuthenticationServ authenticationServ;

    public void filterDatabase() {
        // Export the database
        MongoClient mongoClient = MongoClients.create("mongodb://root:P%40ssw0rd@10.0.0.2/masi?authSource=admin");
        MongoDatabase database = mongoClient.getDatabase("masi");
        MongoCollection<Document> collection = database.getCollection("utilisateur");

        for(Document doc : collection.find()) {
            var registreNational = doc.getString("_id");

            if(registreNational != null) {
                var computers = authenticationServ.getComputerAuthenticationsByRegistreNational(registreNational);
                var mobiles = authenticationServ.getPhoneAuthenticationsByRegistreNational(registreNational);

                Bson updateOperation = Updates.combine(
                        Updates.set("computers", computers),
                        Updates.set("mobiles", mobiles)
                );


                collection.updateOne(Filters.eq("_id", registreNational), updateOperation);
            }
        }

        collection.aggregate(Arrays.asList(
                Aggregates.project(Projections.fields(Projections.exclude("password", "username", "certificat", "adresse.rue","Nom","Prenom","numeroTelephone","email", "registreNational"))),
                Aggregates.out("utilisateur_filtered")
        )).toCollection();
    }

    public void exportDatabase() {
        // Export the database
        MongoClient mongoClientLocal = MongoClients.create("mongodb://root:P%40ssw0rd@10.0.0.2/masi?authSource=admin");
        MongoDatabase databaseLocal = mongoClientLocal.getDatabase("masi");
        MongoCollection<Document> collectionLocal = databaseLocal.getCollection("utilisateur_filtered");



        MongoClient mongoClient = MongoClients.create("mongodb://root:P%40ssw0rd@10.0.0.4/masi?authSource=admin");
        MongoDatabase database = mongoClient.getDatabase("masi");
        MongoCollection<Document> collection = database.getCollection("utilisateur");

        for(Document doc : collectionLocal.find()) {
            // Si on trouve une instance avec la même clé primaire, on la remplace
            Bson filter = Filters.eq("_id", doc.get("_id"));
            // "upsert" permet de créer un document s'il n'existe pas
            collection.replaceOne(filter, doc, new ReplaceOptions().upsert(true));
        }
    }
}
