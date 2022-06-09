package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Entrada implements Serializable {

	public static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime horaEntrada;

	@NotNull
	@Size(max = 30)
	private String veiculo;

	@NotNull
	@Size(min = 7, max = 7)
	private String placa;

	@NotNull
	@Size(max = 30)
	private String nomeMotorista;

	@NotNull
	@Range(min = 1101, max = 2432)
	private String numeroApt;

	@Enumerated(EnumType.STRING)
	private TipoEntrada tipo;

	@Enumerated(EnumType.STRING)
	private StatusEntrada status;

	@Size(max = 50)
	private String usuarioDaEntrada;

	@Size(max = 50)
	private String usuarioDaSaida;

	private LocalDateTime horaSaida;

	private LocalDateTime intervaloDatas;

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

	public TipoEntrada getTipo() {
		return tipo;
	}

	public void setTipo(TipoEntrada tipo) {
		this.tipo = tipo;
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

	public String getUsuarioDaEntrada() {
		return usuarioDaEntrada;
	}

	public void setUsuarioDaEntrada(String usuarioDaEntrada) {
		this.usuarioDaEntrada = usuarioDaEntrada;
	}

	public String getUsuarioDaSaida() {
		return usuarioDaSaida;
	}

	public void setUsuarioDaSaida(String usuarioDaSaida) {
		this.usuarioDaSaida = usuarioDaSaida;
	}

	public LocalDateTime getIntervaloDatas() {
		return intervaloDatas;
	}

	public void setIntervaloDatas(LocalDateTime intervaloDatas) {
		this.intervaloDatas = intervaloDatas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(placa);
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
		return Objects.equals(placa, other.placa);
	}

	public String converterData(LocalDateTime data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String dataFormatada = formatter.format(data);
		return dataFormatada;
	}

	public boolean checaStatus() {
		if (getStatus() == StatusEntrada.FINALIZADA) {
			return false;
		} else {
			return true;
		}
	}
}
