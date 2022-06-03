package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Role;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.GestaoUsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private GestaoUsuarioService gestaoUsuarioService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping(value="/new", method=RequestMethod.GET )
	public ModelAndView formulario() {
		ModelAndView modelAndView = new ModelAndView();
		Usuario usuario = new Usuario();
		modelAndView.setViewName("usuario/cadastroUsuario");
		modelAndView.addObject("usuarioAtual", usuario);
		modelAndView.addObject("roles", Role.values());
		return modelAndView;
	}
	
	@RequestMapping(value="/cadastrar", method=RequestMethod.POST)
	public String cadastrar(Usuario usuario, RedirectAttributes atributes) {
		ModelAndView modelAndView = new ModelAndView();
		gestaoUsuarioService.cadastrar(usuario);
		atributes.addFlashAttribute("message", "Usuário cadastrado com sucesso.");
		return "redirect:/usuarios/new";
		/*ModelMap model = new ModelMap();
		
		try {
			gestaoUsuarioService.cadastrar(usuario);
			model.addAttribute("conteudo", "entrada/home");
			return new ModelAndView("layout", model);
		} catch (Exception e) {
			model.addAttribute("conteudo", "usuario/cadastroUsuario");
		}
		return modelAndView;*/
	}
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public ModelAndView listar() {
		ModelMap model = new ModelMap();
		model.addAttribute("usuarios", gestaoUsuarioService.listar());
        model.addAttribute("conteudo", "usuario/listaUsuarios");
        return new ModelAndView("layout", model);
	}
	
	@RequestMapping(value = "/permissao", method = RequestMethod.GET)
    public ModelAndView listarPorStatus(@RequestParam(value = "role", required = false) Role permiss) {
		ModelMap model = new ModelMap();
		List<Usuario> usuarios = gestaoUsuarioService.listarPorRole(permiss);
        if (permiss == null) {
            return new ModelAndView("redirect:/usuarios/listar");
        }
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("conteudo", "usuario/listaUsuarios");
        return new ModelAndView("layout", model);
    }

	@ModelAttribute("roles")
    public Role[] role() {
        return Role.values();
    }
	
	@RequestMapping(value="/buscar", method=RequestMethod.GET)
	public ModelAndView buscar(@RequestParam(value = "buscarUsuario", required = false) String buscarUsuario) {
		ModelMap model = new ModelMap();
		List<Usuario> usuarios = gestaoUsuarioService.buscarNome(buscarUsuario);
		model.addAttribute("usuarios", usuarios);
        model.addAttribute("conteudo", "usuario/listaUsuarios");
        return new ModelAndView("layout", model);
	}
	
	@RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
	public ModelAndView editar(@PathVariable ("id") Long id, RedirectAttributes atibutes) {
		ModelAndView modelAndView = new ModelAndView();
		Usuario usuario = gestaoUsuarioService.buscar(id);
		modelAndView.setViewName("usuario/cadastroUsuario");
		modelAndView.addObject("usuarioAtual", usuario);
		modelAndView.addObject("roles", Role.values());
		return modelAndView;
		
	}
	
	@RequestMapping(value="/excluir/{id}", method=RequestMethod.GET )
	public String remover(@PathVariable Long id, RedirectAttributes atributes) {
		Usuario usuario = usuarioRepository.findById(id).get();
		usuarioRepository.delete(usuario);
		atributes.addFlashAttribute("message", "Usuário removido com sucesso.");
	    return "redirect:/usuarios/listar";
	}
}
