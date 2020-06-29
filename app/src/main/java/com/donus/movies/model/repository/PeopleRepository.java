package com.donus.movies.model.repository;

import com.donus.movies.model.Person;
import com.donus.movies.model.exception.CreationObjectNotFoundException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {
  Optional<Person> findByName(String name);
}
