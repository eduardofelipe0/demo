package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.Entrada;

public interface EntradaRepository extends CrudRepository<Entrada, String> {
	
	Entrada findByPlaca(String placa);
}
