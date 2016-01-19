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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SGR_RECURSO")
@NamedQueries({
	@NamedQuery(name = "Recurso.finAll", query = "SELECT r FROM Recurso r ORDER BY r.profissional.nome ASC"),
	@NamedQuery(name = "Recurso.findMatricula", query ="SELECT r FROM Recurso r WHERE r.profissional.matricula = :matricula ORDER BY r.profissional.nome ASC"),
	@NamedQuery(name = "Recurso.findId", query = "SELECT r FROM Recurso r where r.id = :id")
//	@NamedQuery(name = "Recurso.findAtivos", query = "SELECT r FROM Recurso r where r.id = :id"),
})
public class Recurso implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RECURSO", nullable = false, precision = 32)
	private Long id;
		
	@ManyToOne
	@JoinColumn(name = "ID_PROFISSIONAL", referencedColumnName = "ID_PROFISSIONAL")
	private Profissional profissional;
	
	@Column(name = "VALOR_HORA", nullable = false)
	private double valorHora;
	
	@ManyToMany
	@JoinTable(name = "SGR_PROJETO_RECURSO", joinColumns={@JoinColumn(name = "SGR_PROJETO_ID_RECURSO")}, inverseJoinColumns={@JoinColumn(name = "SGR_PROJETO_ID_PROJETO")})
	private List<Projeto> projetos;
		
	public Recurso() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public Profissional getProfissional() {
		return profissional;
	}
	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}
	public double getValorHora() {
		return valorHora;
	}
	public void setValorHora(double valorHora) {
		this.valorHora = valorHora;
	}
	public List<Projeto> getProjetos() {
		return projetos;
	}
	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((profissional == null) ? 0 : profissional.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((projetos == null) ? 0 : projetos.hashCode());
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
		if (profissional == null) {
			if (other.profissional != null)
				return false;
		} else if (!profissional.equals(other.profissional))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (projetos == null) {
			if (other.projetos != null)
				return false;
		} else if (!projetos.equals(other.projetos))
			return false;
		if (Double.doubleToLongBits(valorHora) != Double.doubleToLongBits(other.valorHora))
			return false;
		return true;
	}
	
	
	
}
