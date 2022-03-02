package com.example.demo.security;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

@Repository
@Transactional
public class UserDetailsServiceConfigurer implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String nomeUsuario) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByNomeUsuario(nomeUsuario);
		
		if(usuario != null){
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRole());
			Set<GrantedAuthority> authorities = new HashSet<>();
			authorities.add(authority);
			User user = new User(usuario.getNomeUsuario(), usuario.getSenha(), authorities);
			return user;
			
		}
		return null;
	}

}