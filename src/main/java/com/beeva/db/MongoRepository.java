package com.beeva.db;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import jersey.repackaged.com.google.common.collect.Lists;

import com.beeva.exceptions.PersonNotFoundException;
import com.beeva.model.Person;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

public class MongoRepository implements Repository {

    private MongoCollection<Person> collection;

    @Inject
    public MongoRepository(MongoDatabase database) {
        this.collection = database.getCollection("people", Person.class);
    }

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

}