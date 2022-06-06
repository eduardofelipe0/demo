package com.example.demo.model;

public enum TipoEntradaObjeto {

	SAIDA_COM_TERCEIROS("Saída com terceiros"), UTILIZADOS_NO_HOTEL("Será consumido/utilizado dentro do hotel"),
	SAIDA_COM_MESMA_PESSOA("Saída com mesma pessoa");

	private TipoEntradaObjeto(String descricao) {
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
