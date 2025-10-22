package com.example.demo.announcement.repository;

import com.example.demo.announcement.model.HallDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallDetailRepository  extends JpaRepository<HallDetail, Long> {
}
