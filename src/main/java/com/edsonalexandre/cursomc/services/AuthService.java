package com.edsonalexandre.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edsonalexandre.cursomc.domain.Cliente;
import com.edsonalexandre.cursomc.repositories.ClienteRepository;
import com.edsonalexandre.cursomc.services.exceptions.ObjectNotFoundExcepition;

@Service
public class AuthService {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	PasswordEncoder pe;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	private Random rand = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(cliente == null) {
			throw new ObjectNotFoundExcepition("Email não encontrado");
		}
		
		String newPass = newPassword();
		cliente.setSenha(pe.encode(newPass));
		clienteRepository.save(cliente);	
		emailService.sendNewPasswordEmail(cliente,newPass);
	}

	private String newPassword() {
		
		char[] vet = new char[10];
		
		for(int i=0; i<10; i++) {
			vet[i] = randonChar();
		}
		
		return null;
	}

	private char randonChar() {
		int opt = rand.nextInt(3);
		
		if(opt==0) { //Gera um dígito
			return (char) (rand.nextInt(10)+48);
		} else 	if(opt==1) { //Gera letra maiúscula
			return (char) (rand.nextInt(26)+65);
		} else if(opt==2) { //Gera letra minúscula
			return (char) (rand.nextInt(26)+97);
		}
		
		return 0;
		
	};
}
