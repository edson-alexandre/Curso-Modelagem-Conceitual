package com.edsonalexandre.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edsonalexandre.cursomc.domain.Cidade;
import com.edsonalexandre.cursomc.domain.Cliente;
import com.edsonalexandre.cursomc.domain.Endereco;
import com.edsonalexandre.cursomc.domain.enuns.TipoCliente;
import com.edsonalexandre.cursomc.dto.ClienteDTO;
import com.edsonalexandre.cursomc.dto.ClienteNewDTO;
import com.edsonalexandre.cursomc.repositories.ClienteRepository;
import com.edsonalexandre.cursomc.repositories.EnderecoRepository;
import com.edsonalexandre.cursomc.services.exceptions.DataIntegrityException;
import com.edsonalexandre.cursomc.services.exceptions.ObjectNotFoundExcepition;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired BCryptPasswordEncoder pe;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundExcepition(
				"Objeto não encontrado. Id: " + id + " Tipo: " + Cliente.class.getName()));
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	public void delete(Integer id) {
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Cliente não pode ser excluído pois possui pedidos");
		}
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage,String direction, String orderBy){
		PageRequest pageable = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageable);
	}
	
	@Transactional
	public Cliente insert (Cliente obj) {
		obj.setId(null);
		obj = repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());		
		return obj;
	}

	public Cliente fromDTO(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null,null);
	}
	
	public Cliente fromDTO(ClienteNewDTO dto) {
		Cliente cli = new Cliente(null, dto.getNome(),dto.getEmail(), dto.getCpfOUCnpj(),TipoCliente.toEnum(dto.getTipo()),pe.encode(dto.getSenha()));
		cli.getTelefones().add(dto.getTelefone1());
		
		if(dto.getTelefone2() != null) {
			cli.getTelefones().add(dto.getTelefone2());
		}
		
		if(dto.getTelefone3() != null) {
			cli.getTelefones().add(dto.getTelefone3());
		}
		
		Cidade cid = new Cidade(dto.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, dto.getLogradouro(), dto.getNumero(), dto.getComplemento(), dto.getBairro(),dto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		return cli;
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
