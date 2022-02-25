package com.example.demo.model;

public enum Role {
	
	ADMIN("Administrador"), USER("Usuário");
	
	private String nome;
	
	private Role(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return this.nome;
	}
}
