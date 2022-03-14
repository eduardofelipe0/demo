package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Entrada;
import com.example.demo.model.StatusEntrada;
import com.example.demo.repository.EntradaRepository;

@SuppressWarnings("unchecked")
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
	
	public Entrada buscarPlaca(String placa) {
		return entradaRepository.findByPlaca(placa);
	}
	
	public Entrada criar(Entrada entrada) {
		caracteres(entrada);
		entrada.setStatus(StatusEntrada.ABERTA);
		entrada.setHoraEntrada(LocalDateTime.now());
		return entradaRepository.save(entrada);
	}

	public List<Entrada> listar() {
		return (List<Entrada>) entradaRepository.findAll();
	}
	
	public void aberta(Entrada entrada) {
		entrada.getStatus().equals(StatusEntrada.ABERTA);
	}
	public List<Entrada> listarAbertas() {
		return (List<Entrada>) entradaRepository.findByStatus(StatusEntrada.FINALIZADA);
	}
	 // return (List<Entrada>) entradaRepository.findById(id).get().getStatus() == StatusEntrada.ABERTA;
	public void caracteres(Entrada entrada) {
		//Convertendo a String da placa para letras Maiúsculas
		entrada.setPlaca(entrada.getPlaca().toUpperCase());
	
		char[] palavras = entrada.getVeiculo().toCharArray();
		
		for(int i = 1; i < palavras.length; i++) {
            //Convertendo todas as letras para minúsculo;
            if(Character.isAlphabetic(palavras[i])) {
                palavras[i] = Character.toLowerCase(palavras[i]);
            }
            //Se o caracter anterior for espaço, então o atual será maiúsculo;
            if(Character.isWhitespace(palavras[i - 1])) {
                palavras[i] = Character.toUpperCase(palavras[i]);
            }
        }
        //A primeira letra de toda frase ou palavra será maiúscula;
        palavras[0] = Character.toUpperCase(palavras[0]);
        //Retorna o Array de char como String;
        String nomeConvertido = new String(palavras);       
        
        entrada.setVeiculo(nomeConvertido);
        
        //Fazendo a conversão do nome do motorista;
        char[] caractere = entrada.getNomeMotorista().toCharArray();
		
		for(int i = 1; i < caractere.length; i++) {
            if(Character.isAlphabetic(caractere[i])) {
                caractere[i] = Character.toLowerCase(caractere[i]);
            }
            if(Character.isWhitespace(caractere[i - 1])) {
                caractere[i] = Character.toUpperCase(caractere[i]);
            }
        }
        caractere[0] = Character.toUpperCase(caractere[0]);
        String nome = new String(caractere);       
        
        entrada.setNomeMotorista(nome);
    }
}
