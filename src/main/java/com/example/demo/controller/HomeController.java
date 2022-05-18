package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
public class HomeController {

	@GetMapping()
	public ModelAndView home(ModelMap model) {
		//model.addAttribute("usuarios", dao.getTodos());
		model.addAttribute("conteudo", "entrada/home");
		return new ModelAndView("layout", model);
	}

}
