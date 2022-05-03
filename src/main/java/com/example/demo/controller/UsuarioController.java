package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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
	public ModelAndView cadastrar(Usuario usuario) {
		ModelAndView modelAndView = new ModelAndView();
		
		try {
			gestaoUsuarioService.cadastrar(usuario);
			modelAndView.setViewName("/entrada/home");
		} catch (Exception e) {
			modelAndView.setViewName("/login");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/listar", method=RequestMethod.GET)
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView();
		List<Usuario> usuarios = gestaoUsuarioService.listar();
		modelAndView.setViewName("usuario/listaUsuarios");
		modelAndView.addObject("usuarios", usuarios);
		return modelAndView;
	}
	
	@RequestMapping(value = "/permissao", method = RequestMethod.GET)
    public ModelAndView listarPorStatus(@RequestParam(value = "role", required = false) Role permiss) {
		ModelAndView modelAndView = new ModelAndView();
		List<Usuario> usuarios = gestaoUsuarioService.listarPorRole(permiss);
        if (permiss == null) {
            return new ModelAndView("redirect:/usuarios/listar");
        }
        modelAndView.setViewName("usuario/listaUsuarios");
        modelAndView.addObject("usuarios", usuarios);
        return modelAndView;
    }

	@ModelAttribute("roles")
    public Role[] role() {
        return Role.values();
    }
	
	@RequestMapping(value="/buscar", method=RequestMethod.GET)
	public ModelAndView buscar(@RequestParam(value = "buscarUsuario", required = false) String buscarUsuario) {
		ModelAndView modelAndView = new ModelAndView();
		List<Usuario> usuarios = gestaoUsuarioService.buscarNome(buscarUsuario);
		modelAndView.setViewName("usuario/listaUsuarios");
		modelAndView.addObject("usuarios", usuarios);
		return modelAndView;
	}
	
	@RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
	public ModelAndView editar(@PathVariable ("id") Long id) {
		ModelAndView modelAndView = new ModelAndView();
		Usuario usuario = gestaoUsuarioService.buscar(id);
		modelAndView.setViewName("usuario/cadastroUsuario");
		modelAndView.addObject("usuarioAtual", usuario);
		modelAndView.addObject("roles", Role.values());
		return modelAndView;
	}
	
	@RequestMapping(value="/excluir/{id}", method=RequestMethod.GET )
	public ModelAndView remover(@PathVariable Long id) {
		Usuario usuario = usuarioRepository.findById(id).get();
		usuarioRepository.delete(usuario);
		return listar();
	}
}
