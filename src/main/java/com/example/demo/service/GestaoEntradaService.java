package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.NegocioException;
import com.example.demo.model.Entrada;
import com.example.demo.model.StatusEntrada;
import com.example.demo.repository.EntradaRepository;
import com.example.demo.user.ControllerUsuario;

@Service
@Transactional
public class GestaoEntradaService {

	@Autowired
	private EntradaRepository entradaRepository;

	public void finalizar(Long id) {
		Entrada entrada = buscar(id);
		if (entrada.getStatus().equals(StatusEntrada.FINALIZADA)) {
			throw new NegocioException("Entrada já finalizada!");
		} else {
			entrada.setHoraSaida(LocalDateTime.now());
			entrada.setStatus(StatusEntrada.FINALIZADA);
			entradaRepository.save(entrada);
		}
	}

	public Entrada buscar(Long id) {
		return entradaRepository.findById(id).get();
	}

	public List<Entrada> buscarPlaca(String placa) {
		return (List<Entrada>) entradaRepository.findEntradaByPlaca(placa);
	}

	public List<Entrada> buscarData(LocalDate horaEntrada) {
		return (List<Entrada>) entradaRepository.findEntradaByData(horaEntrada);
	}

	public List<Entrada> buscarDataIntervalo(LocalDateTime intervaloDatas) {
		return (List<Entrada>) entradaRepository.findEntradaByDataHora(intervaloDatas, intervaloDatas);
	}

	public List<Entrada> buscarPorNome(String nomeMotorista) {
		return (List<Entrada>) entradaRepository.findEntradaByName(nomeMotorista);
	}

	public String usuarioLogado() {
		ControllerUsuario uc = new ControllerUsuario();
		String nome = uc.getUsuario().getNomeUsuario();
		return nome;
	}

	public void criar(Entrada entrada) throws Exception {

		/*
		 * Optional<Entrada> entra = entradaRepository.findEntradaPlaca(entrada); if
		 * (entra.isPresent()) { System.out.println("Erro!"); } else { }
		 */
		SecurityContext sc = SecurityContextHolder.getContext();
		String username = sc.getAuthentication().getName();
		caracteres(entrada);
		entrada.setStatus(StatusEntrada.ABERTA);
		entrada.setNomeUsuario(username);
		entrada.setHoraEntrada(LocalDate.now());
		entradaRepository.save(entrada);
	}

	public List<Entrada> listar() {
		return (List<Entrada>) entradaRepository.findAll();
	}

	public List<Entrada> listAll() {
		return entradaRepository.findAll(Sort.by("placa").ascending());
	}

	public List<Entrada> listarPorStatus(StatusEntrada status) {
		return (List<Entrada>) entradaRepository.findEntradaByStatus(status);
	}

	public void caracteres(Entrada entrada) {
		// Convertendo a String da placa para letras Maiúsculas
		entrada.setPlaca(entrada.getPlaca().toUpperCase());

		char[] palavras = entrada.getVeiculo().toCharArray();

		for (int i = 1; i < palavras.length; i++) {
			// Convertendo todas as letras para minúsculo;
			if (Character.isAlphabetic(palavras[i])) {
				palavras[i] = Character.toLowerCase(palavras[i]);
			}
			// Se o caracter anterior for espaço, então o atual será maiúsculo;
			if (Character.isWhitespace(palavras[i - 1])) {
				palavras[i] = Character.toUpperCase(palavras[i]);
			}
		}
		// A primeira letra de toda frase ou palavra será maiúscula;
		palavras[0] = Character.toUpperCase(palavras[0]);
		// Retorna o Array de char como String;
		String nomeConvertido = new String(palavras);

		entrada.setVeiculo(nomeConvertido);

		// Fazendo a conversão do nome do motorista;
		char[] caractere = entrada.getNomeMotorista().toCharArray();

		for (int i = 1; i < caractere.length; i++) {
			if (Character.isAlphabetic(caractere[i])) {
				caractere[i] = Character.toLowerCase(caractere[i]);
			}
			if (Character.isWhitespace(caractere[i - 1])) {
				caractere[i] = Character.toUpperCase(caractere[i]);
			}
		}
		caractere[0] = Character.toUpperCase(caractere[0]);
		String nome = new String(caractere);

		entrada.setNomeMotorista(nome);
	}
}
