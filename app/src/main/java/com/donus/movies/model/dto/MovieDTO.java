package com.donus.movies.model.dto;

import com.donus.movies.model.Movie;
import com.donus.movies.model.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.modelmapper.ModelMapper;
import java.util.Date;
import java.util.List;

@Data
public class MovieDTO {
    private Long id;

    private String title;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date releaseDate;

    private Boolean censured;

    private Person director;

    private List<Person> cast;

    public static MovieDTO create(Movie movie) {
        return new ModelMapper().map(movie, MovieDTO.class);
    }
}
