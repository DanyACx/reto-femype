package com.prestamype.reto_dev.service.interfaces;

public interface IEmailService {

	// solo mensajes
	void sendEmail(String toUser, String subject, String message);
}
