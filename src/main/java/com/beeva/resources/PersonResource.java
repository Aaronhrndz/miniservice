package com.beeva.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    public List<PersonView> getAll() {
        List<PersonView> allUsers = Lists.newArrayList();

        for (Person person: repository.getAll()) {
        	allUsers.add(person.toView());
		}

		return allUsers;
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
