package com.netflix.sandBox.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.sandBox.modal.Movie;
import com.netflix.sandBox.service.api.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
    private final ObjectMapper objectMapper;
    private Map<Long, Movie> movies;
    private final String PATH = "films.json";

    @Override
    public List<Movie> getAll() {
        return new ArrayList<>(movies.values());
    }

    @Override
    public Movie getById(Long id) {
        return movies.get(id);
    }

    @Override
    public Movie save(Movie movie) {
        long random = 1L;
        while (movies.containsKey(random)) {
            random = new Random().nextLong();
        }
        movie.setId(random);
        movies.put(random, movie);
        return movie;
    }

    @Override
    public void delete(Long id) {
        movies.remove(id);
    }

    @Override
    public Movie update(Movie movie) {
        movies.put(movie.getId(), movie);
        return movie;
    }

    @PostConstruct
    private void readFromFile() {
        try {
            List<Movie> movieList = objectMapper.readerForListOf(Movie.class).readValue(Paths.get(PATH).toFile());
            movies = movieList.stream().collect(Collectors.toMap(Movie::getId, Function.identity()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    private void writeToFile() {
        try {
            String s = objectMapper.writeValueAsString(movies);
            objectMapper.writeValue(Paths.get(PATH).toFile(), movies.values());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
