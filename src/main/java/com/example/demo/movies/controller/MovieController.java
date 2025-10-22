package com.example.demo.movies.controller;

import com.example.demo.hall.model.dto.response.HallResponse;
import com.example.demo.movies.dto.request.MovieRequest;
import com.example.demo.movies.dto.response.MovieResponse;
import com.example.demo.movies.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/addMovie")
    public ResponseEntity<MovieResponse> addMovie(@RequestBody MovieRequest movieRequest) {
        return ResponseEntity.ok(movieService.addMovie(movieRequest));
    }

    @GetMapping("/allmovie")
    public ResponseEntity<Page<MovieResponse>> getMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<MovieResponse> movies = movieService.getAllMovies(pageable);
        return ResponseEntity.ok(movies);
    }


    // todo

    @GetMapping("/byId/{id}")
    public ResponseEntity<MovieResponse> getMovie(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id,
                                                     @RequestBody MovieRequest request) {
        return ResponseEntity.ok(movieService.updateMovie(request,id));
    }


    @DeleteMapping("/delete/{id}")
    public void deleteMovie(@PathVariable Long id) {
    movieService.deleteMovie(id);
    }
}
