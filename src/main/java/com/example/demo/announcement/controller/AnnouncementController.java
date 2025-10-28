package com.example.demo.announcement.controller;

import com.example.demo.announcement.dto.request.AnnouncementRequest;
import com.example.demo.announcement.dto.response.AnnouncementResponse;
import com.example.demo.announcement.service.AnnouncementService;
import com.example.demo.common.SuccessMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

}
