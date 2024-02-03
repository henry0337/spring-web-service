package com.example.demo.controllers;

import com.example.demo.models.MailBody;
import com.example.demo.services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.ALL_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MailController {

    private final MailService mailService;

    @PostMapping(value = "/send/{toMail}", consumes = ALL_VALUE, produces = APPLICATION_JSON_VALUE)
    public String sendMail(
            @PathVariable String toMail,
            @RequestBody MailBody body
    ) {
        mailService.sendMail(toMail, body);
        return "Successfully sent an email to the final target!";
    }
}
