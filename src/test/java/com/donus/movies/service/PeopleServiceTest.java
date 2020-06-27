package com.donus.movies.service;

import com.donus.movies.model.Person;
import com.donus.movies.model.dto.PersonDTO;
import com.donus.movies.model.exception.AlreadyExistsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

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
}
