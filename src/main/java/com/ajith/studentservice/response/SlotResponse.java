package com.ajith.studentservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotResponse {
    private String status;
    private String message;
    private String student_name;
    private String book_name;
}
