package com.lucas.credentials.controller;

import com.google.gson.Gson;
import com.lucas.credentials.config.JwtTokenUtil;
import com.lucas.credentials.messaging.CreateCustomerMessage;
import com.lucas.credentials.models.JwtRequest;
import com.lucas.credentials.models.JwtResponse;
import com.lucas.credentials.models.UserDao;
import com.lucas.credentials.models.UserDto;
import com.lucas.credentials.service.JwtUserDetailsService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/credentials")
public class JwtAuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    private final JwtUserDetailsService userDetailsService;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${border.rabbitmq.exchange}")
    private String exchange;
    @Value("${border.rabbitmq.routingkey}")
    private String routingkey;

    public JwtAuthenticationController(AuthenticationManager authenticationManager,
                                       JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
        System.out.println(user);
        UserDao userDao = userDetailsService.save(user);
        Gson gson = new Gson();
        CreateCustomerMessage ccm = new CreateCustomerMessage();
        ccm.setCredentialsId(userDao.getId());
        ccm.setFirstName(user.getFirstName());
        ccm.setMiddleName(user.getMiddleName());
        ccm.setLastName(user.getLastName());
        ccm.setPhoneNumbers(user.getPhoneNumbers());
        String result = gson.toJson(ccm);
        rabbitTemplate.convertAndSend(exchange, "create-customer", result);
        return ResponseEntity.ok().build();
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers() {
        List<UserDao> ls = userDetailsService.getAll();

        return ResponseEntity.ok(ls);
    }
}



























