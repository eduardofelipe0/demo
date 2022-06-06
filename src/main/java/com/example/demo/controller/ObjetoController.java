package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Objetos;
import com.example.demo.model.TipoEntradaObjeto;
import com.example.demo.repository.ObjetosRepository;
import com.example.demo.service.GestaoObjetoService;

@Controller
@RequestMapping("/objetos")
public class ObjetoController {

	@Autowired
	private GestaoObjetoService gestaoObjetoService;
	@Autowired
	private ObjetosRepository objetosRepository;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView formulario() {
		ModelAndView modelAndView = new ModelAndView();
		Objetos objeto = new Objetos();
		modelAndView.setViewName("objetos/cadastrar");
		modelAndView.addObject("objetoAtual", objeto);
		modelAndView.addObject("tipoObjetos", TipoEntradaObjeto.values());
		return modelAndView;
	}

	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String cadastrar(Objetos objeto, RedirectAttributes atributes) {

		try {
			gestaoObjetoService.cadastrar(objeto);
			atributes.addFlashAttribute("message", "Entrada de objeto registrada com sucesso.");
			return "redirect:/objetos/new";
		} catch (Exception e) {
			return "e";
		}
	}

	@GetMapping("/listar")
	public ModelAndView listarObjetos() {
		ModelMap model = new ModelMap();
		model.addAttribute("objetos", gestaoObjetoService.listar());
		model.addAttribute("conteudo", "objetos/listaObjetos");
		return new ModelAndView("layout", model);
	}

	@RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Long id, RedirectAttributes atributes) {
		ModelAndView modelAndView = new ModelAndView();
		Objetos objeto = gestaoObjetoService.buscar(id);
		modelAndView.setViewName("objetos/cadastrar");
		atributes.addFlashAttribute("message", "Objeto editado com sucesso.");
		modelAndView.addObject("objetoAtual", objeto);
		return modelAndView;
	}

	@RequestMapping(value = "/excluir/{id}", method = RequestMethod.GET)
	public String remover(@PathVariable Long id, RedirectAttributes atributes) {
		Objetos objeto = objetosRepository.findById(id).get();
		objetosRepository.delete(objeto);
		atributes.addFlashAttribute("message", "Objeto removido com sucesso.");
		return "redirect:/objetos/listar";
	}

	@RequestMapping(value = "/finalizar/{id}", method = RequestMethod.GET)
	public String finalizar(@PathVariable Long id, RedirectAttributes atributes) {
		gestaoObjetoService.finalizar(id);
		atributes.addFlashAttribute("message", "Saída de objeto registrada com sucesso.");
		return "redirect:/objetos/listar";
	}
}
