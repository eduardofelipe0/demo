package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Entrada;
import com.example.demo.model.StatusEntrada;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {
	
	Entrada findByPlaca(String placa);
	
	boolean existsByPlaca(String placa);
	
	@Query("select entra from Entrada entra where entra.placa like %?1%")
	List<Entrada> findEntradaByPlaca(String placa);
	
	@Query("select entra from Entrada entra where entra.placa = :#{#ent. placa}")
	Optional<Entrada> findEntradaPlaca(@Param("ent") Entrada ent);
	
	@Query("select entra from Entrada entra where horaEntrada between entra.horaEntrada and entra.horaEntrada")
	List<Entrada> findEntradaByDataHora(LocalDateTime horaEntrada, LocalDateTime horaEntrada1);
	
	@Query("select entra from Entrada entra where entra.horaEntrada = :horaEntrada")
	List<Entrada> findEntradaByData(LocalDate horaEntrada);
	
	@Query("select entra from Entrada entra where entra.nomeMotorista like %?1%")
	List<Entrada> findEntradaByName(String nomeMotorista);
	
	@Query("from Entrada e where e.status = :status")
	List<Entrada> findEntradaByStatus(StatusEntrada status);
}
