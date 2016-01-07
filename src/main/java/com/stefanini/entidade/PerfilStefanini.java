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
	@NamedQuery(name = "PerfilStefanini.findId", query = "SELECT p FROM PerfilStefanini p WHERE p.id = :id AND p.registroValidadeFim IS NULL OR p.registroValidadeFim > CURRENT_DATE AND p.registroValidadeInicio <= CURRENT_DATE ORDER BY p.nome ASC"),
	@NamedQuery(name = "PerfilStefanini.findCodigoParaHistorico", query = "SELECT p FROM PerfilStefanini p WHERE p.codigo = :codigo ORDER BY p.id DESC"),
	@NamedQuery(name = "PerfilStefanini.findCodigoParaEdicao", query ="SELECT p FROM PerfilStefanini p WHERE p.id = (SELECT MAX(pr.id)FROM PerfilStefanini pr WHERE pr.codigo = :codigo)"),
	@NamedQuery(name = "PerfilStefanini.findMaxId", query ="SELECT p FROM PerfilStefanini p WHERE p.id = (SELECT MAX(pr.id)FROM PerfilStefanini pr)")
})
public class PerfilStefanini implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PERFIL_STEFANINI", nullable = false, precision = 32)
	private Long id;

	@Column(name = "CODIGO", nullable = false)
	private Long codigo;
	
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

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((dataManipulacao == null) ? 0 : dataManipulacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((registroValidadeFim == null) ? 0 : registroValidadeFim.hashCode());
		result = prime * result + ((registroValidadeInicio == null) ? 0 : registroValidadeInicio.hashCode());
		result = prime * result + ((valorFinal == null) ? 0 : valorFinal.hashCode());
		result = prime * result + ((valorInicial == null) ? 0 : valorInicial.hashCode());
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
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (dataManipulacao == null) {
			if (other.dataManipulacao != null)
				return false;
		} else if (!dataManipulacao.equals(other.dataManipulacao))
			return false;
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
		if (valorFinal == null) {
			if (other.valorFinal != null)
				return false;
		} else if (!valorFinal.equals(other.valorFinal))
			return false;
		if (valorInicial == null) {
			if (other.valorInicial != null)
				return false;
		} else if (!valorInicial.equals(other.valorInicial))
			return false;
		return true;
	}

	
}
