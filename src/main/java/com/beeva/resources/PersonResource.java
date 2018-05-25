package com.beeva.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.beeva.db.Repository;
import com.beeva.exceptions.PersonNotFoundException;
import com.beeva.model.Person;
import com.beeva.views.ErrorView;
import com.beeva.views.PersonView;
import jersey.repackaged.com.google.common.collect.Lists;

import static javax.ws.rs.core.Response.status;

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {



    @Inject
    private Repository repository;

    @GET
    public List<PersonView> getAll(@QueryParam("minAge") Integer minAge) {
        List<PersonView> people = repository.getAll().stream().map(Person::toView).collect(Collectors.toList());

        if (minAge != null) {
            people = people.stream().filter(p -> p.getAge() >= minAge).collect(Collectors.toList());
        }

        return people;
    }

    @GET
    @Path("/{id}")
    public Response getPerson(@PathParam("id") String id) {
        try {
            return status(200).entity(repository.getPerson(id).toView())
                    .build();
        } catch (PersonNotFoundException ex) {
            return status(404).entity(new ErrorView("NOT_FOUND", ex.getMessage())).build();
        }
    }

    @POST
    public PersonView createPerson(PersonView view) {
        Person response = repository.savePerson(Person.fromView(view));
        return response.toView();
    }

}
