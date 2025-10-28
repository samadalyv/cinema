package com.example.demo.announcement.repository;

import com.example.demo.announcement.model.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    @Query("""
                SELECT COUNT(a) > 0
                FROM Announcement a
                JOIN a.hallDetails hd
                WHERE hd.hall.id = ?1
                  AND hd.showDate = ?2
                  AND (
                        (?3 < hd.endTime AND ?4 > hd.startTime)
                      )
            """)
    boolean existsByHallIdAndOverlap(Long hallId, LocalDate showDate, LocalTime startTime, LocalTime endTime);


}
