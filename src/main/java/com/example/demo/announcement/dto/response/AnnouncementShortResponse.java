package com.example.demo.announcement.dto.response;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementShortResponse {

    // pagination
    private Long id;
    private String movieName;
    private String hallName;
    private BigDecimal price;


}
