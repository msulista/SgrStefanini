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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "SGR_RECURSO")
@NamedQueries({
	@NamedQuery(name = "Recurso.findAll", query = "SELECT r FROM Recurso r"),
	@NamedQuery(name = "Recurso.findMatricula", query ="SELECT r FROM Recurso r WHERE r.profissional.matricula = :matricula AND r.registroValidadeInicio <= CURRENT_DATE AND (r.registroValidadeFim IS NULL OR r.registroValidadeFim >= CURRENT_DATE) ORDER BY r.profissional.nome ASC"),
	@NamedQuery(name = "Recurso.findId", query = "SELECT r FROM Recurso r where r.id = :id"),
	@NamedQuery(name = "Recurso.findMatriculaParaEdicao", query ="SELECT r FROM Recurso r WHERE r.id = (SELECT MAX(rs.id)FROM Recurso rs WHERE rs.profissional.matricula = :matricula)"),
	@NamedQuery(name = "Recurso.findByEquipe", query = "SELECT r FROM Recurso r WHERE r.profissional.equipe.id = :id AND r.registroValidadeInicio <= CURRENT_DATE AND (r.registroValidadeFim IS NULL OR r.registroValidadeFim >= CURRENT_DATE) ORDER BY r.profissional.nome ASC"),
	@NamedQuery(name = "Recurso.findByCelula", query = "SELECT r FROM Recurso r WHERE r.profissional.celula.id = :id AND r.registroValidadeInicio <= CURRENT_DATE AND (r.registroValidadeFim IS NULL OR r.registroValidadeFim >= CURRENT_DATE) ORDER BY r.profissional.nome ASC"),
	@NamedQuery(name = "Recurso.findByRecursoEquipeECelula", query = "SELECT r FROM Recurso r WHERE r.registroValidadeInicio <= CURRENT_DATE AND (r.registroValidadeFim IS NULL OR r.registroValidadeFim >= CURRENT_DATE) AND r.profissional.celula.id = :idCelula AND r.profissional.equipe.id = :idEquipe ORDER BY r.profissional.nome ASC")
})
public class Recurso implements BaseEntity, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RECURSO", nullable = false, precision = 32)
	private Long id;
		
	
	@JoinColumn(name = "SGR_PROFISSIONAL_ID_PROFISSIONAL", referencedColumnName = "ID_PROFISSIONAL")
	@ManyToOne
	private Profissional profissional;

	
	@Column(name = "REGISTRO_VALIDADE_INICIO")
	private Date registroValidadeInicio;
	
	@Column(name = "REGISTRO_VALIDADE_FIM")
	private Date registroValidadeFim;
	

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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((profissional == null) ? 0 : profissional.hashCode());
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
		Recurso other = (Recurso) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (profissional == null) {
			if (other.profissional != null)
				return false;
		} else if (!profissional.equals(other.profissional))
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
