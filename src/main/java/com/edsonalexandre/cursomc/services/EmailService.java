package com.edsonalexandre.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.edsonalexandre.cursomc.domain.Pedido;

public interface EmailService {
	
	void SendOrderConfirmationMail(Pedido obj);
	
	void SendEmail(SimpleMailMessage msg);
}
