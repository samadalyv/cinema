package com.example.demo.announcement.controller;

import com.example.demo.announcement.dto.request.AnnouncementRequest;
import com.example.demo.announcement.dto.response.AnnouncementResponse;
import com.example.demo.announcement.dto.response.AnnouncementShortResponse;
import com.example.demo.announcement.service.AnnouncementService;
import com.example.demo.common.SuccessMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/announce")
public class AnnouncementController {


    private final AnnouncementService service;

    @PostMapping("/add-announcement")
    public ResponseEntity<SuccessMessage> addAnnouncement(@Valid @RequestBody AnnouncementRequest request) {
        return ResponseEntity.ok(service.addAnnouncement(request));
    }


    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<AnnouncementResponse> getAnnouncementById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAnnouncementById(id));
    }

    @GetMapping("/get-short-announcement")
    public ResponseEntity<Page<AnnouncementShortResponse>> getShortAnnouncementById(Pageable pageable) {
        return ResponseEntity.ok(service.getShortAnnouncement(pageable));
    }

    @PutMapping("/update-announcement-movie/{id}")
    public ResponseEntity<AnnouncementResponse> updateAnnouncement(@PathVariable Long id,
                                                                   @RequestParam Long movieId) {
        return ResponseEntity.ok(service.updateAnnouncementMovie(id, movieId));
    }



}
