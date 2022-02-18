package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Entrada;
import com.example.demo.model.StatusEntrada;

@Repository
public interface EntradaRepository extends CrudRepository<Entrada, Long> {
	
	Entrada findByPlaca(String placa);
	Entrada findByStatus(StatusEntrada status);

}
