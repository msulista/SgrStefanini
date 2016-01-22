package com.stefanini.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sgr_view_perfil_x_celula")
public class ViewPerfilXCelula implements BaseEntity, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private long id;
	
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "nome")
	private String nome;
	
	@Column(name = "ESTAGIO")
	private long estagio;
	
	@Column(name = "JUNIOR")
	private long junior;
	
	@Column(name = "PLENO")
	private long pleno;

	@Column(name = "SENIOR")
	private long senior;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getJunior() {
		return junior;
	}

	public void setJunior(long junior) {
		this.junior = junior;
	}

	public long getPleno() {
		return pleno;
	}

	public void setPleno(long pleno) {
		this.pleno = pleno;
	}

	public long getSenior() {
		return senior;
	}

	public void setSenior(long senior) {
		this.senior = senior;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (junior ^ (junior >>> 32));
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + (int) (pleno ^ (pleno >>> 32));
		result = prime * result + (int) (senior ^ (senior >>> 32));
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
		ViewPerfilXCelula other = (ViewPerfilXCelula) obj;
		if (id != other.id)
			return false;
		if (junior != other.junior)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (pleno != other.pleno)
			return false;
		if (senior != other.senior)
			return false;
		return true;
	}


	
}
