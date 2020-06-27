package com.donus.movies.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Data;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
public class MovieRequest {
  private Long id;

  private String title;

  @JsonFormat(pattern="yyyy-MM-dd")
  private Date releaseDate;

  private Boolean censured;

  @NotNull
  private Long directorId;

  private List<Long> cast = Arrays.asList();
}
