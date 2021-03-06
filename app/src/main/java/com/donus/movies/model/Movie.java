package com.donus.movies.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "movies")
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title;

  @Column
  @JsonFormat(pattern="yyyy-MM-dd")
  private Date releaseDate;

  @Column
  private Boolean censured;

  @JoinColumn(name = "director_id", insertable = false, updatable = false)
  @ManyToOne(targetEntity = Person.class, fetch = FetchType.EAGER)
  private Person director;

  @NotNull(message = "Director is mandatory!")
  @Column(name = "director_id")
  private Long directorId;

  @Column
  @ManyToMany(fetch = FetchType.EAGER)
  @Size(max = 10, message = "Cast should'nt be bigger than 10!")
  @NotNull(message = "Cast is mandatory!")
  @JoinTable(name = "movie_cast",
      joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id", insertable = false, updatable = false),
      inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id", insertable = false, updatable = false)
  )
  private List<Person> cast;

}
