package com.jms.email;

import java.util.Map;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jms.email.model.Email;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailConsumer {
    
    @JmsListener(destination = "emailQueue")    
    public void receiveEmail(Map<String, Object> emailMap) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            /*This configuration will prevent an exception if we receive
            map keys that the class properties does not have*/
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Email email = objectMapper.convertValue(emailMap, Email.class);
            log.info("Email object received is: " + email.toString());
        } catch(Exception e) {
            log.error("Recieved Exception during receiveEmail: ", e);
        }
    }

}
