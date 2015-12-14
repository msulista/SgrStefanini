package com.stefanini.entidade;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SGR_FORMA_CONTRATACAO")
public class FormaContratacao {

	@Id
	@Column(name = "ID_FORMA_CONTRATACAO", nullable = false, precision = 32)
	private long id;

	@Column(name = "NOME")
	private String nome;

	@Column(name = "REGISTRO_VALIDADE_INICIO", nullable = false)
	private Date registroValidadeInicio;

	@Column(name = "REGISTRO_VALIDADE_FIM", nullable = true)
	private Date registroValidadeFim;
	
	@Transient
	private Date dataManipulacaoFim;
	
	public FormaContratacao() {
		this.dataManipulacaoFim = new Date();
	}

	public Date getDataManipulacaoFim() {
		return dataManipulacaoFim;
	}

	public void setDataManipulacaoFim(Date dataManipulacaoFim) {
		this.dataManipulacaoFim = dataManipulacaoFim;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
