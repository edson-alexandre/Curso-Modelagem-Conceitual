package com.edsonalexandre.cursomc.dto;

import java.io.Serializable;

import com.edsonalexandre.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private Double preco;
	
	public ProdutoDTO() {		
	}
	
	public ProdutoDTO(Produto produto) {
		this.id=produto.getId();
		this.nome= produto.getNome();
		this.preco= produto.getPreco();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
		
}
