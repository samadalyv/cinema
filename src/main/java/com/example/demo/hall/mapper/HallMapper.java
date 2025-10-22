package com.example.demo.hall.mapper;

import com.example.demo.hall.model.dto.request.HallRequest;
import com.example.demo.hall.model.dto.response.HallResponse;
import com.example.demo.hall.model.entity.Hall;
import org.springframework.stereotype.Component;

@Component
public class HallMapper {


    public Hall fromRequestToEntity(HallRequest request) {

        if (request == null) {
            throw  new NullPointerException("Request must not be null");
        }
        var hall = new Hall();
        hall.setName(request.getName());
        return hall;
    }

    public HallResponse entityToResponse(Hall hall) {
        var response = new HallResponse();
        response.setId(hall.getId());
        response.setName(hall.getName());
        return response;
    }

}
