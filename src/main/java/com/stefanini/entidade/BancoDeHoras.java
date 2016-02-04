package com.stefanini.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SGR_BANCO_HORAS")

public class BancoDeHoras implements Serializable, BaseEntity {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_BANCO_HORA")
	private Long id;
	
	@Column(name = "DATA")
	private Date data;
	
	@Column(name = "HORA_TRABALHADA")
	private double horario;
	
	@JoinColumn(name = "ID_ALOCACAO", referencedColumnName = "ID_ALOCACAO")
	@ManyToOne
	private Alocacao alocacao;
	
	@JoinColumn(name = "ID_PROJETO", referencedColumnName = "ID_PROJETO")
	@ManyToOne
	private Projeto projeto;
	
	@JoinColumn(name = "ID_RECURSO", referencedColumnName = "ID_RECURSO")
	@ManyToOne
	private Recurso recurso;
	
	
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}


	public Date getData() {
		return data;
	}


	public void setData(Date data) {
		this.data = data;
	}


	public double getHorario() {
		return horario;
	}


	public void setHorario(double horario) {
		this.horario = horario;
	}


	public Alocacao getAlocacao() {
		return alocacao;
	}


	public void setAlocacao(Alocacao alocacao) {
		this.alocacao = alocacao;
	}


	public Projeto getProjeto() {
		return projeto;
	}


	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}


	public Recurso getRecurso() {
		return recurso;
	}


	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alocacao == null) ? 0 : alocacao.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		long temp;
		temp = Double.doubleToLongBits(horario);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((projeto == null) ? 0 : projeto.hashCode());
		result = prime * result + ((recurso == null) ? 0 : recurso.hashCode());
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
		BancoDeHoras other = (BancoDeHoras) obj;
		if (alocacao == null) {
			if (other.alocacao != null)
				return false;
		} else if (!alocacao.equals(other.alocacao))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (Double.doubleToLongBits(horario) != Double.doubleToLongBits(other.horario))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (projeto == null) {
			if (other.projeto != null)
				return false;
		} else if (!projeto.equals(other.projeto))
			return false;
		if (recurso == null) {
			if (other.recurso != null)
				return false;
		} else if (!recurso.equals(other.recurso))
			return false;
		return true;
	}

}
