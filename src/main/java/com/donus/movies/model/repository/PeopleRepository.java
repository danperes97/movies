package com.donus.movies.model.repository;

import com.donus.movies.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long>, QuerydslPredicateExecutor<Person> {
  Optional<Person> findByName(String name);
}
