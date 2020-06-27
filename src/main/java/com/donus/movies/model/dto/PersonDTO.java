package com.donus.movies.model.dto;

import com.donus.movies.model.Person;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class PersonDTO {
    private Long id;

    private String name;

    public static PersonDTO create(Person person) {
        return new ModelMapper().map(person, PersonDTO.class);
    }
}
