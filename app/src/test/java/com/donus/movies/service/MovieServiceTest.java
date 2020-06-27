package com.donus.movies.service;

import com.donus.movies.api.request.MovieRequest;
import com.donus.movies.model.Movie;
import com.donus.movies.model.Person;
import com.donus.movies.model.dto.MovieDTO;
import com.donus.movies.model.exception.AlreadyExistsException;
import com.donus.movies.model.repository.PeopleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
//	assertEquals(movieRequest.getDirectorId(), movie.getDirector().getId());
		assertEquals(movieRequest.getCensured(), movie.get().getCensured());
		assertEquals(movieRequest.getReleaseDate(), movie.get().getReleaseDate());
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
}
