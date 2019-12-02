package com.edsonalexandre.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edsonalexandre.cursomc.domain.Cidade;
import com.edsonalexandre.cursomc.repositories.CidadeRepository;

@Service
public class CidadeService {
	@Autowired
	private CidadeRepository repository;
	
	
	public List<Cidade> find(Integer estadoId){
		return repository.findCidades(estadoId);			
	}
}
