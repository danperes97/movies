package com.donus.movies.api.request;

import com.donus.movies.model.Movie;
import com.donus.movies.model.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MovieRequest {
  private String title;

  @JsonFormat(pattern="yyyy-MM-dd")
  private Date releaseDate;

  private Boolean censured;

  @NotNull
  private Long directorId;

  private List<Long> cast = Collections.emptyList();

  public Movie toMovie() {
    List<Person> cast = this.getCast().stream().map(id -> {
      Person person = new Person();
      person.setId(id);

      return person;
    }).collect(Collectors.toList());

    Movie movie = new Movie();
    movie.setTitle(this.getTitle());
    movie.setReleaseDate(this.getReleaseDate());
    movie.setCensured(this.getCensured());
    movie.setDirectorId(directorId);
    movie.setCast(cast);

    return movie;
  }
}
