package com.edsonalexandre.cursomc.domain;

import javax.persistence.Entity;

import com.edsonalexandre.cursomc.domain.enuns.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("PagamentoComCartao")
public class PagamentoComCartao extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeparcelas;
	
	public PagamentoComCartao() {		
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);		
		this.numeroDeparcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeparcelas() {
		return numeroDeparcelas;
	}

	public void setNumeroDeparcelas(Integer numeroDeparcelas) {
		this.numeroDeparcelas = numeroDeparcelas;
	}
		
	
}	
