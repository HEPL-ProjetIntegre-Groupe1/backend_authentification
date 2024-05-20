package com.example.demo.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ExportDatabase {
    public void exportDatabase() {
        // Export the database
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("masi");
        MongoCollection<Document> collection = database.getCollection("utilisateur");

        collection.aggregate(Arrays.asList(
                Aggregates.project(Projections.fields(Projections.exclude("password", "username", "certificat"))),
                Aggregates.out("utilisateur_filtered")
        )).toCollection();
    }
}
