package com.csmtech.SJTA.util;

import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailSendUtil {

	@Autowired
	private JavaMailSender javaMailSender;

	public Integer sendEmail(String recipientEmail, String content, String subject,String ccEmail,String bccEmail) {
		log.info(":: sendEmail");

		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setTo(recipientEmail);
			helper.setSubject(subject);
			helper.setText(content, true);

			// Add CC recipient if provided
			if (ccEmail != null && !ccEmail.isEmpty()) {
				helper.setCc(ccEmail);
				
			}

			// Add BCC recipient if provided
			if (bccEmail != null && !bccEmail.isEmpty()) {
				helper.setBcc(bccEmail);
			}

			javaMailSender.send(message);
			log.info("Email sent successfully!");
			return 1;

		} catch (Exception e) {
			e.printStackTrace();
			log.info("Failed to send email.");
			return 0;
		}

	}

}
