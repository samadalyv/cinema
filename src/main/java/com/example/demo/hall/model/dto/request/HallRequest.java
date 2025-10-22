package com.example.demo.hall.model.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HallRequest {


    private String name;

    private int numberOfColumn;

    private int numberOfRow;
}
