package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Entrada;
import com.example.demo.model.Usuario;
import com.example.demo.repository.EntradaRepository;
import com.example.demo.service.GestaoEntradaService;
import com.example.demo.service.GestaoUsuarioService;

@Controller
@RequestMapping("/entradas")
public class EntradaController {
	
	@Autowired
	private EntradaRepository entradaRepository;
	@Autowired
	private GestaoEntradaService gestaoEntradaService;
	@Autowired
	private GestaoUsuarioService gestaoUsuarioService;
	
	@RequestMapping(value="/new", method=RequestMethod.GET )
	public ModelAndView formulario() {
		ModelAndView modelAndView = new ModelAndView();
		Entrada entrada = new Entrada();
		modelAndView.setViewName("entrada/registro");
		modelAndView.addObject("entradaAtual", entrada);
		return modelAndView;
	}
	
	@RequestMapping(value="/registrar", method=RequestMethod.POST)
	public ModelAndView registrar(Entrada entrada) {
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			gestaoEntradaService.criar(entrada);
			modelAndView.setViewName("index");
		} catch (Exception e) {
			modelAndView.setViewName("entrada/registro");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public ModelAndView listarEntradas() {
		ModelAndView modelAndView = new ModelAndView();
		List<Entrada> entradas = gestaoEntradaService.listar();
		modelAndView.setViewName("entrada/listaEntradas");
		modelAndView.addObject("entradas", entradas);
		return modelAndView;
	}
	
	@RequestMapping(value="/editar/{entradaId}", method=RequestMethod.GET )
	public ModelAndView editar(@PathVariable("entradaId") String entradaId) {
		ModelAndView modelAndView = new ModelAndView();
		Entrada entrada = gestaoEntradaService.buscar(entradaId);
		modelAndView.setViewName("entrada/registro");
		modelAndView.addObject("entradaAtual", entrada);
		return modelAndView;
	}
	
	@RequestMapping(value="/excluir/{entradaId}", method=RequestMethod.GET )
	public ModelAndView deletar(@PathVariable String entradaId) {
		Entrada entrada = entradaRepository.findById(entradaId).get();
		entradaRepository.delete(entrada);
		return listarEntradas();
	}
	
	//espaço reservado para o método finalizar;
	
}
