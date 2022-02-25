package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.GestaoUsuarioService;

@Controller
public class LoginController {
	
	@Autowired
	GestaoUsuarioService usuarioService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	/*@RequestMapping(value="/efetuarLogin", method=RequestMethod.POST)
	public String logar(Usuario usuario, RedirectAttributes redirect, HttpSession session) {
		
		usuarioService.logar(usuario);
		
		if(usuario != null) {
			session.setAttribute("usuarioLogado", usuario);
			return "redirect:entrada/home";
		} else {
			redirect.addFlashAttribute("mensagemErrro", "Login ou Senha Iválidos");
			return "redirect:/login";
		} */
	}