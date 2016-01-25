package com.stefanini.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SGR_PROJETO_RECURSO")
public class ProjetoRecurso {

	@Id
	@Column(name = "ID_PROJETO")
	private Long idProjeto;
	
	@Column(name = "PROJETO_CODIGO")
	private Long projetoCodigo;
	
	@Column(name = "ID_RECURSO")
	private Long idRecurso;

	public Long getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(Long idProjeto) {
		this.idProjeto = idProjeto;
	}

	public Long getProjetoCodigo() {
		return projetoCodigo;
	}

	public void setProjetoCodigo(Long projetoCodigo) {
		this.projetoCodigo = projetoCodigo;
	}

	public Long getIdRecurso() {
		return idRecurso;
	}

	public void setIdRecurso(Long idRecurso) {
		this.idRecurso = idRecurso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idProjeto == null) ? 0 : idProjeto.hashCode());
		result = prime * result + ((idRecurso == null) ? 0 : idRecurso.hashCode());
		result = prime * result + ((projetoCodigo == null) ? 0 : projetoCodigo.hashCode());
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
		ProjetoRecurso other = (ProjetoRecurso) obj;
		if (idProjeto == null) {
			if (other.idProjeto != null)
				return false;
		} else if (!idProjeto.equals(other.idProjeto))
			return false;
		if (idRecurso == null) {
			if (other.idRecurso != null)
				return false;
		} else if (!idRecurso.equals(other.idRecurso))
			return false;
		if (projetoCodigo == null) {
			if (other.projetoCodigo != null)
				return false;
		} else if (!projetoCodigo.equals(other.projetoCodigo))
			return false;
		return true;
	}
	
	
}
