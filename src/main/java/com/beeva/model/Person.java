package com.beeva.model;

import com.beeva.views.PersonView;

public class Person {

    private String id;
    private String name;
    private Long dateOfBirth;

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

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public PersonView toView() {
        PersonView view = new PersonView();
        view.setName(name);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        return dateOfBirth != null ? dateOfBirth.equals(person.dateOfBirth) : person.dateOfBirth == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        return result;
    }
}
