package com.donus.movies.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Data
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;
}
