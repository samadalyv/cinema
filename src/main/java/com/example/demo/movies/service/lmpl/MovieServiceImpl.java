package com.example.demo.movies.service.lmpl;

import com.example.demo.movies.dto.request.MovieRequest;
import com.example.demo.movies.dto.response.MovieResponse;
import com.example.demo.movies.entity.Movie;
import com.example.demo.movies.mapper.MovieMapper;
import com.example.demo.movies.repository.MovieRepository;
import com.example.demo.movies.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;
    private final MovieMapper mapper;

    @Override
    public MovieResponse addMovie(MovieRequest request) {
        var MovieEntity = mapper.toMovieEntity(request);
        var newMovie = repository.save(MovieEntity);
        log.info("Movie added successfully");
        return mapper.toMovieResponse(newMovie);
    }

    @Override
    public Page<MovieResponse> getAllMovies(Pageable pageable) {
      Page<Movie> movieEntities = repository.findAll(pageable);
      log.info("All movies found");
        return movieEntities.map(mapper::toMovieResponse);
    }

    @Override
    public MovieResponse getMovieById(Long id) {
        log.info("Get movie by id: {}", id);
        return repository.findById(id)
                .map(mapper::toMovieResponse)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public MovieResponse updateMovie(MovieRequest request, Long id) {
       Movie byId = findMovieById(id);
       byId.setTitle(request.getTitle());
       repository.save(byId);
       log.info("Movie updated successfully");
       return mapper.toMovieResponse(byId);
    }

    @Override
    public void deleteMovie(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Movie findMovieById(Long id) {
        return  repository.findById(id).orElseThrow(RuntimeException::new);
    }


}
