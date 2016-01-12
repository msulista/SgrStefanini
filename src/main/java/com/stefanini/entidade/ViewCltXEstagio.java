package com.stefanini.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sgr_view_clt_x_estagio")
public class ViewCltXEstagio implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private long id;
	
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "nome")
	private String nome;
	
	@Column(name = "CLT")
	private long clt;
	
	@Column(name = "EST")
	private long est;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getClt() {
		return clt;
	}

	public void setClt(long clt) {
		this.clt = clt;
	}

	public long getEst() {
		return est;
	}

	public void setEst(long est) {
		this.est = est;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (clt ^ (clt >>> 32));
		result = prime * result + (int) (est ^ (est >>> 32));
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		ViewCltXEstagio other = (ViewCltXEstagio) obj;
		if (clt != other.clt)
			return false;
		if (est != other.est)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	
}
