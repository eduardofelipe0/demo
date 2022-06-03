package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Objetos {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime horaDeEntrada;

	@Size(max = 30)
	private String porteiro;

	@NotNull
	@Size(max = 30)
	private String funcionario;

	@NotNull
	@Size(max = 500)
	private String comentario;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime horaDeSaida;

	@Enumerated(EnumType.STRING)
	private StatusEntrada status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getHoraDeEntrada() {
		return horaDeEntrada;
	}

	public void setHoraDeEntrada(LocalDateTime horaDeEntrada) {
		this.horaDeEntrada = horaDeEntrada;
	}

	public String getPorteiro() {
		return porteiro;
	}

	public void setPorteiro(String porteiro) {
		this.porteiro = porteiro;
	}

	public String getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDateTime getHoraDeSaida() {
		return horaDeSaida;
	}

	public void setHoraDeSaida(LocalDateTime horaDeSaida) {
		this.horaDeSaida = horaDeSaida;
	}

	public StatusEntrada getStatus() {
		return status;
	}

	public void setStatus(StatusEntrada status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Objetos other = (Objetos) obj;
		return Objects.equals(id, other.id);
	}

}
