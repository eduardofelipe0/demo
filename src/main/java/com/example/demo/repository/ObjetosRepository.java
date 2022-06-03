package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Objetos;

@Repository
public interface ObjetosRepository extends JpaRepository<Objetos, Long> {
	
}
