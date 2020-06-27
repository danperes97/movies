package com.donus.movies.service;

import com.donus.movies.api.request.MovieRequest;
import com.donus.movies.model.Movie;
import com.donus.movies.model.Person;
import com.donus.movies.model.dto.MovieDTO;
import com.donus.movies.model.exception.AlreadyExistsException;
import com.donus.movies.model.exception.ObjectNotFoundException;
import com.donus.movies.model.repository.MoviesRepository;
import com.donus.movies.model.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.donus.movies.model.exception.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MoviesService {
  @Autowired
  private MoviesRepository repository;

  @Autowired
  private PeopleRepository peopleRepository;

  public List<MovieDTO> getMovies(Optional<Boolean> censured) {
     return listToMovieDTO(repository.searchBy(censured));
  }

  public MovieDTO save(MovieRequest movieRequest) {
    Movie movie = movieRequestToMovie(movieRequest);

    if(findMovieByName(movie.getTitle()).isPresent()) {
      throw new AlreadyExistsException("This movie already exists");
    } else {
      return MovieDTO.create(repository.save(movie));
    }
  }

  private Movie movieRequestToMovie(MovieRequest movieRequest) {
    if (movieRequest.getCast() != null && movieRequest.getCast().size() >= 10) throw new ValidationException("Cast too big, the max limit is: 10");

    List<Person> cast = movieRequest.getCast().stream().map(id ->
      peopleRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Person not found:" + id))
    ).collect(Collectors.toList());

    Person director = peopleRepository.findById(movieRequest.getDirectorId()).orElseThrow(() -> new ObjectNotFoundException("Person not found!"));

    Movie movie = new Movie();
    movie.setTitle(movieRequest.getTitle());
    movie.setReleaseDate(movieRequest.getReleaseDate());
    movie.setCensured(movieRequest.getCensured());
//    movie.setDirector(director);
//    movie.setCast(cast);

    return movie;
  }

  public Optional<MovieDTO> findMovieByName(String title) {
    return repository.findByTitle(title).map(MovieDTO::create);
  }

  private List<MovieDTO> listToMovieDTO(List<Movie> movies) {
    return movies.stream().map(MovieDTO::create).collect(Collectors.toList());
  }
}
