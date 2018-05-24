package com.beeva.db;

import java.util.List;

import com.beeva.exceptions.PersonNotFoundException;
import com.beeva.model.Person;

public interface Repository {
	
	public List<Person> getAll();
	
	public Person getPerson(String personId) throws PersonNotFoundException;
	
	public Person savePerson(Person person);

}
