package com.example.demo.announcement.model;


import com.example.demo.movies.entity.Movie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
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

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HallDetail> hallDetails = new HashSet<>();

}
