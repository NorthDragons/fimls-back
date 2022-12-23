package com.netflix.sandBox.modal;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class Movie{
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
}
