package com.edsonalexandre.cursomc.domain.enuns;

public enum TipoCliente {
	PESSOAFISICA(1,"Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private Integer cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public Integer getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer cod) {
		for(TipoCliente tipoCliente : TipoCliente.values()) {
			if(tipoCliente.getCod() == cod) {
				return tipoCliente;
			}
		}
		throw new IllegalArgumentException("Id inválido: "+cod);
	}
}
