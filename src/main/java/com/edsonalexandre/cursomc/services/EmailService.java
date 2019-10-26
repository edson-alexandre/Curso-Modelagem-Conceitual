package com.edsonalexandre.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.edsonalexandre.cursomc.domain.Pedido;

public interface EmailService {
	
	void SendOrderConfirmationMail(Pedido obj);	
	void SendEmail(SimpleMailMessage msg);
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	void sendHtmlEmail(MimeMessage msg);
}
