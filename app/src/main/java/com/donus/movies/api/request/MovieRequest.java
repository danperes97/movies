package com.donus.movies.api.request;

import com.donus.movies.model.Movie;
import com.donus.movies.model.Person;
import com.donus.movies.model.dto.MovieDTO;
import com.donus.movies.model.exception.CreationObjectNotFoundException;
import com.donus.movies.model.exception.ValidationException;
import com.donus.movies.model.repository.PeopleRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class MovieRequest {
  private String title;

  @JsonFormat(pattern="yyyy-MM-dd")
  private Date releaseDate;

  private Boolean censured;

  @NotNull
  private Long directorId;

  private List<Long> cast = Arrays.asList();

  public Movie toMovie() {
    if (this.getCast() != null && this.getCast().size() >= 10) throw new ValidationException("Cast too big, the max limit is: 10");

//    List<Person> cast = this.getCast().stream().map(id ->
//      peopleRepository.findById(id).orElseThrow(() -> new CreationObjectNotFoundException("Person not found:" + id))
//    ).collect(Collectors.toList());

//    Person director = peopleRepository.findById(this.getDirectorId()).orElseThrow(() -> new CreationObjectNotFoundException("Person not found!"));

    Movie movie = new Movie();
    movie.setTitle(this.getTitle());
    movie.setReleaseDate(this.getReleaseDate());
    movie.setCensured(this.getCensured());
//    movie.setDirector(director);
//    movie.setCast(cast);

    return movie;
  }
}
