package com.netflix.sandBox.dao;

import com.netflix.sandBox.dao.api.MovieDao;
import com.netflix.sandBox.exception.MovieNotFoundException;
import com.netflix.sandBox.modal.Movie;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieDaoImplTest {
    private final MovieDao movieDao;

    @Autowired
    @Lazy
    public MovieDaoImplTest(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    private static Integer preDestroySize;
    private static Movie movie = Movie.builder().title("test 3").build();

    @Test
    public void checkPostConstructAssertNotEmpty() {
        Collection<Movie> all = movieDao.getAll();
        assertFalse(all.isEmpty());
    }


    @Test
    @Order(1)
    public void saveAssertSavedFilmIdNotNullAndCorrectTitle() {
        preDestroySize = movieDao.getAll().size();
        movie = movieDao.save(movie);
        assertAll(
                () -> assertNotNull(movie.getId()),
                () -> assertEquals(movie.getTitle(), movie.getTitle())
        );
    }

    @Test
    @Order(2)
    public void checkPreDestroySuccess() {
        Collection<Movie> all = movieDao.getAll();
        assertNotEquals(preDestroySize, all.size());
    }

    @Test
    @Order(3)
    public void deleteAssertSuccessWithNoExceptions() {
        assertDoesNotThrow(() -> movieDao.delete(movie.getId()));
    }

    @Test
    @Order(4)
    public void deleteWithWrongIdAssertException() {
        assertThrows(MovieNotFoundException.class, () -> movieDao.delete(movie.getId()));
    }
}
