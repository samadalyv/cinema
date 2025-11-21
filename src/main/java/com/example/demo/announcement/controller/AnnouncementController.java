package com.example.demo.announcement.controller;

import com.example.demo.announcement.dto.request.AnnouncementRequest;
import com.example.demo.announcement.dto.response.AnnouncementResponse;
import com.example.demo.announcement.dto.response.AnnouncementShortResponse;
import com.example.demo.announcement.service.AnnouncementService;
import com.example.demo.common.SuccessMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<Page<AnnouncementShortResponse>> getShortAnnouncementById(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<AnnouncementShortResponse> shortAnnouncement = service.getShortAnnouncement(pageable);
        return ResponseEntity.ok(shortAnnouncement);
    }

}
