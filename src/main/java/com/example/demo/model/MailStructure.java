package com.example.demo.model;

import lombok.Data;

import java.util.Date;

@Data
public class MailStructure {
    private String subject;
    private String message;
    private Date sentDate = new Date(System.currentTimeMillis());
}
