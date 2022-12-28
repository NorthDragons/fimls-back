package com.netflix.sandBox.service;

import com.netflix.sandBox.dao.MovieDaoImpl;
import com.netflix.sandBox.modal.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class MovieServiceImplTest {
    @Mock
    private MovieDaoImpl movieDao;
    @InjectMocks
    private MovieServiceImpl movieService;

    private final List<Movie> moviesMock = new ArrayList<>();

    public MovieServiceImplTest() {
        moviesMock.add(Movie.builder().id(1L).genres(List.of("Genre to test 1")).title("test1").build());
        moviesMock.add(Movie.builder().id(2L).genres(List.of("Detective")).title("test2").build());
        moviesMock.add(Movie.builder().id(3L).genres(List.of("Criminal")).title("test3").build());
    }

    @Test
    public void getAllAssertAllMovies() {
        Mockito.when(movieDao.getAll()).thenReturn(moviesMock);
        Collection<Movie> all = movieService.getAll(null);
        assertNotNull(all);
        assertEquals(moviesMock.size(), all.size());
    }

    @Test
    public void getAllWithSortingAssertSortedListWithOneElem() {
        Mockito.when(movieDao.getAll()).thenReturn(moviesMock);
        List<String> genres = new ArrayList<>();
        genres.add("Detective");
        List<Movie> all = new ArrayList<>(movieService.getAll(genres));
        assertNotNull(all);
        assertEquals(moviesMock.get(1), all.get(0));
        assertEquals(1, all.size());
    }

    @Test
    public void getByIdAssertNotNullAndReturnedCorrectMovie() {
        Long id = moviesMock.get(0).getId();
        Mockito.when(movieDao.getById(id)).thenReturn(Optional.of(moviesMock.get(0)));
        Movie byId = movieService.getById(id);
        assertNotNull(byId);
        assertEquals(id, byId.getId());
        assertEquals(moviesMock.get(0), byId);
    }
}
