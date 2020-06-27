package com.donus.movies.api;

import com.donus.movies.MoviesApplication;
import com.donus.movies.model.Person;
import com.donus.movies.model.dto.PersonDTO;
import com.donus.movies.model.repository.PeopleRepository;
import com.donus.movies.service.PeopleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import java.util.List;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes = MoviesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PeopleControllerTest {
  @Autowired
  protected TestRestTemplate rest;

  @Autowired
  private PeopleService service;

  private ResponseEntity<PersonDTO> getPerson(String url) {
    return rest.getForEntity(url, PersonDTO.class);
  }

  private ResponseEntity<List<PersonDTO>> getPeople(String url) {
    return rest.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<PersonDTO>>() {
        });
  }

  @Test
  public void testSave() {
    Person personFixture = new Person("Chris Evans");

    // Insert
    ResponseEntity response = rest.postForEntity("/api/v1/people", personFixture, null);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    String location = response.getHeaders().get("location").get(0);
    PersonDTO person = getPerson(location).getBody();

    assertNotNull(person);
    assertEquals("Chris Evans", person.getName());
  }

  @Test
  public void testList() {
    ResponseEntity response = rest.postForEntity("/api/v1/people", new Person("Chris Evans"), null);
    List<PersonDTO> people = getPeople("/api/v1/people").getBody();
    assertNotNull(people);
  }

  @Test
  public void testGetOk() {
    rest.postForEntity("/api/v1/people", new Person("Chris Evans"), null);

    ResponseEntity<PersonDTO> response = getPerson("/api/v1/people/1");
    assertEquals(response.getStatusCode(), HttpStatus.OK);

    PersonDTO person = response.getBody();
    assertEquals("Chris Evans", person.getName());
  }

  @Test
  public void testGetNotFound() {
    ResponseEntity response = getPerson("/api/v1/people/1");
    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
  }
}
