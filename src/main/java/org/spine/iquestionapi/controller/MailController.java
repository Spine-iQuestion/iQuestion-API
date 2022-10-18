package org.spine.iquestionapi.controller;

import org.spine.iquestionapi.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    EmailSenderService emailSenderService;

    // Send a mail
    @GetMapping("/send")
    public void sendMail() {
        emailSenderService.sendSimpleEmail("to@email.com", "subject", "body");
    }
}
