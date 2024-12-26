package com.prestamype.reto_dev.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

	@Value("${email.sender}")
	private String emailUser;
	
	@Value("${email.password}")
	private String password;

	@Bean
	public JavaMailSender getJavaMailSender() {
		// JavaMailSender: ayuda a configurar propiedades correos para enviar,
		// ayuda a interactuar con la cuenta que envia mensaje 
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost("smtp.gmail.com"); // se define el protocolo para gmail (proveedor)
		mailSender.setPort(587); // define el puerto estandar
		mailSender.setUsername(emailUser); // el correo que envia
		mailSender.setPassword(password); // define el password, considerar vulneravilidad
		
		// obtener propiedades, para luego agregar otras adicionales
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp"); // especifica el protcolo
		props.put("mail.smtp.auth", "true"); // habilitando la autenticacion
	    props.put("mail.smtp.starttls.enable", "true"); // habilita el cifrado de la comunicación
		//props.put("mail.debug", "true"); //para imprimir información en el consola
		props.put("mail.smtp.ssl.trust", "*");
		
		return mailSender;
	}
}
