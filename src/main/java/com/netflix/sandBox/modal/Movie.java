package com.netflix.sandBox.modal;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Movie {
    private Long id;
    private String title;
    private String tagline;
    private Double vote_average;
    private Long vote_count;
    private LocalDate release_date;
    private String poster_path;
    private String overview;
    private Long budget;
    private Long revenue;
    private List<String> genres;
    private Long runtime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return id.equals(movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
