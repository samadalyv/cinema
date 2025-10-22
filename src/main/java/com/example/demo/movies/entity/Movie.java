package com.example.demo.movies.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

//    // todo category
//
//    @Column(name = "show_date")
//    private LocalDate showDate;
//
//
//    //todo
//    @Column(name = "start_time")
//    private LocalTime startTime;
//
//    @Column(name = "end_time")
//    private LocalTime endTime;
//
//    @Positive(message = "price must be positive")
//    private Double price;
}
