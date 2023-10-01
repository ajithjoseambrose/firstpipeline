package com.ajith.studentservice.controller;

import com.ajith.studentservice.model.SlotBooking;
import com.ajith.studentservice.response.SlotResponse;
import com.ajith.studentservice.service.SlotBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/slot")
public class SlotController {
    @Autowired
    private SlotBookingService bookingService;
    @PostMapping("/book/{custId}/{bookId}")
    public ResponseEntity<SlotResponse> bookSlot(@PathVariable int custId, @PathVariable int bookId){
        SlotResponse slotResponse = bookingService.bookSlot(custId, bookId);
        return new ResponseEntity<>(slotResponse, HttpStatus.CREATED);
    }
    @PostMapping("/bookReturn/{id}")
    public ResponseEntity<SlotBooking> returnBook(@PathVariable int id){
        return new ResponseEntity<>(bookingService.bookReturned(id),HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<SlotBooking>> allBookings(){
        return new ResponseEntity<>(bookingService.allBookings(),HttpStatus.OK);
    }
    @GetMapping("/student")
    public ResponseEntity<List<SlotBooking>> allBookingsByUser(@RequestParam String user){
        return new ResponseEntity<>(bookingService.byStudentName(user),HttpStatus.OK);
    }
}
