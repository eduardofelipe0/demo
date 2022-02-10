package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exception.EntidadeNaoEncontrada;
import com.example.demo.model.Entrada;
import com.example.demo.repository.EntradaRepository;

@Service
public class GestaoEntradaService {
	
	@Autowired
	private EntradaRepository entradaRepository;
	
	public void finalizar(String entradaId) {
		Entrada entrada = buscar(entradaId);
		
		//entrada.finalizar();
		entradaRepository.save(entrada);
	}
		
	private Entrada buscar(String entradaId) {
		return entradaRepository.findById(entradaId).
				orElseThrow(() -> new EntidadeNaoEncontrada("Entrada não Encontrada!"));
	}
}
