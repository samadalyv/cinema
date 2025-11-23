package com.example.demo.announcement.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HallDetailRequest {

    private Long hallId;
    @FutureOrPresent(message = "Date must not be at pass time")
    private LocalDate showDate;
    private LocalTime startTime;
    private LocalTime endTime;
    @Positive(message = "Price must not be negative")
    private BigDecimal price;

}
