package com.donus.movies.service;

import com.donus.movies.model.Person;
import com.donus.movies.model.dto.PersonDTO;
import com.donus.movies.model.exception.AlreadyExistsException;
import com.donus.movies.model.exception.ObjectNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class PeopleServiceTest {
  @Autowired
	private PeopleService peopleService;

	@Test
	public void createPersonTest() {
		peopleService.save(new Person("Chris Evans"));

	  PersonDTO person = peopleService.findPersonById(1L);
	  assertEquals("Chris Evans", person.getName());
  }

  @Test
	public void createWithSameNamePersonTest() {
  	Person chrisEvans = new Person("Chris Evans");
  	Person chrisEvans2 = new Person("Chris Evans");

  	peopleService.save(chrisEvans);
  	assertThrows(AlreadyExistsException.class, () -> {
			peopleService.save(chrisEvans2);
		});
  }

  @Test
	public void updatePersonTest() {
		PersonDTO person = peopleService.save(new Person("Chris Evans"));

	  Person personFound = peopleService.findById(person.getId()).get();
	  assertEquals(personFound.getName(), person.getName());

	  personFound.setName("Chris Evans Updated");

	  peopleService.update(person.getId(), personFound);
	  Person personUpdated = peopleService.findById(person.getId()).get();
	  assertEquals(personUpdated.getName(), "Chris Evans Updated");
  }

  @Test
	public void deleteMovieTest() {
		PersonDTO person = peopleService.save(new Person("Chris Evans"));

	  peopleService.findById(person.getId()).get();
		peopleService.deletePersonById(person.getId());

		assertThrows(NoSuchElementException.class, () -> {
			peopleService.findById(person.getId()).get();
		});
  }
}
