package com.example.demo.announcement.service;

import com.example.demo.announcement.dto.request.AnnouncementRequest;

public interface AnnouncementService {

    String addAnnouncement(AnnouncementRequest request);
}
