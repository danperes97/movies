package com.donus.movies.service;

import com.donus.movies.model.Person;
import com.donus.movies.model.dto.PersonDTO;
import com.donus.movies.model.exception.AlreadyExistsException;
import com.donus.movies.model.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
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

  public PersonDTO save(Person person) {
    if(findPersonByName(person.getName()).isPresent()) {
      throw new AlreadyExistsException("This person already exists");
    } else {
      return PersonDTO.create(repository.save(person));
    }
  }

  public Optional<Person> findById(Long id) {
    return repository.findById(id);
  }

  private Optional<PersonDTO> findPersonByName(String name) {
    return repository.findByName(name).map(PersonDTO::create);
  }

  private List<PersonDTO> listToPersonDTO(List<Person> people) {
    return people.stream().map(PersonDTO::create).collect(Collectors.toList());
  }
}
