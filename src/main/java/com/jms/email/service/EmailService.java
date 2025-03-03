package com.jms.email.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.jms.email.model.Email;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailService {
    
    @Autowired
    JmsTemplate jmsTemplate;
    //This is the producer that will send a message to a queue
    public ResponseEntity<String> sendEmail(Email email) {
        try {
            //Converting email object to map for sending
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> emailMap = objectMapper.convertValue(email, new TypeReference<Map<String, Object>>() {});
            jmsTemplate.convertAndSend("emailQueue", emailMap);
            log.info("Email map sent is: " + emailMap);
            return new ResponseEntity<>("Sent", HttpStatus.CREATED);
        } catch(Exception e) {
            log.error("Recieved Exception during sendEmail: ", e);
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
