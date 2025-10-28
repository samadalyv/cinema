package com.example.demo.hall.service.lmpl;

import com.example.demo.hall.mapper.HallMapper;
import com.example.demo.hall.model.dto.request.HallRequest;
import com.example.demo.hall.model.dto.response.HallResponse;
import com.example.demo.hall.model.entity.Hall;
import com.example.demo.hall.model.entity.Seat;
import com.example.demo.hall.repository.HallRepository;
import com.example.demo.hall.repository.SeatRepository;
import com.example.demo.hall.service.HallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;
    private final SeatRepository seatRepository;
    private final HallMapper mapper;

    @Override
    public HallResponse addHall(HallRequest request) {
        var savedHall = mapper.fromRequestToEntity(request);
        var newHall = hallRepository.save(savedHall);

        for (int i =1; i<= request.getNumberOfRow(); i++) {
            for (int j =1; j<= request.getNumberOfColumn(); j++) {
                var newSeat = new Seat();
                newSeat.setSeatRow(i);
                newSeat.setSeatColumn(j);
                newSeat.setHall(savedHall);
                seatRepository.save(newSeat);
            }
        }

        log.info("New hall added successfully");
        return mapper.entityToResponse(newHall);
    }


    @Override
    public Page<HallResponse> getAllHalls(Pageable pageable) {
        Page<Hall> all = hallRepository.findAll(pageable);
        log.info("All halls found");
        return all.map(mapper::entityToResponse);
    }


    @Override
    public HallResponse getHallById(Long id) {
     return hallRepository.findById(id)
                .map(mapper::entityToResponse)//method reference
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public HallResponse updateHall(HallRequest request,Long id) {
        Hall byId = findHallById(id);
        byId.setName(request.getName());
        log.info("Updating hall successfully");
       return mapper.entityToResponse(byId);
    }

    @Override
    public void deleteHall(Long id) {
        log.info("Deleting hall successfully");
        hallRepository.deleteById(id);
    }

    @Override
    public Hall findHallById(Long id) {
        return hallRepository.findById(id).orElseThrow(RuntimeException::new);
    }


}
