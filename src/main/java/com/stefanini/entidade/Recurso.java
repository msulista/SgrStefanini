package com.stefanini.entidade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SGR_RECURSO")
public class Recurso implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RECURSO", nullable = false, precision = 32)
	private Long id;
	
	@Column(name = "MATRICULA", nullable = false)
	private int matricula;
	
	@Column(name = "VALOR_HORA", nullable = false)
	private double valorHora;
	
	@ManyToMany
	@JoinTable(name = "PROJETO_RECURSO", joinColumns={@JoinColumn(name = "ID_RECURSO")}, inverseJoinColumns={@JoinColumn(name = "ID_PROJETO")})
	private List<Profissional> profissionais;
	
	
	public Recurso() {
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public double getValorHora() {
		return valorHora;
	}
	public void setValorHora(double valorHora) {
		this.valorHora = valorHora;
	}
	public List<Profissional> getProfissionais() {
		return profissionais;
	}
	public void setProfissionais(List<Profissional> profissionais) {
		this.profissionais = profissionais;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + matricula;
		result = prime * result + ((profissionais == null) ? 0 : profissionais.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorHora);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Recurso other = (Recurso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (matricula != other.matricula)
			return false;
		if (profissionais == null) {
			if (other.profissionais != null)
				return false;
		} else if (!profissionais.equals(other.profissionais))
			return false;
		if (Double.doubleToLongBits(valorHora) != Double.doubleToLongBits(other.valorHora))
			return false;
		return true;
	}		
	
	
}
