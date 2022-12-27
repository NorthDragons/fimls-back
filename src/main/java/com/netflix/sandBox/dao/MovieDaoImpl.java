package com.netflix.sandBox.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.sandBox.dao.api.MovieDao;
import com.netflix.sandBox.exception.MovieNotFoundException;
import com.netflix.sandBox.modal.Movie;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MovieDaoImpl implements MovieDao {
    private final Logger log = Logger.getLogger(MovieDaoImpl.class);

    private final ObjectMapper objectMapper;
    private final String PATH = "films.json";
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
        return movies.values();
    }

    @Override
    public Optional<Movie> getById(Long id) {
        return Optional.of(movies.get(id));
    }

    @Override
    public Movie save(Movie movie) {
        long randomId = 1L;
        while (movies.containsKey(randomId)) {
            randomId = new Random().nextLong();
        }
        movie.setId(randomId);
        movies.put(randomId, movie);
        return movie;
    }

    @Override
    public void delete(Long id) {
        if (movies.containsKey(id)) {
            movies.remove(id);
        } else {
            throw new MovieNotFoundException("Movie with id: " + id + " not found");
        }
    }

    @Override
    public void update(Movie movie) {
        movies.put(movie.getId(), movie);
    }
}
