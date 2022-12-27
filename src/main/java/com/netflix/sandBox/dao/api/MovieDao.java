package com.netflix.sandBox.dao.api;

import com.netflix.sandBox.modal.Movie;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MovieDao {
    Collection<Movie> getAll();

    Optional<Movie> getById(Long id);

    Movie save(Movie movie);

    void delete(Long id);

    void update(Movie movie);
}
