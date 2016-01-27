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
@Table(name = "SGR_PROJETO")
@NamedQueries({
	@NamedQuery(name = "Projeto.findAll", query = "SELECT p FROM Projeto p ORDER BY p.nome ASC"),
	@NamedQuery(name = "Projeto.findCodigo", query ="SELECT p FROM Projeto p WHERE p.codigo = :codigo ORDER BY p.nome ASC"),
	@NamedQuery(name = "Projeto.findId", query = "SELECT p FROM Projeto p WHERE p.id = :id"),
	@NamedQuery(name = "Projeto.findAtivos", query = "SELECT p FROM Projeto p WHERE p.registroValidadeFim IS NULL OR p.registroValidadeFim > CURRENT_DATE AND p.dataInicio <= CURRENT_DATE ORDER BY p.nome ASC"),
	@NamedQuery(name = "Projeto.checkCodigoParaEdicao", query ="SELECT p FROM Projeto p WHERE p.id = (SELECT MAX(pr.id)FROM Projeto pr WHERE pr.codigo = :codigo AND pr.registroValidadeInicio <= CURRENT_DATE())")
})
public class Projeto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PROJETO", nullable = false, precision = 32)
	private Long id;
	
	@Column(name = "CODIGO", nullable = false)
	private int codigo;
	
	@Column(name = "DATA_INICIO", nullable = false)
	private Date dataInicio;
	
	@Column(name = "DATA_FIM", nullable = true)
	private Date dataFim;
	
	@Column(name = "NOME", nullable = false)
	private String nome;
	
	@Column(name = "BUDGET", nullable = false)
	private Double budget;
	
	@Column(name = "CUSTO_PREVISTO", nullable = true)
	private Double custoPrevisto;
	
	@Column(name = "SALDO", nullable = true)
	private Double saldo;
	
	@Column(name = "REGISTRO_VALIDADE_INICIO", nullable = false)
	private Date registroValidadeInicio;

	@Column(name = "REGISTRO_VALIDADE_FIM", nullable = true)
	private Date registroValidadeFim;
	
	@JoinColumn(name = "ID_CELULA", referencedColumnName = "ID_CELULA")
	@ManyToOne
	private Celula celula;
	
	@JoinColumn(name = "ID_EQUIPE", referencedColumnName = "ID_EQUIPE")
	@ManyToOne
	private Equipe equipe;
	
	public Projeto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Double getCustoPrevisto() {
		return custoPrevisto;
	}

	public void setCustoPrevisto(Double custoPrevisto) {
		this.custoPrevisto = custoPrevisto;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
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

	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}

	
	
	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((budget == null) ? 0 : budget.hashCode());
		result = prime * result + ((celula == null) ? 0 : celula.hashCode());
		result = prime * result + codigo;
		result = prime * result + ((custoPrevisto == null) ? 0 : custoPrevisto.hashCode());
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((equipe == null) ? 0 : equipe.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((registroValidadeFim == null) ? 0 : registroValidadeFim.hashCode());
		result = prime * result + ((registroValidadeInicio == null) ? 0 : registroValidadeInicio.hashCode());
		result = prime * result + ((saldo == null) ? 0 : saldo.hashCode());
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
		Projeto other = (Projeto) obj;
		if (budget == null) {
			if (other.budget != null)
				return false;
		} else if (!budget.equals(other.budget))
			return false;
		if (celula == null) {
			if (other.celula != null)
				return false;
		} else if (!celula.equals(other.celula))
			return false;
		if (codigo != other.codigo)
			return false;
		if (custoPrevisto == null) {
			if (other.custoPrevisto != null)
				return false;
		} else if (!custoPrevisto.equals(other.custoPrevisto))
			return false;
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
		if (equipe == null) {
			if (other.equipe != null)
				return false;
		} else if (!equipe.equals(other.equipe))
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
		if (saldo == null) {
			if (other.saldo != null)
				return false;
		} else if (!saldo.equals(other.saldo))
			return false;
		return true;
	}

	
}
