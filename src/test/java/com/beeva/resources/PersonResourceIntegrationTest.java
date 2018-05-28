package com.beeva.resources;

import com.beeva.db.MongoRepository;
import com.beeva.db.Repository;
import com.beeva.views.PersonView;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonResourceIntegrationTest {

    private final static String DATABASE_NAME = "people-test";

    private MongoClient client;
    private MongoDatabase database;
    private PersonResource resource;
    private Repository repository;

    @Before
    public void setUp() {
        this.client = new MongoClient();
        this.database = client.getDatabase(DATABASE_NAME);
        this.repository = new MongoRepository(DATABASE_NAME);
        repository.open();
        this.resource = new PersonResource(repository);
    }

    @After
    public void cleanUp() {
        this.database.drop();
        this.client.close();
        this.repository.close();
    }

    @Test
    public void givenRepositoryEmptyWhenGetAllThenEmptyListReturned() {

        List<PersonView> items = resource.getAll(null);

        assertThat(items).isEmpty();
    }

    @Test
    public void givenRepositoryTwoElementsWhenGetAllThenListWithOneElementReturned() {

        insertElementInDatabase("7", "Aitor", 12345);
        insertElementInDatabase("8", "Sergio", 54321);

        List<PersonView> items = resource.getAll(null);

        PersonView view1 = generatePersonView("7", "Aitor", 12345);
        PersonView view2 = generatePersonView("8", "Sergio", 54321);
        assertThat(items).hasSize(2);
        assertThat(items).contains(view1);
        assertThat(items).contains(view2);

    }

    private PersonView generatePersonView(String id, String name,
            long dateOfBirth) {
        PersonView view = new PersonView();
        view.setName(name);
        view.setId(id);
        view.setDateOfBirth(dateOfBirth);

        return view;
    }

    @Test
    public void givenElementInDatabaseWhenGetPersonThenPersonReturned() {

        insertElementInDatabase("7", "Aitor", 12345);

        Response response = resource.getPerson("7");

        assertThat(response.getStatus()).isEqualTo(200);

        PersonView receivedView = (PersonView) response.getEntity();
        PersonView expectedView = generatePersonView("7", "Aitor", 12345);
        assertThat(receivedView).isEqualTo(expectedView);

    }

    private void insertElementInDatabase(String id, String name,
            long dateOfBirth) {
        Document element1 = new Document();
        element1.append("_id", id);
        element1.append("name", name);
        element1.append("dateOfBirth", dateOfBirth);
        this.database.getCollection("people").insertOne(element1);

    }

    @Test
    public void givenNoElementInDatabaseWhenGetPersonThen404Returned() {
        Response response = resource.getPerson("7");
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    public void givenViewWhenCreatePersonThenViewWithIdReturnedAndInsertedInDB() {
        PersonView view = new PersonView();
        view.setName("Aitor");
        view.setDateOfBirth(12345L);

        PersonView receivedView = resource.createPerson(view);

        assertThat(receivedView).isNotNull();
        String id = receivedView.getId();

        Document storedElement = this.database.getCollection("people")
                .find(eq("_id", id)).first();
        assertThat(storedElement.get("name")).isEqualTo("Aitor");
        assertThat(storedElement.get("dateOfBirth")).isEqualTo(12345L);

    }

}
