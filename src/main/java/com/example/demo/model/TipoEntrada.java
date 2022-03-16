package com.example.demo.model;

public enum TipoEntrada {

	UBER("Uber"), TÁXI("Táxi"), HÓSPEDE("Hóspede"), ENTREGA("Entrega");
	
	private TipoEntrada (String descricao){
		this.descricao = descricao;
	}
	
	private String descricao;

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Override
	public String toString() {
		return this.descricao;
	}
	public String getName() {
		return this.name();
	}
}
