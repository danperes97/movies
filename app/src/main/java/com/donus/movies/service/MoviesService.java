package com.donus.movies.service;

import com.donus.movies.api.request.MovieRequest;
import com.donus.movies.model.Movie;
import com.donus.movies.model.dto.MovieDTO;
import com.donus.movies.model.exception.AlreadyExistsException;
import com.donus.movies.model.exception.ObjectNotFoundException;
import com.donus.movies.model.repository.MoviesRepository;
import com.donus.movies.model.repository.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
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
    Movie movie = movieRequest.toMovie();

    if(findMovieByName(movie.getTitle()).isPresent()) {
      throw new AlreadyExistsException("This movie already exists");
    } else {
      return MovieDTO.create(repository.save(movie));
    }
  }

  public MovieDTO update(Long id, MovieRequest movieRequest) {
    Assert.notNull(id, "Not possible update the registry");

    return repository.findById(id).map(movie -> {
      movie.setTitle(movieRequest.getTitle());
      movie.setReleaseDate(movieRequest.getReleaseDate());
      movie.setCensured(movieRequest.getCensured());

      repository.save(movie);
      return MovieDTO.create(movie);
    }).orElseThrow(() -> new RuntimeException("Not possible update the registry"));
  }

  public void deleteMovieById(Long id) {
    repository.deleteById(id);
  }

  public Optional<Movie> findMovieByName(String title) {
    return repository.findByTitle(title);
  }

  public MovieDTO findMovieById(Long id) {
    return repository.findById(id)
        .map(MovieDTO::create)
        .orElseThrow(() -> new ObjectNotFoundException("Movie not found!"));
  }

  private List<MovieDTO> listToMovieDTO(List<Movie> movies) {
    return movies.stream().map(MovieDTO::create).collect(Collectors.toList());
  }
}
