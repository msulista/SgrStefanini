package com.stefanini.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SGR_EQUIPE")
public class Equipe {

	@Id
	@Column(name="ID_EQUIPE", nullable=false, precision = 32)
	private int idEquipe;

	@Column(name="NOME", length=45, nullable=false)
	private String nome;

	@Column(name="REGISTRO_VALIDADE_INICIO", nullable=false)
	private Date registroValidadeInicio;

	@Column(name="REGISTRO_VALIDADE_FIM", nullable=true)
	private Date registroValidadeFim;

	public int getIdEquipe() {
		return idEquipe;
	}

	public void setIdEquipe(int idEquipe) {
		this.idEquipe = idEquipe;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
