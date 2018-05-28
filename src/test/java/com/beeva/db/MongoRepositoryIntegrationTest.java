package com.beeva.db;

import com.beeva.exceptions.PersonNotFoundException;
import com.beeva.model.Person;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.assertj.core.api.Assertions.assertThat;

public class MongoRepositoryIntegrationTest {

    private static final String DATABASE_NAME = "people-name";

    private Repository repository;
    private MongoClient client;
    private MongoDatabase database;

    @Before
    public void setUp() {
        this.client = new MongoClient();
        this.database = client.getDatabase(DATABASE_NAME);
        this.repository = new MongoRepository(DATABASE_NAME);
        this.repository.open();
    }

    @After
    public void tearDown() {
        this.database.drop();
        this.repository.close();
    }

    @Test
    public void testGivenDatabaseEmptyWhenGetAllThenEmptyListReturned() {
        List<Person> people = this.repository.getAll();
        assertThat(people).isEmpty();
    }

    @Test
    public void testGivenDatabaseWithOneElementWhenGetAllThenOneElementReturned() {
        insertElementInDatabase("7", "Aitor", 1234);
        List<Person> people = this.repository.getAll();
        assertThat(people).hasSize(1);
        assertThat(people.get(0)).isEqualTo(generatePerson("7", "Aitor", 1234));
    }

    @Test
    public void testGivenDatabaseWithTwoElementWhenGetAllThenTwoElementsReturned() {
        insertElementInDatabase("7", "Aitor", 1234);
        insertElementInDatabase("8", "Sergio", 54321);
        List<Person> people = this.repository.getAll();
        assertThat(people).hasSize(2);
        assertThat(people).contains(generatePerson("7", "Aitor", 1234));
        assertThat(people).contains(generatePerson("8", "Sergio", 54321));
    }

    @Test(expected = PersonNotFoundException.class)
    public void testGivenNoElementInDatabseWhenGetElementThenExceptionThown()
            throws PersonNotFoundException {
        this.repository.getPerson("7");
    }

    @Test
    public void testGivenElementInDatabseWhenGetElementThenElementReturned()
            throws PersonNotFoundException {
        insertElementInDatabase("7", "Aitor", 1234);
        assertThat(this.repository.getPerson("7"))
                .isEqualTo(generatePerson("7", "Aitor", 1234));
    }

    @Test
    public void testGivenPersonWhenSavePersonThenPersonStoredInDatabaseAndIdSet() {
        Person person = generatePerson(null, "Aitor", 54321);
        this.repository.savePerson(person);

        assertThat(person.getId()).isNotNull();

        Document document = this.database.getCollection("people").find(eq("_id", person.getId())).first();
        assertThat(document.get("name")).isEqualTo("Aitor");
        assertThat(document.get("dateOfBirth")).isEqualTo(54321L);
    }

    private void insertElementInDatabase(String id, String name,
            long dateOfBirth) {
        Document element1 = new Document();
        element1.append("_id", id);
        element1.append("name", name);
        element1.append("dateOfBirth", dateOfBirth);
        this.database.getCollection("people").insertOne(element1);

    }

    private Person generatePerson(String id, String name, long dateOfBirth) {

        Person person = new Person();
        person.setName(name);
        person.setId(id);
        person.setDateOfBirth(dateOfBirth);

        return person;

    }

}
