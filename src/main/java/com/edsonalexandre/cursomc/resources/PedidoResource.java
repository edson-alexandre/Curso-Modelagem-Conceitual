package com.edsonalexandre.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.edsonalexandre.cursomc.domain.Pedido;
import com.edsonalexandre.cursomc.services.PedidoService;

@RestController
@RequestMapping (value = "pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;
	
	@GetMapping (value = "/{id}")
	public ResponseEntity<Pedido> find(@PathVariable Integer id){
		return ResponseEntity.ok().body(service.find(id));
	}
	
	@PostMapping
	public ResponseEntity<Pedido> insert(@RequestBody Pedido obj){
		obj = service.inserir(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping
	public ResponseEntity<Page<Pedido>> findPage(
		@RequestParam (value="page", defaultValue = "0") Integer page, 
		@RequestParam (value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
		@RequestParam (value="direction", defaultValue = "DESC") String direction, 
		@RequestParam (value="orderBy", defaultValue = "instante") String orderBy){
		Page<Pedido> pageable = service.findPage(page, linesPerPage, direction, orderBy);
		return ResponseEntity.ok().body(pageable);
	}
}
