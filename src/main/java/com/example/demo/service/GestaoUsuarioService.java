package com.example.demo.service;

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
	
	public void deletar(String clienteId) {
		usuarioRepository.deleteById(clienteId);
	}
	
	public Usuario buscar(String nomeUsuario) {
		return usuarioRepository.buscarUsuarios(nomeUsuario).get(0);
	}
}
