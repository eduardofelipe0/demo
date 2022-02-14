package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Entrada;

@Repository
public interface EntradaRepository extends CrudRepository<Entrada, String> {
	
	Entrada findByPlaca(String placa);

}
