package com.example.demo.movies.service;

import com.example.demo.movies.dto.request.MovieRequest;
import com.example.demo.movies.dto.response.MovieResponse;
import com.example.demo.movies.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieService {

    MovieResponse addMovie(MovieRequest movieRequest);
    Page<MovieResponse> getAllMovies(Pageable pageable);
    MovieResponse getMovieById(Long id);
    MovieResponse updateMovie(MovieRequest request, Long id);
    void deleteMovie(Long id);
    Movie findMovieById(Long id);
}
