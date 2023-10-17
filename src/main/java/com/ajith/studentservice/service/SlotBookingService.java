package com.ajith.studentservice.service;

import com.ajith.studentservice.model.Book;
import com.ajith.studentservice.model.SlotBooking;
import com.ajith.studentservice.model.Student;
import com.ajith.studentservice.repo.SlotBookingRepo;
import com.ajith.studentservice.repo.StudentRepo;
import com.ajith.studentservice.response.SlotResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SlotBookingService {
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private Book bookObject;
    @Autowired
    private SlotBookingRepo slotBookingRepo;
    Logger logger = LoggerFactory.getLogger(StudentService.class);
    @Autowired
    private RestTemplate restTemplate;
    public SlotResponse bookSlot(int custId, int bookId){
        ResponseEntity<Book> book = restTemplate.exchange("http://BOOK-SERVICE/books/" + bookId, HttpMethod.GET,null, Book.class);
            if (book.getStatusCode().is2xxSuccessful()) {
                Book recievedBook = book.getBody();
                Student student = studentRepo.findById(custId).get();
                SlotResponse slotResponse = new SlotResponse();
                logger.info("Book availability: {}", recievedBook.getAvailable());

                if (recievedBook.getAvailable().equals("true")) {
                    Date date = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String dateToSet = dateFormat.format(date);
                    SlotBooking slotBooking = new SlotBooking();
                    slotResponse.setStatus("success");
                    slotResponse.setMessage("Booking confirmed..");
                    slotResponse.setStudent_name(student.getName());
                    slotResponse.setBook_name(recievedBook.getName());
                    slotBooking.setStudent_id(custId);
                    slotBooking.setStudent_name(student.getName());
                    slotBooking.setBook_id(bookId);
                    slotBooking.setBook_name(recievedBook.getName());
                    slotBooking.setStatus("not returned");
                    slotBooking.setCreated_date(dateToSet);
                    slotBookingRepo.save(slotBooking);
                    bookObject.setAvailable("false");
                    HttpEntity<Book> requestEntity = new HttpEntity<>(bookObject);
                    ResponseEntity<Book> responseEntity = restTemplate.exchange("http://BOOK-SERVICE/books/update/" + bookId, HttpMethod.PUT, requestEntity, Book.class);
                    Book bookResponseObject = responseEntity.getBody();
                    logger.info("Book response: {}",bookResponseObject);

                } else {
                    slotResponse.setStatus("failed");
                    slotResponse.setMessage("Book is not available");
                    slotResponse.setStudent_name(student.getName());
                    slotResponse.setBook_name(recievedBook.getName());
                }
                logger.info("{} ", slotResponse);
                return slotResponse;
            }
        return null;
    }
    public SlotBooking bookReturned(int slot_id){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dateToSet = dateFormat.format(date);
        SlotBooking getSlotToUpdate = slotBookingRepo.findById(slot_id).get();
        int book_id = getSlotToUpdate.getBook_id();
        getSlotToUpdate.setStatus("returned");
        getSlotToUpdate.setReturned_date(dateToSet);
        SlotBooking saveReturned = slotBookingRepo.save(getSlotToUpdate);

        bookObject.setAvailable("true");
        HttpEntity<Book> requestEntity = new HttpEntity<>(bookObject);
        ResponseEntity<Book> responseEntity = restTemplate.exchange("http://BOOK-SERVICE/books/update/" + book_id, HttpMethod.PUT, requestEntity, Book.class);
        Book bookResponseObject = responseEntity.getBody();
        logger.info("Book response: {} Updated: {}",bookResponseObject,saveReturned);
        return saveReturned;
    }
    public List<SlotBooking> allBookings(){
        List<SlotBooking> allBookings = slotBookingRepo.findAll();
        logger.info("All slots: {}",allBookings);
        return allBookings;
    }

    public List<SlotBooking> byStudentName(String student){
        List<SlotBooking> byUser = slotBookingRepo.findByUser(student);
        logger.info("By user name: {}",byUser);
        return byUser;
    }
}
