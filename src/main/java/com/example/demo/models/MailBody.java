package com.example.demo.models;

import lombok.Data;

import java.util.Date;

@Data
public class MailBody {
    private String subject;
    private String message;
    private Date sentDate = new Date(System.currentTimeMillis());
}
