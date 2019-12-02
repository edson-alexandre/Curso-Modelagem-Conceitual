package com.edsonalexandre.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edsonalexandre.cursomc.dto.CidadeDTO;
import com.edsonalexandre.cursomc.dto.EstadoDTO;
import com.edsonalexandre.cursomc.services.CidadeService;
import com.edsonalexandre.cursomc.services.EstadoService;

@RestController
@RequestMapping (value="/estados")
public class EstadoResource {
	
	@Autowired
	private EstadoService service;
	@Autowired CidadeService cidadeService;
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAll(){		
		List<EstadoDTO> obj = service.findAll().stream().map(e -> new EstadoDTO(e)).collect(Collectors.toList());
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping (value="{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId){
		List<CidadeDTO> cidades = cidadeService.find(estadoId).stream().map(c -> new CidadeDTO(c)).collect(Collectors.toList());		
		return ResponseEntity.ok().body(cidades);
	}

}
