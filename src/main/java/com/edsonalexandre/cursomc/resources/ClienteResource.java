package com.edsonalexandre.cursomc.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
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

import com.edsonalexandre.cursomc.domain.Cliente;
import com.edsonalexandre.cursomc.dto.ClienteDTO;
import com.edsonalexandre.cursomc.dto.ClienteNewDTO;
import com.edsonalexandre.cursomc.services.ClienteService;

@RestController
@RequestMapping (value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	
	@GetMapping (value = "/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id){
		return ResponseEntity.ok().body(service.find(id));
	}
	
	@GetMapping
	@PostAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<ClienteDTO> listDTO = new ArrayList<>();
		List<Cliente> list = service.findAll();
		//listDTO = list.stream().map((Cliente obj) -> new ClienteDTO(obj)).collect(Collectors.toList());
		for(Cliente c : list) {
			listDTO.add( new ClienteDTO(c));
		}
		return ResponseEntity.ok().body(listDTO);
	}
	
	@GetMapping(value="/page")
	@PostAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam (value="page",defaultValue = "0") Integer page, 
			@RequestParam (value="linesPerPage",defaultValue = "24") Integer linesPerPage,
			@RequestParam (value="direction", defaultValue = "ASC") String direction, 
			@RequestParam (value="orderBy", defaultValue = "nome") String orderBy
		
			){
		Page<Cliente> pageable = service.findPage(page, linesPerPage, direction, orderBy);
		Page<ClienteDTO> pageableDTO = pageable.map(x -> new ClienteDTO(x)); 
		return ResponseEntity.ok().body(pageableDTO);
	}
	
	@PutMapping( value="/{id}")
	public ResponseEntity<Cliente> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO clienteDTO){
		Cliente cliente = service.fromDTO(clienteDTO);
		cliente.setId(id);
		cliente=service.update(cliente);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping (value="/{id}")
	@PostAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Cliente> delete(@PathVariable Integer id){
		service.find(id);
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@PostMapping 
	public ResponseEntity<Cliente> insert (@Valid @RequestBody ClienteNewDTO objDTO){
		Cliente obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
}
