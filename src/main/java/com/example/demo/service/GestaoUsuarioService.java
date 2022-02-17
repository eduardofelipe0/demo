package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

@Service
public class GestaoUsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario cadastrar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}
	public List<Usuario> listar() {
		return (List<Usuario>) usuarioRepository.findAll();
	}
	
	public Usuario buscar(Long id) {
		return usuarioRepository.findById(id).get();
	}
	
	public void deletar(Usuario usuario) {
		usuarioRepository.delete(usuario);
	}
}
