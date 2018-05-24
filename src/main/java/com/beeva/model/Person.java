package com.beeva.model;

import com.beeva.views.PersonView;

public class Person {

    private String id;
    private String name;
    private Integer dateOfBirth;

    public Person() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Integer dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public PersonView toView() {
        PersonView view = new PersonView();
        view.setName(this.name);
        view.setId(id);
        view.setDateOfBirth(dateOfBirth);

        return view;
    }

    public static Person fromView(PersonView view) {
        Person person = new Person();
        person.setName(view.getName());
        person.setDateOfBirth(view.getDateOfBirth());

        return person;

    }

}
