package com.edsonalexandre.cursomc.domain.enuns;

public enum Perfil {
	ADMIN(1,"ROLE_ADMIN"),
	CLIENTE (2,"ROLE_CLIENTE");
	
	private Integer codigo;
	private String descricao;
	
	private Perfil(Integer codigo, String descricao) {
		this.codigo=codigo;
		this.descricao=descricao;
	}
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer codigo) {
		for(Perfil p : Perfil.values()) {
			if(p.getCodigo() == codigo) {
				return p;
			}
		}
		throw new IllegalArgumentException("Perfil inválido. Código: "+codigo);
	}
}
