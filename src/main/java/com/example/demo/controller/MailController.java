package com.example.demo.controller;

import com.example.demo.model.MailBody;
import com.example.demo.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping(value = "/send/{toMail}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String sendMail(
            @PathVariable String toMail,
            @RequestBody MailBody body
    ) {
        mailService.sendMail(toMail, body);
        return "Successfully sent an email to the final target!";
    }
}
