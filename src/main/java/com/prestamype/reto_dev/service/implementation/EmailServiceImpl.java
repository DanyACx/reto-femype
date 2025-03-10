package com.prestamype.reto_dev.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.prestamype.reto_dev.service.interfaces.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService{

	@Value("${email.sender}")
	private String emailUser;

	@Autowired
	private JavaMailSender mailSender;

	
	@Override
	public void sendEmail(String toUser, String subject, String message) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setFrom(emailUser); // cuenta que envia email
		mailMessage.setTo(toUser);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);

		mailSender.send(mailMessage); // enviando el correo

	}
}
