package com.netflix.sandBox.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.sandBox.dao.api.MovieDao;
import com.netflix.sandBox.exception.MovieNotFoundException;
import com.netflix.sandBox.modal.Movie;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class MovieDaoImpl implements MovieDao {
    private final Object MOVIE_MUTEX = new Object();
    private final Logger log = Logger.getLogger(MovieDaoImpl.class);
    private final ObjectMapper objectMapper;
    @Value("${movie.path}")
    private String PATH;
    private Map<Long, Movie> movies;


    @PostConstruct
    private void readFromFile() {
        try {
            List<Movie> movieList =
                    objectMapper.readerForListOf(Movie.class).readValue(Paths.get(PATH).toFile());
            movies =
                    movieList.stream().collect(Collectors.toMap(Movie::getId, Function.identity()));
        } catch (FileNotFoundException e) {
            log.error("File not found: ", e);
            movies = new HashMap<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    private void writeToFile() {
        try {
            objectMapper.writeValue(Paths.get(PATH).toFile(), movies.values());
        } catch (IOException e) {
            log.error("Write To File IO exception: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<Movie> getAll() {
        synchronized (MOVIE_MUTEX) {
            return movies.values();
        }

    }

    @Override
    public Movie getById(Long id) {
        synchronized (MOVIE_MUTEX) {
            if (movies.containsKey(id)) {
                return movies.get(id);
            }
            throw new MovieNotFoundException("Movie with id: " + id + " not found");

        }
    }

    @Override
    public Movie save(Movie movie) {
        synchronized (MOVIE_MUTEX) {
            long randomId = 1L;
            while (movies.containsKey(randomId)) {
                randomId = new Random().nextLong();
            }
            movie.setId(randomId);
            movies.put(randomId, movie);
            return movie;
        }
    }

    @Override
    public void delete(Long id) {
        synchronized (MOVIE_MUTEX) {
            if (movies.containsKey(id)) {
                movies.remove(id);
                return;
            }
            throw new MovieNotFoundException("Movie with id: " + id + " not found");
        }
    }

    @Override
    public void update(Movie movie) {
        synchronized (MOVIE_MUTEX) {
            movies.put(movie.getId(), movie);
        }
    }
}
