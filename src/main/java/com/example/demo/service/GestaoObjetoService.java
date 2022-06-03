package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.NegocioException;
import com.example.demo.model.Objetos;
import com.example.demo.model.StatusEntrada;
import com.example.demo.repository.ObjetosRepository;

@Service
public class GestaoObjetoService {

	@Autowired
	private ObjetosRepository objetosRepository;
	
	public Objetos cadastrar(Objetos objeto) {
		objeto.setStatus(StatusEntrada.ABERTA);
		objeto.setHoraDeEntrada(LocalDateTime.now());
		return objetosRepository.save(objeto);
	}

	public Objetos buscar(Long id) {
		return objetosRepository.findById(id).get();
	}

	public List<Objetos> listar() {
		return (List<Objetos>) objetosRepository.findAll();
	}
	
	public void finalizar(Long id) {
		Objetos objeto = buscar(id);
		if (objeto.getStatus().equals(StatusEntrada.FINALIZADA)) {
			throw new NegocioException("Entrada já finalizada!");
		} else {
			objeto.setHoraDeSaida(LocalDateTime.now());
			objeto.setStatus(StatusEntrada.FINALIZADA);
			objetosRepository.save(objeto);
		}
	}
}
