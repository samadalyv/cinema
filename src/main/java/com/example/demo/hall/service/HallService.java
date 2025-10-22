package com.example.demo.hall.service;

import com.example.demo.hall.model.dto.request.HallRequest;
import com.example.demo.hall.model.dto.response.HallResponse;
import com.example.demo.hall.model.entity.Hall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HallService {

    HallResponse addHall(HallRequest request);
    Page<HallResponse> getAllHalls(Pageable pageable);
    HallResponse getHallById(Long id);
    HallResponse updateHall(HallRequest request, Long id);
    void  deleteHall(Long id);
    Hall findHallById(Long id);
}
