package com.example.demo.controller;

import com.example.demo.model.MailStructure;
import com.example.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping(
            value = "/send/{toMail}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String sendMail(
            @PathVariable String toMail,
            @RequestBody MailStructure body
    ) {
        mailService.sendMail(toMail, body);
        return "Successfully sent an email to the final target!";
    }
}
