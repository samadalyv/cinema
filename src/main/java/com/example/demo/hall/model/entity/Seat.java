package com.example.demo.hall.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "seats")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Seat{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "seat_column")
    private Integer seatColumn;

    @Column(name = "seat_row")
    private Integer seatRow;

    @JoinColumn(name = "hall_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Hall hall;



}
