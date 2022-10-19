package org.spine.iquestionapi.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    private String NO_REPLY_EMAIL = "noreply@spine.ngo";

    public void sendSimpleEmail(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");


        helper.setFrom(NO_REPLY_EMAIL);
        helper.setReplyTo(NO_REPLY_EMAIL);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body, false);

        mailSender.send(mimeMessage);
    }
}