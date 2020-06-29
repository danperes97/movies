package com.donus.movies.api;

import com.donus.movies.api.request.MovieRequest;
import com.donus.movies.model.dto.MovieDTO;
import com.donus.movies.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/movies")
public class MoviesController {
  @Autowired
  private MoviesService service;

  @GetMapping
  public ResponseEntity<List<MovieDTO>> index(@RequestParam Optional<Boolean> censured) {
    return ResponseEntity.ok(service.getMovies(censured));
  }

  @GetMapping("/{id}")
  public ResponseEntity get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findMovieById(id));
    }

  @PostMapping
  public ResponseEntity post(@RequestBody MovieRequest movie) {
    MovieDTO movieSaved = service.save(movie);
    URI location = getUri(movieSaved.getId());

    return ResponseEntity.created(location).build();
  }

  @PutMapping("/{id}")
  public ResponseEntity put(@PathVariable("id") Long id, @RequestBody MovieRequest movieRequest) {
    return ResponseEntity.ok(service.update(id, movieRequest));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable("id") Long id) {
    service.deleteMovieById(id);
    return ResponseEntity.ok().build();
  }

  private URI getUri(Long id) {
    return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
  }
}
