package com.edsonalexandre.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractEmailService.class);
	
	@Override
	public void SendEmail(SimpleMailMessage msg) {		
		LOG.info("Simulando envio de e-mail");
		LOG.info(msg.toString());
		LOG.info("E-mail enviado com sucesso");		
	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Simulando envio de e-mail HTML");
		LOG.info(msg.toString());
		LOG.info("E-mail enviado com sucesso");
	}

}
