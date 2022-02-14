package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
	
	Usuario findByNomeUsuario(String nomeUsuario);
	
	@Query("from Usuario where nomeUsuario=?1")
	public List<Usuario> buscarUsuarios(String nomeUsuario);
	
}
