package com.example.demo.announcement.service.Impl;

import com.example.demo.announcement.dto.request.AnnouncementRequest;
import com.example.demo.announcement.dto.request.HallDetailRequest;
import com.example.demo.announcement.dto.response.AnnouncementResponse;
import com.example.demo.announcement.dto.response.AnnouncementShortResponse;
import com.example.demo.announcement.dto.response.HallDetailResponse;
import com.example.demo.announcement.model.Announcement;
import com.example.demo.announcement.model.HallDetail;
import com.example.demo.announcement.repository.AnnouncementRepository;
import com.example.demo.announcement.repository.HallDetailRepository;
import com.example.demo.announcement.service.AnnouncementService;
import com.example.demo.common.SuccessMessage;
import com.example.demo.hall.model.entity.Hall;
import com.example.demo.hall.service.HallService;
import com.example.demo.movies.service.MovieService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementImpl implements AnnouncementService {

    private static final Logger log = LoggerFactory.getLogger(AnnouncementImpl.class);
    private final AnnouncementRepository repository;
    private final HallDetailRepository hallDetailRepository;
    private final MovieService movieService;
    private final HallService hallService;

    @Override
    public SuccessMessage addAnnouncement(AnnouncementRequest request) {
        var announcement = new Announcement();
        var movie = movieService.findMovieById(request.getMovieId());
        announcement.setMovie(movie);
        repository.save(announcement);
        var hallDetails = request.getHallDetails().stream()
                .map(hallDetailRequest -> {
                    alreadyExists(hallDetailRequest);

                    var hallDetail = new HallDetail();
                    Hall hall = hallService.findHallById(hallDetailRequest.getHallId());
                    hallDetail.setHall(hall);
                    hallDetail.setShowDate(hallDetailRequest.getShowDate());
                    hallDetail.setStartTime(hallDetailRequest.getStartTime());
                    hallDetail.setEndTime(hallDetailRequest.getEndTime());
                    hallDetail.setPrice(hallDetailRequest.getPrice());
                    hallDetail.setAnnouncement(announcement);
                    return hallDetail;
                })
                .collect(Collectors.toSet());

        hallDetailRepository.saveAll(hallDetails);
        return SuccessMessage.builder().message("Successfully added announcement").status(200).build();
    }

    @Override
    public AnnouncementResponse getAnnouncementById(Long id) {
        var announcement = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Announcement not found"));
        return toResponse(announcement);
    }

    @Override
    public Page<AnnouncementShortResponse> getShortAnnouncement(Pageable pageable) {
        return hallDetailRepository.findAll(pageable)
                .map(this::toShortResponse);
    }

    @Transactional
    @Override
    public AnnouncementResponse updateAnnouncementMovie(Long id, Long movieId) {
        var announcement = findAnnouncementById(id);
        var movie = movieService.findMovieById(movieId);
        announcement.setMovie(movie);
        repository.save(announcement);
        return toResponse(announcement);

    }

    @Override
    public Announcement findAnnouncementById(Long id) {
        return repository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void alreadyExists(HallDetailRequest request) {
        var exists = repository.existsByHallIdAndOverlap(request.getHallId(),
                request.getShowDate(),
                request.getStartTime(),
                request.getEndTime()
        );
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Hall is already booked for this time slot");
        }
    }

    private AnnouncementResponse toResponse(Announcement announcement) {
        AnnouncementResponse response = new AnnouncementResponse();
        response.setMovieName(announcement.getMovie().getTitle());
        var hallDetailResponses = announcement.getHallDetails().stream().
                map(hallDetail -> {
                    HallDetailResponse hallDetailResponse = new HallDetailResponse();
                    hallDetailResponse.setHallName(hallDetail.getHall().getName());
                    hallDetailResponse.setShowDate(hallDetail.getShowDate());
                    hallDetailResponse.setStartTime(hallDetail.getStartTime());
                    hallDetailResponse.setEndTime(hallDetail.getEndTime());
                    hallDetailResponse.setPrice(hallDetail.getPrice());
                    return hallDetailResponse;
                })
                .toList();
        response.setHallDetails(hallDetailResponses);
        return response;
    }

    private AnnouncementShortResponse toShortResponse(HallDetail  hallDetail) {
        var announcement = hallDetail.getAnnouncement();// null
        var movie = announcement.getMovie();

        String hallName = hallDetail.getHall().getName();
        return AnnouncementShortResponse.builder()
                .movieName(movie.getTitle())
                .id(announcement.getId())
                .hallName(hallName)
                .price(hallDetail.getPrice())
                .build();
    }

//  todo  private HallDetail toHallDetailEntity(HallDetailRequest hd) {
//        var hallDetail = new HallDetail();
//        hallDetail.setHall(hallService.findHallById(hd.getHallId()));
//        hallDetail.setShowDate(hd.getShowDate());
//        hallDetail.setStartTime(hd.getStartTime());
//        hallDetail.setEndTime(hd.getEndTime());
//        hallDetail.setPrice(hd.getPrice());
//        return hallDetail;
//    }
}
