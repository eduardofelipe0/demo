package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Entrada;
import com.example.demo.model.StatusEntrada;
import com.example.demo.repository.EntradaRepository;

@Service
public class GestaoEntradaService {
	
	@Autowired
	private EntradaRepository entradaRepository;
	
	public void finalizar(Long id) {
		Entrada entrada = buscar(id);
		
		entrada.finalizar();
		entradaRepository.save(entrada);
	}
		
	public Entrada buscar(Long id) {
		return entradaRepository.findById(id).get();
	}
	
	public Entrada criar(Entrada entrada) {
		entrada.setStatus(StatusEntrada.ABERTA);
		return entradaRepository.save(entrada);
	}
	
	public List<Entrada> listar() {
		return (List<Entrada>) entradaRepository.findAll();
	}
	
	public List<Entrada> listaFinalizadas() {
		return (List<Entrada>) entradaRepository.findAll();	
	}
}
