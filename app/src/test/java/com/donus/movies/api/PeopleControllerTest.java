package com.donus.movies.api;

import com.donus.movies.MoviesApplication;
import com.donus.movies.model.Person;
import com.donus.movies.model.dto.PersonDTO;
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

  private ResponseEntity<PersonDTO> getPerson(String url) {
    return rest.getForEntity(url, PersonDTO.class);
  }

  private ResponseEntity<List<PersonDTO>> getPeople(String url) {
    return rest.exchange(
        url,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<PersonDTO>>() {});
  }

  private ResponseEntity postPerson(Person person) {
        return rest.postForEntity("/api/v1/people", person, null);
    }

  @Test
  public void testSave() {
    Person personFixture = new Person("Chris Evans");

    ResponseEntity response = postPerson(personFixture);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    String location = response.getHeaders().get("location").get(0);
    PersonDTO person = getPerson(location).getBody();

    assertNotNull(person);
    assertEquals("Chris Evans", person.getName());
  }

  @Test
  public void testUpdate() {
    Person personFixture = new Person("Chris Evans");

    ResponseEntity response = postPerson(personFixture);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    String location = response.getHeaders().get("location").get(0);
    PersonDTO person = getPerson(location).getBody();

    assertNotNull(person);
    assertEquals("Chris Evans", person.getName());

    person.setName("Chris Evans Updated");

    rest.put("/api/v1/people/1", person);
    PersonDTO personUpdated = getPerson(location).getBody();

    assertNotNull(personUpdated);
    assertEquals("Chris Evans Updated", personUpdated.getName());
  }

  @Test
  public void testDeleteMovie() {
    Person personFixture = new Person("Chris Evans");
    postPerson(personFixture);

    rest.delete("/api/v1/people/1");
    ResponseEntity response = getPerson("/api/v1/people/1");
    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
  }

  @Test
  public void testList() {
    postPerson(new Person("Chris Evans"));
    List<PersonDTO> people = getPeople("/api/v1/people").getBody();
    assertNotNull(people);
  }

  @Test
  public void testGetOk() {
    postPerson(new Person("Chris Evans"));

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
