package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Entrada;
import com.example.demo.repository.EntradaRepository;

@Controller
public class EntradaController {
	
	@Autowired 
	private EntradaRepository rer;
	
	@RequestMapping(value="/registroEntrada", method=RequestMethod.GET )
	public String form() {
		return "entrada/registro";
	}
	
	@RequestMapping(value="/registroEntrada", method=RequestMethod.POST )
	public String registrarEntrada(Entrada entrada) {
		
		rer.save(entrada);
		
		return "redirect:/registroEntrada";
	}
	
	@RequestMapping("/entradas")
	public ModelAndView listarEntradas() {
		ModelAndView mv = new ModelAndView("entrada/listaEntradas");
		Iterable<Entrada> entradas = rer.findAll();
		mv.addObject("entradas", entradas);
		return mv;
	}
	
	@RequestMapping("/deletar")
	public String deletarEvento(String placa){
		Entrada entrada = rer.findByPlaca(placa);
		rer.delete(entrada);
		return "redirect:/entradas";
	}
	
	/*@RequestMapping(value="/{nome}", method=RequestMethod.GET )
	public ModelAndView buscarEntradas() {
		ModelAnd
	}
	
	*/
}
