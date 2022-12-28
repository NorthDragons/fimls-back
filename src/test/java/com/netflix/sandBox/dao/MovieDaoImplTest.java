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
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MovieDaoImplTest {
    @Autowired
    private MovieDao movieDao;

    private static Movie movie = Movie.builder().title("test 3").build();

    @Test
    @Order(1)
    public void saveAssertSavedFilmIdNotNullAndCorrectTitle() {
        movie = movieDao.save(movie);
        assertAll(
                () -> assertNotNull(movie.getId()),
                () -> assertEquals(movie.getTitle(), movie.getTitle())
        );
    }

    @Test
    @Order(2)
    public void checkPreDestroySuccess() {
        Movie movie1 = movieDao.getById(movie.getId());
        assertAll(
                () -> assertNotNull(movie1),
                () -> assertEquals(movie, movie1)
        );
    }

    @Test
    @Order(3)
    public void deleteAssertSuccessWithNoExceptions() {
        assertDoesNotThrow(() -> movieDao.delete(movie.getId()));
    }

    @Test
    @Order(4)
    public void deleteWithWrongIdAssertException() {
        assertThrows(MovieNotFoundException.class, () -> movieDao.delete(null));
    }
}
