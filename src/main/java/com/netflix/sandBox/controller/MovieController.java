package com.netflix.sandBox.controller;

import com.netflix.sandBox.modal.Movie;
import com.netflix.sandBox.service.api.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/film")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping()
    public ResponseEntity<List<Movie>> getAll() {
        List<Movie> movies = movieService.getAll();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable("id") Long id) {
        Movie movie = movieService.getById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Movie> save(@RequestBody Movie movie) {
        Movie savedMovie = movieService.save(movie);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable("id") Long id, @RequestBody Movie movie) {
        Movie updatedMovie = movieService.update(id, movie);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        movieService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
