package com.example.demo.announcement.controller;

import com.example.demo.announcement.dto.request.AnnouncementRequest;
import com.example.demo.announcement.service.AnnouncementService;
import com.example.demo.common.SuccessMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/announce")
public class AnnouncementController {

    private final AnnouncementService service;

    @PostMapping("/add-announcement")
    public ResponseEntity<SuccessMessage> addAnnouncement(@Valid @RequestBody AnnouncementRequest request) {
        return ResponseEntity.ok(service.addAnnouncement(request));
    }



}
