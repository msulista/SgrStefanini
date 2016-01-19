package com.stefanini.entidade;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SGR_PROJETO")
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
	private double budget;
	
	@Column(name = "CUSTO_PREVISTO", nullable = true)
	private double custoPrevisto;
	
	@Column(name = "SALDO", nullable = true)
	private double saldo;
	
	@ManyToMany
	@JoinTable(name = "SGR_PROJETO_RECURSO", joinColumns={@JoinColumn(name = "SGR_PROJETO_ID_RECURSO")}, inverseJoinColumns={@JoinColumn(name = "SGR_PROJETO_ID_PROJETO")})
	private List<Recurso> recursos;	
	
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

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public double getCustoPrevisto() {
		return custoPrevisto;
	}

	public void setCustoPrevisto(double custoPrevisto) {
		this.custoPrevisto = custoPrevisto;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public List<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(budget);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + codigo;
		temp = Double.doubleToLongBits(custoPrevisto);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((recursos == null) ? 0 : recursos.hashCode());
		temp = Double.doubleToLongBits(saldo);
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
		Projeto other = (Projeto) obj;
		if (Double.doubleToLongBits(budget) != Double.doubleToLongBits(other.budget))
			return false;
		if (codigo != other.codigo)
			return false;
		if (Double.doubleToLongBits(custoPrevisto) != Double.doubleToLongBits(other.custoPrevisto))
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
		if (recursos == null) {
			if (other.recursos != null)
				return false;
		} else if (!recursos.equals(other.recursos))
			return false;
		if (Double.doubleToLongBits(saldo) != Double.doubleToLongBits(other.saldo))
			return false;
		return true;
	}
	
	
}
