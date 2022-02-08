package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping(value="/cadastroUsuario", method=RequestMethod.GET )
	public String form() {
		return "entrada/cadastroUsuario";
	}
	
	@RequestMapping(value="/cadastroUsuario", method=RequestMethod.POST )
	public String form(Usuario usuario) {
		
		usuarioRepository.save(usuario);
		
		return "redirect:/cadastroUsuario";
	}
}
