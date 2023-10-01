package com.ajith.studentservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "slot_booking")
public class SlotBooking {
    @Id
    @GeneratedValue
    private int id;
    private int student_id;
    private String student_name;
    private int book_id;
    private String book_name;
    private String status;
    private String created_date;
    private String returned_date;

}
