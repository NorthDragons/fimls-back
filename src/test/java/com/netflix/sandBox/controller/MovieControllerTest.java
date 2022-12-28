package com.netflix.sandBox.controller;

import com.netflix.sandBox.modal.Movie;
import com.netflix.sandBox.service.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {
    @Mock
    private MovieServiceImpl movieService;
    @InjectMocks
    private MovieController movieController;

    @Test
    public void getAllAssertHttpStatusOkAndBodyNotNull() {
        when(movieService.getAll(any())).thenReturn(new ArrayList<>());
        ResponseEntity<Collection<Movie>> responseEntity = movieController.getAll(null);
        Collection<Movie> body = responseEntity.getBody();
        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
                () -> assertNotNull(body)
        );
    }

    @Test
    public void getByIdAssertBodyNotNullAndResponseStatusOk() {
        when(movieService.getById(anyLong())).thenReturn(Movie.builder().build());
        ResponseEntity<Movie> responseEntity = movieController.getById(1L);
        Movie body = responseEntity.getBody();
        assertAll(
                () -> assertNotNull(body),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );
    }

    @Test
    public void saveAssertBodyAndResponseEntityNotNullAndHttpStatusOk() {
        when(movieService.save(any())).thenReturn(Movie.builder().build());
        ResponseEntity<Movie> responseEntity = movieController.save(Movie.builder().build());
        Movie body = responseEntity.getBody();
        assertAll(
                () -> assertNotNull(responseEntity),
                () -> assertNotNull(body),
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode())
        );
    }

    @Test
    public void updateAssertBodyNotNullAndResponseStatusOk() {
        when(movieService.update(any())).thenReturn(Movie.builder().build());
        ResponseEntity<Movie> responseEntity = movieController.update(Movie.builder().build());
        Movie body = responseEntity.getBody();
        assertAll(
                () -> assertNotNull(body),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );
    }

    @Test
    public void deleteAssertResponseStatusOk() {
        ResponseEntity<HttpStatus> responseEntity = movieController.delete(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
