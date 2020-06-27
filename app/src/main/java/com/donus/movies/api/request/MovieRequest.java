package com.donus.movies.api.request;

import com.donus.movies.model.Movie;
import com.donus.movies.model.Person;
import com.donus.movies.model.dto.MovieDTO;
import com.donus.movies.model.exception.CreationObjectNotFoundException;
import com.donus.movies.model.repository.PeopleRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MovieRequest {
  @Autowired
  private PeopleRepository peopleRepository;

  private Long id;

  private String title;

  @JsonFormat(pattern="yyyy-MM-dd")
  private Date releaseDate;

  private Boolean censured;

  @NotNull
  private Long directorId;

  private List<Long> cast = Arrays.asList();

  public Movie toMovie() {
    List<Person> cast = this.getCast().stream().map(id ->
      peopleRepository.findById(id).orElseThrow(() -> new CreationObjectNotFoundException("Person not found:" + id))
    ).collect(Collectors.toList());

    Person director = peopleRepository.findById(this.getDirectorId()).orElseThrow(() -> new CreationObjectNotFoundException("Person not found!"));

    Movie movie = new Movie();
    movie.setTitle(this.getTitle());
    movie.setReleaseDate(this.getReleaseDate());
    movie.setCensured(this.getCensured());
//    movie.setDirector(director);
//    movie.setCast(cast);

    return movie;
  }
}