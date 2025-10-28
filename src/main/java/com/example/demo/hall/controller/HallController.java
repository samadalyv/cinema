package com.example.demo.hall.controller;

import com.example.demo.hall.model.dto.request.HallRequest;
import com.example.demo.hall.model.dto.response.HallResponse;
import com.example.demo.hall.service.HallService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/halls")
@RequiredArgsConstructor
public class HallController {

    private final HallService hallService;


    @GetMapping
    public ResponseEntity<Page<HallResponse>> getAllHalls(Pageable pageable) {
        Page<HallResponse> halls = hallService.getAllHalls(pageable);
        return ResponseEntity.ok(halls);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HallResponse> getHallById(@PathVariable Long id) {
        return ResponseEntity.ok(hallService.getHallById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<HallResponse> addHall(@RequestBody HallRequest hallRequest) {
        return ResponseEntity.ok(hallService.addHall(hallRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HallResponse> updateHall(@RequestBody HallRequest hallRequest,
                                                   @PathVariable Long id) {
        return ResponseEntity.ok(hallService.updateHall(hallRequest, id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteHall(@PathVariable Long id) {
        hallService.deleteHall(id);
    }


}

