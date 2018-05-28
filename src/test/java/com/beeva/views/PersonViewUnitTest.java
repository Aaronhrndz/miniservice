package com.beeva.views;

import com.beeva.model.Person;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonViewUnitTest {

    @Test
    public void testGivenPersonIdIsSetWhenGetIdThenIDReturned() {
        PersonView person = new PersonView();
        person.setId("1234");

        String id = person.getId();

        assertThat(id).isEqualTo("1234");
    }

    @Test
    public void testGivenPersonNameIsSetWhenGetNameThenNameReturned() {
        PersonView person = new PersonView();
        person.setName("Aitor");

        String name = person.getName();

        assertThat(name).isEqualTo("Aitor");
    }

    @Test
    public void testGivenPersonDateOfBithIsSetWhenGetDateOfBirthThenNameReturned() {
        PersonView person = new PersonView();
        person.setDateOfBirth(12345L);

        long dateOfBirth = person.getDateOfBirth();

        assertThat(dateOfBirth).isEqualTo(12345);
    }


}
