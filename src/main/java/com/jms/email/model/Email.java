package com.jms.email.model;

import lombok.Data;

@Data
public class Email {
    private String email;
    private String title;
    private String message;
}
