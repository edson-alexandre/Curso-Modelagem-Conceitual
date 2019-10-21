package com.edsonalexandre.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edsonalexandre.cursomc.domain.Produto;
import com.edsonalexandre.cursomc.dto.ProdutoDTO;
import com.edsonalexandre.cursomc.resources.utils.URL;
import com.edsonalexandre.cursomc.services.ProdutoService;

@RestController
@RequestMapping (value = "produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Produto> find(@PathVariable Integer id){
		return ResponseEntity.ok().body(service.find(id));
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> search(
			@RequestParam (defaultValue = "") String nome, 
			@RequestParam  (defaultValue = "") String categorias, 
			@RequestParam (defaultValue = "0") Integer page, 
			@RequestParam (defaultValue = "24") Integer linesPerPage, 
			@RequestParam (defaultValue = "ASC") String direction, 
			@RequestParam (defaultValue = "nome") String orderBy
			){
		
		String nomeDecoded = URL.deodeParam(nome);
		List<Integer> ids= URL.decodeURL(categorias);		
		Page<Produto> pageable = service.search(nomeDecoded,ids,page, linesPerPage, direction, orderBy);
		Page<ProdutoDTO> pageableDTO= pageable.map(x -> new ProdutoDTO(x));
		return ResponseEntity.ok().body(pageableDTO);
	}
}
