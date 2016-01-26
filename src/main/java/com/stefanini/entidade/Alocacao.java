package com.stefanini.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SGR_ALOCACAO")
@NamedQueries({
	@NamedQuery(name = "Projeto.findAll", query = "SELECT a FROM Alocacao a ORDER BY a.projeto.nome ASC"),
	@NamedQuery(name = "Projeto.findRecursosPorProjeto", query ="SELECT a FROM Alocacao a WHERE a.projeto.codigo = :codigo AND (a.dataFim IS NULL OR a.dataFim > CURRENT_DATE) AND a.dataInicio <= CURRENT_DATE ORDER BY a.recurso.profissional.nome ASC"),
	@NamedQuery(name = "Projeto.findId", query = "SELECT a FROM Alocacao a WHERE a.id = :id"),
	@NamedQuery(name = "Projeto.findAtivos", query = "SELECT a FROM Alocacao a WHERE a.dataFim IS NULL OR a.dataFim > CURRENT_DATE AND a.dataInicio <= CURRENT_DATE ORDER BY a.recurso.profissional.nome ASC")	
})
public class Alocacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ALOCACAO", nullable = false, precision = 32)
	private Long id;
	
	@JoinColumn(name = "ID_PROJETO", referencedColumnName = "ID_PROJETO")
	@ManyToOne
	private Projeto projeto;
	
	@JoinColumn(name = "ID_RECURSO", referencedColumnName = "ID_RECURSO")
	@ManyToOne
	private Recurso recurso;
	
	@Column(name = "DATA_FIM", nullable = true)
	private Date dataFim;
	
	@Column(name = "DATA_INICIO", nullable = false)
	private Date dataInicio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
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
		Alocacao other = (Alocacao) obj;
		if (dataFim == null) {
			if (other.dataFim != null)
				return false;
		} else if (!dataFim.equals(other.dataFim))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
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
