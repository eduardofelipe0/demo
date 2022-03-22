package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	public Usuario findByNomeUsuario(String nomeUsuario);
	public Usuario findByNomeUsuarioAndSenha(String nomeUsuario, String senha);
	
	@Query("select user from Usuario user where user.nome like %?1%")
	List<Usuario> findUsuarioByName(String nome);
	
	@Query("from Usuario where nome_usuario = ?1")
	public List<Usuario> findByUsuarioByNomeUsuario(String nomeUsuario);

}
