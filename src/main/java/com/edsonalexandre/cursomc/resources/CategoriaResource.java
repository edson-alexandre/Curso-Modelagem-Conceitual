package com.edsonalexandre.cursomc.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edsonalexandre.cursomc.domain.Categoria;
import com.edsonalexandre.cursomc.dto.CategoriaDTO;
import com.edsonalexandre.cursomc.services.CategoriaService;

@RestController
@RequestMapping (value = "/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {			
		return ResponseEntity.ok().body(service.find(id));
	}
	
	@PostMapping
	public ResponseEntity<Void> inserir(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = service.fromDTO(categoriaDTO);
		categoria = service.insert(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> findAll(){
		List<Categoria> list = service.findAll();
		//List<CategoriaDTO> listDTO = list.stream().map((Categoria categoria)-> new CategoriaDTO(categoria)).collect(Collectors.toList());
		List<CategoriaDTO> listDTO= new ArrayList<>();
		for(Categoria categoria : list) {			
			listDTO.add(new CategoriaDTO(categoria));
		}
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping (value="/page")
	public ResponseEntity<Page<CategoriaDTO>> findPage(
		@RequestParam (value="page", defaultValue = "0") Integer page, 
		@RequestParam (value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
		@RequestParam (value="direction", defaultValue = "ASC") String direction, 
		@RequestParam (value="orderBy", defaultValue = "nome") String orderBy){
		Page<Categoria> pageable = service.findPage(page, linesPerPage, direction, orderBy);
		Page<CategoriaDTO> pageableDTO = pageable.map(cat -> new CategoriaDTO(cat));
		return ResponseEntity.ok().body(pageableDTO);
	}
	
	@PutMapping(value="/{id}")
	public ResponseEntity<Void> update (@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id) {
		Categoria categoria = service.fromDTO(categoriaDTO);
		categoria.setId(id);
		categoria = service.update(categoria);		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping (value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		find(id);
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
