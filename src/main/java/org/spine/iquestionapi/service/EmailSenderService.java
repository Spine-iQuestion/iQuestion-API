package org.spine.iquestionapi.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * The service for sending emails
 */
@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    private final String NO_REPLY_EMAIL = "noreply@spine.ngo";

    /**
     * Send an email
     * @param toEmail the email of the recipient
     * @param subject the subject of the email
     * @param token the token to be sent
     * @throws MessagingException if the email could not be sent
     */
    public void sendSimpleEmail(String toEmail, String subject, String token) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setFrom(NO_REPLY_EMAIL);
        helper.setReplyTo(NO_REPLY_EMAIL);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(token, false);

        mailSender.send(mimeMessage);
    }
}