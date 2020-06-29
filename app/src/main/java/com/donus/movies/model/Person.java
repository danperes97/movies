package com.donus.movies.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "people")
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(hidden = true)
  private Long id;

  @NotNull(message = "Name is mandatory!")
  private String name;

  public Person(String name) { this.name = name; }

  public Person(){}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Person person = (Person) o;

    return id.equals(person.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
