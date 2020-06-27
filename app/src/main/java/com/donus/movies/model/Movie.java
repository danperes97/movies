package com.donus.movies.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
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

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "person_id")
  private Person director;

  @Column
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "movie_cast",
      joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id")
  )
  private List<Person> cast;
}
