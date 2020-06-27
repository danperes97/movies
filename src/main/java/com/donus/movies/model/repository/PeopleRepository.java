package com.donus.movies.model.repository;

import com.donus.movies.model.Movie;
import com.donus.movies.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PeopleRepository extends JpaRepository<Person, Long> {
  Optional<Person> findByName(String name);
}
