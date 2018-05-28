package com.beeva.db;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import jersey.repackaged.com.google.common.collect.Lists;

import com.beeva.exceptions.PersonNotFoundException;
import com.beeva.model.Person;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static com.mongodb.client.model.Filters.*;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoRepository implements Repository {

    private MongoClient client;
    private MongoCollection<Person> collection;

    @Override
    public List<Person> getAll() {
        List<Person> people = Lists.newArrayList();
        for (Person person : collection.find()) {
            people.add(person);
        }

        return people;
    }

    @Override
    public Person getPerson(String personId) throws PersonNotFoundException {
        Person person = collection.find(eq("_id", personId)).first();

        if (person == null) {
            throw new PersonNotFoundException(
                    String.format("Person '%s' not found", personId));
        }

        return person;
    }

    @Override
    public Person savePerson(Person person) {
        person.setId(UUID.randomUUID().toString());
        collection.insertOne(person);
        return person;
    }

    @Override
    public void open() {
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClient.getDefaultCodecRegistry(), fromProviders(
                        PojoCodecProvider.builder().automatic(true).build()));

        this.client = new MongoClient("localhost", MongoClientOptions
                .builder().codecRegistry(pojoCodecRegistry).build());
        this.collection = client.getDatabase("people").getCollection("people", Person.class);

    }

    @Override
    public void close() {
        this.client.close();
    }

}
