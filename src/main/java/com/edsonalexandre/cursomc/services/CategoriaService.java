package com.edsonalexandre.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.edsonalexandre.cursomc.domain.Categoria;
import com.edsonalexandre.cursomc.dto.CategoriaDTO;
import com.edsonalexandre.cursomc.repositories.CategoriaRepository;
import com.edsonalexandre.cursomc.services.exceptions.DataIntegrityException;
import com.edsonalexandre.cursomc.services.exceptions.ObjectNotFoundExcepition;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundExcepition(
				"Objeto não encontrado. Id: "+id+" Tipo: "+Categoria.class.getName())
				);
	}
	
	public Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repository.save(categoria);
	}
	
	public Categoria update (Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}
	
	public void delete(Integer id) {
		try {
			repository.deleteById(id);	
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Categoria não pode ser excluída pois possui produtos");
		}		
	}
	
	public List<Categoria> findAll(){
		return repository.findAll();
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String direction, String orderBy){
		PageRequest pageable = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageable);
	}
	
	public Categoria fromDTO(CategoriaDTO dto) {
		return new Categoria(dto.getId(),dto.getNome());
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
}
