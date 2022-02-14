package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Entrada;
import com.example.demo.repository.EntradaRepository;

@Service
public class GestaoEntradaService {
	
	@Autowired
	private EntradaRepository entradaRepository;
	
	public void finalizar(String entradaId) {
		Entrada entrada = buscar(entradaId);
		
		entrada.setHoraSaida(entrada.getHoraSaida());
		entradaRepository.save(entrada);
	}
		
	public Entrada buscar(String id) {
		return entradaRepository.findById(id).get();
	}
	
	public void remover(String id) {
		//
    }
	
	public Entrada criar(Entrada entrada) {
		return entradaRepository.save(entrada);
	}
	
	public List<Entrada> listar() {
		return (List<Entrada>) entradaRepository.findAll();
	}
}
