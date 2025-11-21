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
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                    return hallDetail;
                })
                .collect(Collectors.toSet());
        hallDetailRepository.saveAll(hallDetails);
        announcement.setHallDetails(hallDetails);
        repository.save(announcement);
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
        Page<Announcement> page = repository.findAll(pageable);

        return page.map(this::toShortResponse);
    }

    @Override
    public AnnouncementResponse updateAnnouncement(AnnouncementRequest request, Long id) {
        var byId = findAnnouncementById(id);
        hallDetailRepository.deleteAll(byId.getHallDetails());
        var announcement = findAnnouncementById(id);
        hallDetailRepository.deleteAll(announcement.getHallDetails());

        var newHallDetails = request.getHallDetails().stream()
                .map(this::toHallDetailEntity)
                .collect(Collectors.toSet());

        hallDetailRepository.saveAll(newHallDetails);
        announcement.setHallDetails(newHallDetails);
        repository.save(announcement);
        log.info("Announcement updated successfully");

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

    private AnnouncementShortResponse toShortResponse(Announcement announcement) {

        AnnouncementShortResponse response = new AnnouncementShortResponse();

        var hallDetail = announcement.getHallDetails().stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hall not found"));

        return AnnouncementShortResponse.builder()
                .id(announcement.getId())
                .movieName(announcement.getMovie().getTitle())
                .hallName(hallDetail.getHall().getName())
                .price(hallDetail.getPrice())
                .build();
    }

    private HallDetail toHallDetailEntity(HallDetailRequest hd) {
        var hallDetail = new HallDetail();
        hallDetail.setHall(hallService.findHallById(hd.getHallId()));
        hallDetail.setShowDate(hd.getShowDate());
        hallDetail.setStartTime(hd.getStartTime());
        hallDetail.setEndTime(hd.getEndTime());
        hallDetail.setPrice(hd.getPrice());
        return hallDetail;
    }
}
