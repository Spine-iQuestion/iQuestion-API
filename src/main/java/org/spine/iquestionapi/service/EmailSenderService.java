package org.spine.iquestionapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");


        String htmlMsg;
        try {
            htmlMsg = Files.readString(Paths.get("src/main/resources/templates/password_reset.html"), StandardCharsets.UTF_8);
            htmlMsg.replace("${PASSWORD_RESET_TOKEN}", body);
        } catch (Exception e) {
            htmlMsg = body;
        }

        helper.setFrom("your_email");
        helper.setTo(toEmail);

        helper.setSubject(subject);
        helper.setText(htmlMsg, true);
        helper.setReplyTo("noreply@spine.ngo");


        mailSender.send(mimeMessage);
    }
}

