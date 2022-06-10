package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Objetos;
import com.example.demo.model.TipoEntradaObjeto;

@Repository
public interface ObjetosRepository extends JpaRepository<Objetos, Long> {
	
	@Query("from Objetos ob where ob.tipoEntradaObjeto = :tipoEntradaObjeto")
	List<Objetos> findObjetosByTipoEntradaObjeto(TipoEntradaObjeto tipoEntradaObjeto);
}
