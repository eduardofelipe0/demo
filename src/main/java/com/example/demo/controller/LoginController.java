package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.demo.model.Usuario;
import com.example.demo.service.GestaoUsuarioService;

@Controller
public class LoginController {
	
	@Autowired
	GestaoUsuarioService usuarioService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="/efetuarLogin", method=RequestMethod.GET)
	public String logar(Usuario usuario, RedirectAttributes redirect, HttpSession session) {
		
		usuarioService.logar(usuario);
		
		if(usuario != null) {
			session.setAttribute("usuarioLogado", usuario);
			return "redirect:entrada/home";
		} else {
			redirect.addFlashAttribute("mensagemErrro", "Login ou Senha Iválidos");
			return "redirect:/login";
		}
	}
	
}