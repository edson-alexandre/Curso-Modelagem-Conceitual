package com.edsonalexandre.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.edsonalexandre.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{

	@Value("${default.sender}")
	private String sender;
	
	@Override
	public void SendOrderConfirmationMail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessagemFromPedido(obj);
		SendEmail(sm);
	}

	protected  SimpleMailMessage prepareSimpleMailMessagemFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("Confirmação de pedido Nro: "+obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}
}
