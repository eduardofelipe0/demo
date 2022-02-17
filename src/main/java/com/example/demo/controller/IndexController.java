package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Usuario;
import com.example.demo.service.GestaoUsuarioService;

@Controller
@RequestMapping("/home")
public class IndexController {
	
	@Autowired
	private GestaoUsuarioService gestaoUsuarioService;
	private Usuario usuario;
	
	@GetMapping()
	public ModelAndView home() {
		// usuarioLogado();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("entrada/home");
		return modelAndView;
	}
	
	/* private void usuarioLogado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!(authentication instanceof AnonymousAuthenticationToken)) {
			String nomeUsuario = authentication.getName();
			usuario = gestaoUsuarioService.buscar(nomeUsuario);
		}
	} */
}
