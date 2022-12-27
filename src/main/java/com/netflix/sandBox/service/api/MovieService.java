package com.netflix.sandBox.service.api;

import com.netflix.sandBox.modal.Movie;
import java.util.Collection;
import java.util.List;

public interface MovieService {
    Collection<Movie> getAll(List<String> genres);

    Movie getById(Long id);

    Movie save(Movie movie);

    void delete(Long id);

    Movie update(Movie movie);
}
