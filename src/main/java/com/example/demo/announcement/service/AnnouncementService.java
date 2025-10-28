package com.example.demo.announcement.service;

import com.example.demo.announcement.dto.request.AnnouncementRequest;
import com.example.demo.announcement.dto.response.AnnouncementResponse;
import com.example.demo.common.SuccessMessage;

public interface AnnouncementService {

    SuccessMessage addAnnouncement(AnnouncementRequest request);
    AnnouncementResponse getAnnouncementById(Long id);
}
