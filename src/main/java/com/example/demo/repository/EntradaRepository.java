package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Entrada;
import com.example.demo.model.StatusEntrada;

@Repository
public interface EntradaRepository extends CrudRepository<Entrada, Long> {
	
	Entrada findByPlaca(String placa);
	
	/*@Query("from Entrada e where e.status = :status")
	Entrada findByStatus(StatusEntrada status);*/
	
	@Query("select entra from Entrada entra where entra.placa like %?1%")
	List<Entrada> findEntradaByPlaca(String placa);
	
	@Query("from Entrada e where e.status = :status")
	List<Entrada> findEntradaByStatus(StatusEntrada status);
}
