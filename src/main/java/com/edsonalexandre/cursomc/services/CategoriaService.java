package com.edsonalexandre.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edsonalexandre.cursomc.domain.Categoria;
import com.edsonalexandre.cursomc.repositories.CategoriaRepository;
import com.edsonalexandre.cursomc.services.exceptions.ObjectNotFoundExcepition;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repositorio;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repositorio.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundExcepition(
				"Objeto não encontrado. Id: "+id+" Tipo: "+Categoria.class.getName())
				);
	}
	
	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repositorio.save(categoria);
	}
}
