package com.donus.movies.api;

import com.donus.movies.MoviesApplication;
import com.donus.movies.api.request.MovieRequest;
import com.donus.movies.model.Movie;
import com.donus.movies.model.Person;
import com.donus.movies.model.dto.MovieDTO;
import com.donus.movies.model.repository.PeopleRepository;
import com.donus.movies.service.MoviesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes = MoviesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MoviesControllerTest {
    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    private MoviesService service;

    @Autowired
	  private PeopleRepository peopleRepository;

    private ResponseEntity<MovieDTO> getMovie(String url) {
        return rest.getForEntity(url, MovieDTO.class);
    }

    private ResponseEntity<List<MovieDTO>> getMovies(String url) {
        return rest.exchange(
            url,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<MovieDTO>>() {}
        );
    }

    private ResponseEntity postMovie(MovieRequest movieRequest) {
        return rest.postForEntity("/api/v1/movies", movieRequest, null);
    }

    private MovieRequest setupMovie(String movieName){
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
    public void testSave() {
        MovieRequest movieRequest = setupMovie("Avengers");

        ResponseEntity response = postMovie(movieRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        String location = response.getHeaders().get("location").get(0);
        MovieDTO movie = getMovie(location).getBody();

        assertNotNull(movie);
        assertEquals("Avengers", movie.getTitle());
    }

    @Test
    public void testUpdate() {
        MovieRequest movieRequest = setupMovie("Avengers");

        ResponseEntity response = postMovie(movieRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        String location = response.getHeaders().get("location").get(0);
        MovieDTO movie = getMovie(location).getBody();

        assertNotNull(movie);
        assertEquals("Avengers", movie.getTitle());

        movieRequest.setTitle("Avengers Updated");

        rest.put("/api/v1/movies/1", movieRequest);
        MovieDTO movieUpdated = getMovie(location).getBody();

        assertNotNull(movieUpdated);
        assertEquals("Avengers Updated", movieUpdated.getTitle());
    }

    @Test
    public void testSaveWithNonexistentCast() {
        MovieRequest movieRequest = setupMovie("Avengers");
        movieRequest.setCast(Arrays.asList(10000000L));

        ResponseEntity response = postMovie(movieRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testSaveWithNonexistentDirector() {
        MovieRequest movieRequest = setupMovie("Avengers");
  	    movieRequest.setDirectorId(100000L);

        ResponseEntity response = postMovie(movieRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteMovie() {
        MovieRequest movieRequest = setupMovie("Avengers");
        postMovie(movieRequest);

        rest.delete("/api/v1/movies/1");
        ResponseEntity response = getMovie("/api/v1/movies/1");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testList() {
        List<MovieDTO> movies = getMovies("/api/v1/movies").getBody();
        assertNotNull(movies);
    }

    @Test
    public void testGetOk() {
        MovieRequest movieRequest = setupMovie("Avengers");
        postMovie(movieRequest);

        ResponseEntity<MovieDTO> response = getMovie("/api/v1/movies/1");
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        MovieDTO movie = response.getBody();
        assertEquals("Avengers", movie.getTitle());
    }

    @Test
    public void testGetNotFound() {
        ResponseEntity response = getMovie("/api/v1/movies/1");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}