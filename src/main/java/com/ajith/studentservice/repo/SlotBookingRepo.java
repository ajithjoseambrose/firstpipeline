package com.ajith.studentservice.repo;

import com.ajith.studentservice.model.SlotBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotBookingRepo extends JpaRepository<SlotBooking, Integer> {
    @Query(value = "SELECT * FROM slot_booking u WHERE u.student_name = ?1 and u.book_id = ?2", nativeQuery = true)
    List<SlotBooking> findByUserAndBookId(String student_name, int book_id);
    @Query(value = "SELECT * FROM slot_booking u WHERE u.student_name = ?1", nativeQuery = true)
    List<SlotBooking> findByUser(String student_name);

}
