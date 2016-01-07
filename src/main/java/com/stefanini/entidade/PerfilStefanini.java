package com.stefanini.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.stefanini.util.DateUtil;

@Entity
@Table(name = "SGR_PERFIL_STEFANINI")
@NamedQueries({
	@NamedQuery(name = "PerfilStefanini.findAll", query = "SELECT p FROM PerfilStefanini p ORDER BY p.nome ASC"),
	@NamedQuery(name = "PerfilStefanini.findAtivos", query = "SELECT p FROM PerfilStefanini p WHERE p.registroValidadeFim IS NULL OR p.registroValidadeFim > CURRENT_DATE AND p.registroValidadeInicio <= CURRENT_DATE ORDER BY p.nome ASC"),
	@NamedQuery(name = "PerfilStefanini.findNome", query = "SELECT p FROM PerfilStefanini p WHERE p.nome = :nome AND p.registroValidadeFim IS NULL OR p.registroValidadeFim > CURRENT_DATE AND p.registroValidadeInicio <= CURRENT_DATE ORDER BY p.nome ASC"),
	@NamedQuery(name = "PerfilStefanini.findId", query = "SELECT p FROM PerfilStefanini p WHERE p.id = :id AND p.registroValidadeFim IS NULL OR p.registroValidadeFim > CURRENT_DATE AND p.registroValidadeInicio <= CURRENT_DATE ORDER BY p.nome ASC")
})
public class PerfilStefanini implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PERFIL_STEFANINI", nullable = false, precision = 32)
	private Long id;

	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "REGISTRO_HISTORICO", nullable = true)
	private int registro;
	
	@Column(name = "VALOR_INICIAL", nullable = false)
	private Double valorInicial;

	@Column(name = "VALOR_FINAL", nullable = false)
	private Double valorFinal;

	@Column(name = "NOME", nullable = false)
	private String nome;

	@Column(name = "REGISTRO_VALIDADE_INICIO", nullable = false)
	private Date registroValidadeInicio;

	@Column(name = "REGISTRO_VALIDADE_FIM", nullable = true)
	private Date registroValidadeFim;

	@Transient
	private Date dataManipulacao;
	
	public PerfilStefanini() {
		this.registroValidadeInicio = DateUtil.getProximoDiaUtil();
		this.dataManipulacao = DateUtil.getProximoDiaUtil();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(Double valorInicial) {
		this.valorInicial = valorInicial;
	}

	public Double getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(Double valorFinal) {
		this.valorFinal = valorFinal;
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

	public void setRegistroValidadeFim(Date registroValidade) {
		this.registroValidadeFim = registroValidade;
	}

	public Date getDataManipulacao() {
		return dataManipulacao;
	}

	public void setDataManipulacao(Date dataManipulacao) {
		this.dataManipulacao = dataManipulacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((registroValidadeFim == null) ? 0 : registroValidadeFim.hashCode());
		result = prime * result + ((registroValidadeInicio == null) ? 0 : registroValidadeInicio.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorFinal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(valorInicial);
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
		PerfilStefanini other = (PerfilStefanini) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
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
		if (Double.doubleToLongBits(valorFinal) != Double.doubleToLongBits(other.valorFinal))
			return false;
		if (Double.doubleToLongBits(valorInicial) != Double.doubleToLongBits(other.valorInicial))
			return false;
		return true;
	}

}
