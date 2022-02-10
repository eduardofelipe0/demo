package com.example.demo.exception;

public class EntidadeNaoEncontrada extends NegocioException {
	
	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontrada(String message) {
		super(message);
	}
}
