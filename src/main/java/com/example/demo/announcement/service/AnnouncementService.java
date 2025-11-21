package com.example.demo.announcement.service;

import com.example.demo.announcement.dto.request.AnnouncementRequest;
import com.example.demo.announcement.dto.response.AnnouncementResponse;
import com.example.demo.announcement.dto.response.AnnouncementShortResponse;
import com.example.demo.announcement.model.Announcement;
import com.example.demo.common.SuccessMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnnouncementService {

    SuccessMessage addAnnouncement(AnnouncementRequest request);
    AnnouncementResponse getAnnouncementById(Long id);
    Page<AnnouncementShortResponse> getShortAnnouncement(Pageable pageable);
    AnnouncementResponse updateAnnouncement(AnnouncementRequest request,Long id);
    Announcement findAnnouncementById(Long id);
}
