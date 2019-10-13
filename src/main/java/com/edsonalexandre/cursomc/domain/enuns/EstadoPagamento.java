package com.edsonalexandre.cursomc.domain.enuns;

import com.sun.xml.txw2.IllegalAnnotationException;

public enum EstadoPagamento {
	PENDENTE (1,"Pendente"),
	QUITADO  (2,"Quitado"),
	CANCELADO(3, "Cancelado");
	
	private int cod;
	private String descricao;
	
	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public static EstadoPagamento toEnum(int cod) {
		for(EstadoPagamento e : EstadoPagamento.values()) {
			if(e.getCod() == cod) {
				return e;
			}
		}		
		throw new IllegalAnnotationException("Id inválido: "+cod);
	}
	
}
