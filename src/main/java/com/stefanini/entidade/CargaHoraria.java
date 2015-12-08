package com.stefanini.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SGR_CARGA_HORARIA")
public class CargaHoraria {

	@Id
	@Column(name = "ID_CARGA_HORARIA", nullable = false, precision = 32)
	private int idCargaHoraria;

	@Column(name = "CARGA_HORARIA")
	private double cargaHoraria;

	@Column(name = "REGISTRO_VALIDADE_INICIO", nullable = false)
	private Date registroValidadeInicio;

	@Column(name = "REGISTRO_VALIDADE_FIM", nullable = true)
	private Date registroValidadeFim;

	public int getIdCargaHoraria() {
		return idCargaHoraria;
	}

	public void setIdCargaHoraria(int idCargaHoraria) {
		this.idCargaHoraria = idCargaHoraria;
	}

	public double getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(double cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Date getRegistroValidadeInicio() {
		return registroValidadeInicio;
	}

	public void setRegistroValidadeInicio(Date registroValidadeInicio) {
		this.registroValidadeInicio = registroValidadeInicio;
	}

	public Date getRegistroValidadeFim() {
		return registroValidadeFim;
	}

	public void setRegistroValidadeFim(Date registroValidadeFim) {
		this.registroValidadeFim = registroValidadeFim;
	}

}
