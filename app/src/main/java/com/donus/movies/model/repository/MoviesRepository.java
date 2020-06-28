package com.donus.movies.model.repository;

import com.donus.movies.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MoviesRepository extends JpaRepository<Movie, Long> {
  Optional<Movie> findByTitle(String title);

  @Query("SELECT m FROM Movie m WHERE m.censured = :censured or m.censured is not null")
  public List<Movie> searchBy(@Param("censured") Optional<Boolean> censured);
}
