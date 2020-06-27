package com.donus.movies.api;

import com.donus.movies.model.Person;
import com.donus.movies.model.dto.PersonDTO;
import com.donus.movies.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
public class PeopleController {
  @Autowired
  private PeopleService service;

  @GetMapping
  public ResponseEntity<List<PersonDTO>> index() {
    return ResponseEntity.ok(service.getPeople());
  }

  @GetMapping("/{id}")
  public ResponseEntity get(@PathVariable("id") Long id) {
    return ResponseEntity.ok(service.findPersonById(id));
  }

  @PostMapping
  public ResponseEntity post(@RequestBody Person person) {
    PersonDTO personSaved = service.save(person);
    URI location = getUri(personSaved.getId());

    return ResponseEntity.created(location).build();
  }

  private URI getUri(Long id) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
  }

  @PutMapping("/{id}")
  public ResponseEntity put(@PathVariable("id") Long id, @RequestBody Person person) {
    return ResponseEntity.ok(service.update(id, person));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable("id") Long id) {
    service.deletePersonById(id);
    return ResponseEntity.ok().build();
  }
}
