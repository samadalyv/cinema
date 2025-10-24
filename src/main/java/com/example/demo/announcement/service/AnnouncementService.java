package com.example.demo.announcement.service;

import com.example.demo.announcement.dto.request.AnnouncementRequest;
import com.example.demo.common.SuccessMessage;

public interface AnnouncementService {

    SuccessMessage addAnnouncement(AnnouncementRequest request);
}
