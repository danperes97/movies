package com.donus.movies.model.repository;

import com.donus.movies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoviesRepository extends JpaRepository<Movie, Long> {
  Optional<Movie> findByTitle(String title);
}
