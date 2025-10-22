package com.example.demo.announcement.repository;

import com.example.demo.announcement.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {


    @Query("select a from  Announcement a join HallDetail hd where  hd.hall.id = ?1 and " +
            "hd.showDate =?2 and " +
            "hd.startTime =?3 and " +
            "")


    boolean existsByHallIdAndOverlap(Long hallId, LocalDate showDate, LocalTime startTime, LocalTime endTime);
}
