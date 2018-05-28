package com.beeva.db;

import java.util.List;

import com.beeva.exceptions.PersonNotFoundException;
import com.beeva.model.Person;

public interface Repository {
	
	List<Person> getAll();
	
	Person getPerson(String personId) throws PersonNotFoundException;
	
	Person savePerson(Person person);

	void open();

	void close();

}
