package com.donus.movies.api;

import com.donus.movies.api.request.MovieRequest;
import com.donus.movies.model.Movie;
import com.donus.movies.model.dto.MovieDTO;
import com.donus.movies.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MoviesController {
  @Autowired
  private MoviesService service;

  @GetMapping
  public ResponseEntity<List<MovieDTO>> index() {
    return ResponseEntity.ok(service.getMovies());
  }

  @PostMapping
  public ResponseEntity post(@RequestBody MovieRequest movie) {
    MovieDTO movieSaved = service.save(movie);
    URI location = getUri(movieSaved.getId());

    return ResponseEntity.created(location).build();
  }

  private URI getUri(Long id) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
  }
}
