package com.lucas.credentials.controller;

import com.lucas.credentials.models.CredentialStatus;
import com.lucas.credentials.models.UserDao;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
public class HelloWorldController {

//    @Autowired
//    private AmqpTemplate rabbitTemplate;
//
//    @Value("${border.rabbitmq.exchange}")
//    private String exchange;
//    @Value("${border.rabbitmq.routingkey}")
//    private String routingkey;

//    @GetMapping(value = "/send-user", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity sendUser() {
//        UserDao userDao = new UserDao();
//        userDao.setEmail("test.email@email.nl");
//        userDao.setStatus(CredentialStatus.UNVERIFIED);
//        rabbitTemplate.convertAndSend(exchange, routingkey, userDao);
//        return ResponseEntity.ok().build();
//    }

}
