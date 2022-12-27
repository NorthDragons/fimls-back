package com.netflix.sandBox.service;

import com.netflix.sandBox.dao.MovieDaoImpl;
import com.netflix.sandBox.exception.MovieNotFoundException;
import com.netflix.sandBox.modal.Movie;
import com.netflix.sandBox.service.api.MovieService;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MovieServiceImpl implements MovieService {
    private final Logger log = Logger.getLogger(MovieServiceImpl.class);
    private final MovieDaoImpl movieDao;

    @Override
    public Collection<Movie> getAll(List<String> genres) {
        Collection<Movie> movies = movieDao.getAll();
        if (genres != null && !genres.isEmpty()) {
            return filterMovies(movies, genres);
        }
        return movies;
    }

    @Override
    public Movie getById(Long id) {
        Movie movie = movieDao.getById(id).orElseThrow(
                () -> new MovieNotFoundException("Movie with id: " + id + " not found"));
        return movie;
    }

    @Override
    public Movie save(Movie movie) {
        Movie savedMovie = movieDao.save(movie);
        log.info("Movie with id: " + savedMovie.getId() + " was created");
        return savedMovie;
    }

    @Override
    public void delete(Long id) {
        movieDao.delete(id);
        log.info("Movie with id: " + id + " was deleted");
    }

    @Override
    public Movie update(Movie movie) {
        movieDao.update(movie);
        log.info("Movie with id: " + movie.getId() + " was updated");
        return movie;
    }

    private Set<Movie> filterMovies(Collection<Movie> movies, List<String> genres) {
        Set<Movie> movieSet = new HashSet<>();
        for (String genre : genres) {
            Set<Movie> collect = movies.stream().filter(
                    movie -> movie.getGenres().contains(genre)).collect(Collectors.toSet());
            movieSet.addAll(collect);
        }
        return movieSet;
    }
}
