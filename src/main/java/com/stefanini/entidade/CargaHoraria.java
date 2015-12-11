package com.stefanini.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SGR_CARGA_HORARIA")
public class CargaHoraria {

	@Id
	@Column(name = "ID_CARGA_HORARIA", nullable = false, precision = 32)
	private long id;

	@Column(name = "CARGA_HORARIA")
	private double cargaHoraria;

	@Column(name = "REGISTRO_VALIDADE_INICIO", nullable = false)
	private Date registroValidadeInicio;

	@Column(name = "REGISTRO_VALIDADE_FIM", nullable = true)
	private Date registroValidadeFim;
	
	@Transient
	private Date dataManipulacaoFim;
	
	public CargaHoraria() {
		this.dataManipulacaoFim = new Date();
	}

	public Date getDataManipulacaoFim() {
		return dataManipulacaoFim;
	}

	public void setDataManipulacaoFim(Date dataManipulacaoFim) {
		this.dataManipulacaoFim = dataManipulacaoFim;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
