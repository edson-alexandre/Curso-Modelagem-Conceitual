package com.edsonalexandre.cursomc.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edsonalexandre.cursomc.domain.Categoria;
import com.edsonalexandre.cursomc.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{
	
	@Query("SELECT distinct obj "
		  + "FROM Produto obj "
		  + "INNER JOIN obj.categorias cat "
		  + "where obj.nome like %:nome% "
		  + "and cat IN :categorias")
	public Page<Produto>findDistinctByNomeContainingAndCategoriasIn(@Param("nome") String nome,@Param("categorias")  List<Categoria> categorias,Pageable pageable);
}
