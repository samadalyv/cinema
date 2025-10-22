package com.example.demo.movies.mapper;

import com.example.demo.movies.dto.request.MovieRequest;
import com.example.demo.movies.dto.response.MovieResponse;
import com.example.demo.movies.entity.Movie;
import org.springframework.stereotype.Service;

@Service
public class MovieMapper {

    public Movie toMovieEntity(MovieRequest request) {
        if (request == null) {
            throw new NullPointerException("Movie request is null");
        }
        var entity = new Movie();
         entity.setTitle(request.getTitle());
         return entity;
    }


    public MovieResponse toMovieResponse(Movie entity) {
        var response = new MovieResponse();
        response.setId(entity.getId());
        response.setTitle(entity.getTitle());
        return response;
    }




}
