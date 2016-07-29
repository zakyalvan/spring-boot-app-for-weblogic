package com.jakartawebs.mail;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@Profile("staging")
public class MailSenderService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailSenderService.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Scheduled(fixedDelay=240000, initialDelay=5000)
	public void sendTest() {
		LOGGER.info("Preparing testing email message...");
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage message) throws Exception {
				message.setRecipient(RecipientType.TO, new InternetAddress("zakyalvan@gmail.com"));
				message.setSubject("Test Subject");
				message.setText("Test Content");
			}
		};
		LOGGER.info("Sending testing email message...");
		mailSender.send(preparator);
	}
}