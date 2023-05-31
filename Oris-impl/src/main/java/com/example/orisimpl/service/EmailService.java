package com.example.orisimpl.service;

import dto.response.MailReponse;

public interface EmailService {

    MailReponse sendEmail(String to, String subject, String text);
}
