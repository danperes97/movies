package com.donus.movies.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "people")
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "Name is mandatory!")
  private String name;

  public Person(String name) {
    this.name = name;
  }

  public Person(){}
}
