package org.spine.iquestionapi.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.spine.iquestionapi.model.RequestPasswordResetBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailSenderService {
	
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private Configuration config;
	
	public Map<String, Object> sendEmail(RequestPasswordResetBody request, Map<String, Object> model) throws MessagingException, IOException, TemplateException {
		MimeMessage message = sender.createMimeMessage();
		// set mediaType
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		// add attachment
		helper.addAttachment("logo.png", new ClassPathResource("logo.png"));

		Template t = config.getTemplate("email-template.ftl");
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

		helper.setTo(request.getEmail());
		helper.setText(html, true);
		helper.setSubject("Uw wachtwoord resetten - iQuestion");
		helper.setFrom("noreply@spine.ngo");
		sender.send(message);

		return Collections.singletonMap("message", "Email sent!");
	}
}
	