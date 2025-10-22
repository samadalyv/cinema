package com.example.demo.announcement.service.Impl;

import com.example.demo.announcement.dto.request.AnnouncementRequest;
import com.example.demo.announcement.dto.request.HallDetailRequest;
import com.example.demo.announcement.model.Announcement;
import com.example.demo.announcement.model.HallDetail;
import com.example.demo.announcement.repository.AnnouncementRepository;
import com.example.demo.announcement.repository.HallDetailRepository;
import com.example.demo.announcement.service.AnnouncementService;
import com.example.demo.hall.model.entity.Hall;
import com.example.demo.hall.service.HallService;
import com.example.demo.movies.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementImpl implements AnnouncementService {

    private final AnnouncementRepository repository;
    private final HallDetailRepository hallDetailRepository;
    private final MovieService movieService;
    private final HallService hallService;

    @Override
    public String addAnnouncement(AnnouncementRequest request) {
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
//                    hallDetailRepository.save(hallDetail);
               return hallDetail;
                })
                .collect(Collectors.toSet());
        hallDetailRepository.saveAll(hallDetails);
        announcement.setHallDetails(hallDetails);
        repository.save(announcement);
        return "Successfully added announcement";
    }

    public void alreadyExists(HallDetailRequest request) {
            var exists = repository.existsByHallIdAndOverlap(request.getHallId(),
                    request.getShowDate(),request.getStartTime(), request.getEndTime());
    }
}
