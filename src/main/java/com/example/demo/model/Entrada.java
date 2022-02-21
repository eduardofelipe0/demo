package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.example.demo.exception.NegocioException;

@Entity
public class Entrada implements Serializable{
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDateTime horaEntrada;
	
	@NotNull
	private String veiculo;
	
	@NotNull
	private String placa;
	
	@NotNull
	private String nomeMotorista;
	
	@NotNull
	private String numeroApt;	
	
	@Enumerated(EnumType.STRING)
	private StatusEntrada status;
	
	private LocalDateTime horaSaida;
	
	// Getters And Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getHoraEntrada() {
		return horaEntrada;
	}
	public void setHoraEntrada(LocalDateTime horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	public String getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(String veiculo) {
		this.veiculo = veiculo;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getNomeMotorista() {
		return nomeMotorista;
	}
	public void setNomeMotorista(String nomeMotorista) {
		this.nomeMotorista = nomeMotorista;
	}
	public String getNumeroApt() {
		return numeroApt;
	}
	public void setNumeroApt(String numeroApt) {
		this.numeroApt = numeroApt;
	}	
	public StatusEntrada getStatus() {
		return status;
	}
	public void setStatus(StatusEntrada status) {
		this.status = status;
	}
	public LocalDateTime getHoraSaida() {
		return horaSaida;
	}
	public void setHoraSaida(LocalDateTime horaSaida) {
		this.horaSaida = horaSaida;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((placa == null) ? 0 : placa.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entrada other = (Entrada) obj;
		if (placa == null) {
			if (other.placa != null)
				return false;
		} else if (!placa.equals(other.placa))
			return false;
		return true;
	}
	
	public boolean podeSerFinalizada() {
		return StatusEntrada.ABERTA.equals(getStatus());
	}
	
	public boolean naoPodeSerFinalizada() {
		return !podeSerFinalizada();
	}
	
	public void finalizar() {
		if(naoPodeSerFinalizada()) {
			throw new NegocioException("Entrada não pode ser finalizada");
		}
		
		setStatus(StatusEntrada.FINALIZADA);
		setHoraSaida(LocalDateTime.now());
	}
}
