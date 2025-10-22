package com.example.demo.announcement.model;


import com.example.demo.movies.entity.Movie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Table(name = "announcements")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class  Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "movie_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @JoinTable(name = "announcement_hall_details",
            joinColumns = @JoinColumn(name = "announcement_id"),
            inverseJoinColumns = @JoinColumn(name = "hall_detail_id"))
    @OneToMany(fetch = FetchType.LAZY)
    private Set<HallDetail> hallDetails;

}
