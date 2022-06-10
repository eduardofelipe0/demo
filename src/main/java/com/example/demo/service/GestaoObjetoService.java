package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.exception.NegocioException;
import com.example.demo.model.Objetos;
import com.example.demo.model.StatusEntrada;
import com.example.demo.model.TipoEntradaObjeto;
import com.example.demo.repository.ObjetosRepository;

@Service
public class GestaoObjetoService {

	@Autowired
	private ObjetosRepository objetosRepository;
	
	public Objetos cadastrar(Objetos objeto) {
		if(objeto.getTipoEntradaObjeto() == TipoEntradaObjeto.UTILIZADOS_NO_HOTEL) {
			SecurityContext sc = SecurityContextHolder.getContext();
			String username = sc.getAuthentication().getName();
			objeto.setPorteiroDeEntrada(username);
			objeto.setPorteiroDeSaida(username);
			objeto.setStatus(StatusEntrada.FINALIZADA);
			objeto.setHoraDeEntrada(LocalDateTime.now());
			objeto.setHoraDeSaida(LocalDateTime.now());
			return objetosRepository.save(objeto);
		}
		else {
			SecurityContext sc = SecurityContextHolder.getContext();
			String username = sc.getAuthentication().getName();
			objeto.setPorteiroDeEntrada(username);
			objeto.setStatus(StatusEntrada.ABERTA);
			objeto.setHoraDeEntrada(LocalDateTime.now());
			return objetosRepository.save(objeto);
		}
	}

	public Objetos buscar(Long id) {
		return objetosRepository.findById(id).get();
	}
	public List<Objetos> listarPorTipoEntrada(TipoEntradaObjeto tipo) {
		return (List<Objetos>) objetosRepository.findObjetosByTipoEntradaObjeto(tipo);
	}
	public List<Objetos> listar() {
		return (List<Objetos>) objetosRepository.findAll();
	}
	
	public void finalizar(Long id) {
		Objetos objeto = buscar(id);
		if (objeto.getStatus().equals(StatusEntrada.FINALIZADA)) {
			throw new NegocioException("Entrada já finalizada!");
		} else {
			SecurityContext sc = SecurityContextHolder.getContext();
			String username = sc.getAuthentication().getName();
			objeto.setPorteiroDeSaida(username);
			objeto.setHoraDeSaida(LocalDateTime.now());
			objeto.setStatus(StatusEntrada.FINALIZADA);
			objetosRepository.save(objeto);
		}
	}
}
