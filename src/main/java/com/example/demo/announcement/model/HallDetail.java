package com.example.demo.announcement.model;

import com.example.demo.hall.model.entity.Hall;
import jakarta.persistence.*;
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
@Table(name = "hall_details") //(uniqueConstraints = {@UniqueConstraint(name = "unique_hall_and_show_date",columnNames = {"hall","showDate"})})
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HallDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "hall_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Hall hall;

    @Column(name = "show_date")
    private LocalDate showDate;


    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Positive(message = "price must be positive")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;
}
