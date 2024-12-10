package com.core.coffee.repository;

import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCollection;

@Repository
public class CustomRepository {

    private MongoTemplate genericTemplate;

    
    public CustomRepository(MongoTemplate genericTemplate) {
        this.genericTemplate = genericTemplate;
    }

    public long count(String collection) {
        Query query = new Query();
        return genericTemplate.count(query, long.class, collection);
    }

    public MongoCollection<Document> createCollection(String collection) {
        return genericTemplate.createCollection(collection);
    }

}

