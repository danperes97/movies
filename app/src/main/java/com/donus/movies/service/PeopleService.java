package com.donus.movies.service;

import com.donus.movies.model.Person;
import com.donus.movies.model.dto.PersonDTO;
import com.donus.movies.model.exception.AlreadyExistsException;
import com.donus.movies.model.exception.ObjectNotFoundException;
import com.donus.movies.model.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PeopleService {
  @Autowired
  private PeopleRepository repository;

  public List<PersonDTO> getPeople() {
    return listToPersonDTO(repository.findAll());
  }

  public PersonDTO save(Person personRequest) {
    if(findPersonByName(personRequest.getName()).isPresent()) {
      throw new AlreadyExistsException("This person already exists");
    } else {
      return PersonDTO.create(repository.save(personRequest));
    }
  }

  public Optional<Person> findById(Long id) {
    return repository.findById(id);
  }

  private Optional<PersonDTO> findPersonByName(String name) {
    return repository.findByName(name).map(PersonDTO::create);
  }

  public PersonDTO update(Long id, Person personRequest) {
    Assert.notNull(id, "Not possible update the registry");

    return repository.findById(id).map(person -> {
      person.setName(personRequest.getName());

      repository.save(person);
      return PersonDTO.create(person);
    }).orElseThrow(() -> new RuntimeException("Not possible update the registry"));
  }

  public void deletePersonById(Long id) {
    repository.deleteById(id);
  }

  public PersonDTO findPersonById(Long id) {
    return repository.findById(id)
        .map(PersonDTO::create)
        .orElseThrow(() -> new ObjectNotFoundException("Person not found!"));
  }

  private List<PersonDTO> listToPersonDTO(List<Person> people) {
    return people.stream().map(PersonDTO::create).collect(Collectors.toList());
  }
}
