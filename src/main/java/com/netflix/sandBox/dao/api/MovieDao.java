package com.netflix.sandBox.dao.api;

import com.netflix.sandBox.modal.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieDao {
    List<Movie> getAll();
    Optional<Movie> getById(Long id);
    Movie save(Movie movie);
    void delete(Long id);
    void update(Movie movie);
}
