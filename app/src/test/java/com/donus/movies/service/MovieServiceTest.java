package com.donus.movies.service;

import com.donus.movies.api.request.MovieRequest;
import com.donus.movies.model.Movie;
import com.donus.movies.model.Person;
import com.donus.movies.model.exception.AlreadyExistsException;
import com.donus.movies.model.exception.ObjectNotFoundException;
import com.donus.movies.model.repository.PeopleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
public class MovieServiceTest {
  @Autowired
	private MoviesService moviesService;

  @Autowired
	private PeopleRepository peopleRepository;

  public MovieRequest setupMovie(String movieName){
		peopleRepository.save(new Person("Chris Evans"));
		peopleRepository.save(new Person("Robert Downey Jr"));

		MovieRequest movieRequest = new MovieRequest();
    movieRequest.setCast(Arrays.asList(1L, 2L));
    movieRequest.setCensured(false);
    movieRequest.setDirectorId(1L);
    movieRequest.setTitle(movieName);
    movieRequest.setReleaseDate(new Date());

    return movieRequest;
	}

	@Test
	public void createMovieTest() {
  	MovieRequest movieRequest = setupMovie("Avengers");

		moviesService.save(movieRequest);

	  Optional<Movie> movie = moviesService.findMovieByName(movieRequest.getTitle());
	  assertEquals(movieRequest.getTitle(), movie.get().getTitle());
		assertEquals(movieRequest.getDirectorId(), movie.get().getDirector().getId());
		assertEquals(movieRequest.getCensured(), movie.get().getCensured());
		assertEquals(movieRequest.getReleaseDate(), movie.get().getReleaseDate());
		assertEquals(movie.get().getCast().stream().map(p -> p.getName()).collect(Collectors.toList()), Arrays.asList("Chris Evans", "Robert Downey Jr"));
  }

  @Test
	public void updateMovieTest() {
  	MovieRequest movieRequest = setupMovie("Avengers");
		moviesService.save(movieRequest);

	  Movie movie = moviesService.findMovieByName(movieRequest.getTitle()).get();
	  assertEquals(movieRequest.getTitle(), movie.getTitle());

	  movieRequest.setTitle("Avengers Updated");

	  moviesService.update(movie.getId(), movieRequest);
	  Movie movieUpdated = moviesService.findMovieByName(movieRequest.getTitle()).get();
	  assertEquals(movieUpdated.getTitle(), "Avengers Updated");
  }

  @Test
	public void deleteMovieTest() {
  	MovieRequest movieRequest = setupMovie("Avengers");
		moviesService.save(movieRequest);

	  Movie movie = moviesService.findMovieByName(movieRequest.getTitle()).get();
		moviesService.deleteMovieById(movie.getId());

		assertThrows(ObjectNotFoundException.class, () -> {
			moviesService.findMovieById(movie.getId());
		});
  }

  @Test
	public void createWithSameTitleMovieTest() {
  	MovieRequest movieRequest = setupMovie("Avengers");
  	MovieRequest movie2Request = setupMovie("Avengers");

		moviesService.save(movieRequest);
		assertThrows(AlreadyExistsException.class, () -> {
			moviesService.save(movie2Request);
		});
  }

  @Test
	public void createMovieWithMoreThanTenCastTest() {
  	MovieRequest movieRequest = setupMovie("Avengers");
  	movieRequest.setCast(Arrays.asList(1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L, 1L));

		assertThrows(ConstraintViolationException.class, () -> {
			moviesService.save(movieRequest);
		});
  }
  
  @Test
	public void createWithNonexistentCast() {
  	MovieRequest movieRequest = setupMovie("Avengers");
  	movieRequest.setCast(Arrays.asList(10000000L));

		assertThrows(DataIntegrityViolationException.class, () -> {
			moviesService.save(movieRequest);
		});
  }

  @Test
	public void createWithNonexistentDirector() {
  	MovieRequest movieRequest = setupMovie("Avengers");
  	movieRequest.setDirectorId(100000L);

		assertThrows(DataIntegrityViolationException.class, () -> {
			moviesService.save(movieRequest);
		});
  }
}
