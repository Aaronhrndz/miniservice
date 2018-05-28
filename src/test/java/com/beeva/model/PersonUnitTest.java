package com.beeva.model;

import com.beeva.views.PersonView;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PersonUnitTest {

    @Test
    public void testGivenPersonIdIsSetWhenGetIdThenIDReturned() {
        Person person = new Person();
        person.setId("1234");

        String id = person.getId();

        assertThat(id).isEqualTo("1234");
    }

    @Test
    public void testGivenPersonNameIsSetWhenGetNameThenNameReturned() {
        Person person = new Person();
        person.setName("Aitor");

        String name = person.getName();

        assertThat(name).isEqualTo("Aitor");
    }

    @Test
    public void testGivenPersonDateOfBithIsSetWhenGetDateOfBirthThenNameReturned() {
        Person person = new Person();
        person.setDateOfBirth(12345L);

        long dateOfBirth = person.getDateOfBirth();

        assertThat(dateOfBirth).isEqualTo(12345);
    }


    @Test
    public void testGivenPersonWhenToViewThenViewWithAllAttributesReturned() {
        Person person = new Person();
        person.setId("7");
        person.setName("Aitor");
        person.setDateOfBirth(12345L);

        PersonView view = person.toView();

        assertThat(view.getName()).isEqualTo("Aitor");
        assertThat(view.getId()).isEqualTo("7");
        assertThat(view.getDateOfBirth()).isEqualTo(12345L);
    }


    @Test
    public void testGivenViewWhenFromViewThenPersonWithAllAttributesReturned() {
        PersonView view = new PersonView();
        view.setName("Aitor");
        view.setDateOfBirth(12345L);

        Person person = Person.fromView(view);

        assertThat(person.getName()).isEqualTo("Aitor");
        assertThat(person.getDateOfBirth()).isEqualTo(12345);
    }




}
