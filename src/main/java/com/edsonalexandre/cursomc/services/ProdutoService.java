package com.edsonalexandre.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.edsonalexandre.cursomc.domain.Categoria;
import com.edsonalexandre.cursomc.domain.Produto;
import com.edsonalexandre.cursomc.repositories.CategoriaRepository;
import com.edsonalexandre.cursomc.repositories.ProdutoRepository;
import com.edsonalexandre.cursomc.services.exceptions.ObjectNotFoundExcepition;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundExcepition(
				"Produto não encontrado. Id: "+id+" Tipo: "+Produto.class.getName())
				);
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageable = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);				
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome,categorias,pageable);		
	}
}
