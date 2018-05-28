package com.beeva.resources;

import com.beeva.db.Repository;
import com.beeva.exceptions.PersonNotFoundException;
import com.beeva.model.Person;
import com.beeva.views.PersonView;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PersonResourceUnitTest {

    @Mock private Repository repository;
    @InjectMocks private PersonResource resource;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenRepositoryEmptyWhenGetAllThenEmptyListReturned() {
        when(repository.getAll()).thenReturn(Lists.newArrayList());

        List<PersonView> items = resource.getAll(null);

        assertThat(items).isEmpty();
        verify(repository).getAll();

    }

    @Test
    public void givenRepositoryOneElementWhenGetAllThenListWithOneElementReturned() {

        Person person = mock(Person.class);
        PersonView view = mock(PersonView.class);
        when(person.toView()).thenReturn(view);

        List<Person> repositoryList = Lists.newArrayList();
        repositoryList.add(person);

        doReturn(repositoryList).when(repository).getAll();

        List<PersonView> items = resource.getAll(null);

        assertThat(items).hasSize(1);
        assertThat(items.get(0)).isEqualTo(view);
        verify(repository).getAll();


    }

    @Test
    public void givenExistingIdWhenGetPersonThenPersonReturned() throws PersonNotFoundException {

        PersonView view = mock(PersonView.class);
        Person person = mock(Person.class);
        when(person.toView()).thenReturn(view);

        doReturn(person).when(repository).getPerson(anyString());

        Response response = resource.getPerson("7");
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getEntity()).isEqualTo(view);

        verify(repository).getPerson("7");

    }

    @Test
    public void givenNonExistingIdWhenGetPersonThen404Returned() throws PersonNotFoundException {

        doThrow(PersonNotFoundException.class).when(repository).getPerson(anyString());

        Response response = resource.getPerson("7");
        assertThat(response.getStatus()).isEqualTo(404);

        verify(repository).getPerson("7");

    }
}
