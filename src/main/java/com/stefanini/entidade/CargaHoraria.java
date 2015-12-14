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

	public Date getDataManipulacao() {
		return dataManipulacaoFim;
	}

	public void setDataManipulacao(Date dataManipulacaoFim) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cargaHoraria);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((registroValidadeFim == null) ? 0 : registroValidadeFim.hashCode());
		result = prime * result + ((registroValidadeInicio == null) ? 0 : registroValidadeInicio.hashCode());
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
		CargaHoraria other = (CargaHoraria) obj;
		if (Double.doubleToLongBits(cargaHoraria) != Double.doubleToLongBits(other.cargaHoraria))
			return false;
		if (id != other.id)
			return false;
		if (registroValidadeFim == null) {
			if (other.registroValidadeFim != null)
				return false;
		} else if (!registroValidadeFim.equals(other.registroValidadeFim))
			return false;
		if (registroValidadeInicio == null) {
			if (other.registroValidadeInicio != null)
				return false;
		} else if (!registroValidadeInicio.equals(other.registroValidadeInicio))
			return false;
		return true;
	}

	
}
