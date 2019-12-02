package com.edsonalexandre.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edsonalexandre.cursomc.domain.Estado;
import com.edsonalexandre.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository repository;

	
	public List<Estado> findAll() {
		return repository.findAllByOrderByNome();
	}
}
