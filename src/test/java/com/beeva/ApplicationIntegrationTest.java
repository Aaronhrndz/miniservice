package com.beeva;

import com.beeva.MiniServiceApplication;
import com.beeva.MiniServiceConfiguration;
import com.beeva.db.MongoRepository;
import com.beeva.resources.PersonResource;
import com.beeva.views.ErrorView;
import com.beeva.views.PersonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URLDecoder;

import static com.mongodb.client.model.Filters.eq;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationIntegrationTest {

    private MongoClient client;
    private MongoDatabase database;

    @ClassRule
    public static final DropwizardAppRule<MiniServiceConfiguration> RULE = new DropwizardAppRule(
            MiniServiceApplication.class, "config-test.yml");

    @Before
    public void setUp() {
        this.client = new MongoClient();
        this.database = client.getDatabase(
                RULE.getConfiguration().getDatabaseConfig().getName());
    }

    @After
    public void cleanUp() {
        this.database.drop();
    }

    @Test
    public void givenPersonAsJsonWhenPostUserThenUserIsCreatedAndReturned()
            throws IOException {
        String json = "{\"name\": \"Aitor\", \"dateOfBirth\": 1234}";

        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8080/people")
                .request().accept(APPLICATION_JSON).post(Entity.json(json));

        assertThat(response.getStatus()).isEqualTo(200);

        PersonView view = new ObjectMapper().readValue(
                new BufferedReader(new InputStreamReader(
                        ((InputStream) response.getEntity()))),
                PersonView.class);

        assertThat(view.getName()).isEqualTo("Aitor");
        assertThat(view.getDateOfBirth()).isEqualTo(1234);

        Document document = this.database.getCollection("people")
                .find(eq("_id", view.getId())).first();
        assertThat(document.get("name")).isEqualTo("Aitor");
        assertThat(document.get("dateOfBirth")).isEqualTo(1234L);
    }

    @Test
    public void givenPersonStoredInDatabaseWhenGetPersonThenPersonReturned()
            throws IOException {

        insertElementInDatabase("7", "Aitor", 12345);

        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8080/people/7")
                .request().accept(APPLICATION_JSON).get();

        assertThat(response.getStatus()).isEqualTo(200);

        PersonView view = new ObjectMapper().readValue(
                new BufferedReader(new InputStreamReader(
                        ((InputStream) response.getEntity()))),
                PersonView.class);

        assertThat(view.getName()).isEqualTo("Aitor");
        assertThat(view.getDateOfBirth()).isEqualTo(12345L);
    }

    private void insertElementInDatabase(String id, String name,
                                         int dateOfBirth) {
        Document element1 = new Document();
        element1.append("_id", id);
        element1.append("name", name);
        element1.append("dateOfBirth", dateOfBirth);
        this.database.getCollection("people").insertOne(element1);

    }


}
