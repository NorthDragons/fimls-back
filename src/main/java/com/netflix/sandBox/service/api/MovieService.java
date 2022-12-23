package com.netflix.sandBox.service.api;

import com.netflix.sandBox.modal.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> getAll();

    Movie getById(Long id);

    Movie save(Movie movie);

    void delete(Long id);

    Movie update(Movie movie);
}
