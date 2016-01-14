package com.stefanini.entidade;

public class Relatorio {
	
	private Long quantidade01;
	private String nome01;
	
	private Long quantidade02;
	private String nome02;
	
	private Long quantidade03;
	private String nome03;
	
	private Long quantidade04;
	private String nome04;
	
	private Double valorMedio;
		
	public Relatorio() {
	}
	
	//Relatorio Prifissionais por equipe
	public Relatorio(String nome01, Long quantidade01) {
		this.quantidade01 = quantidade01;
		this.nome01 = nome01;
	}	
	
	//Relatorio CLT x Estagio por equipe
	public Relatorio(String nome01, Long quantidade01, Long quantidade02) {
		this.quantidade01 = quantidade01;
		this.nome01 = nome01;
		this.quantidade02 = quantidade02;
	}
	
	//Relatorio Valor Médio Por Equipe
	public Relatorio(String nome01, Double valorMedio){
		this.nome01 = nome01;
		this.valorMedio = valorMedio;
	}

	public Relatorio(Long quantidade01, String nome01, Long quantidade02, String nome02) {
		this.quantidade01 = quantidade01;
		this.nome01 = nome01;
		this.quantidade02 = quantidade02;
		this.nome02 = nome02;
	}

	
	public Relatorio(Long quantidade01, String nome01, Long quantidade02, String nome02, Long quantidade03,
			String nome03) {
		this.quantidade01 = quantidade01;
		this.nome01 = nome01;
		this.quantidade02 = quantidade02;
		this.nome02 = nome02;
		this.quantidade03 = quantidade03;
		this.nome03 = nome03;
	}
	
	public Relatorio (String nome, Long quantidade01, Long quantidade02, Long quantidade03){
		this.nome01 = nome;
		this.quantidade01 = quantidade01;
		this.quantidade02 = quantidade02;
		this.quantidade03 = quantidade03;
	}
	
	public Relatorio (String nome, Long quantidade01, Long quantidade02, Long quantidade03, Long quantidade04){
		this.nome01 = nome;
		this.quantidade01 = quantidade01;
		this.quantidade02 = quantidade02;
		this.quantidade03 = quantidade03;
		this.quantidade04 = quantidade04;
	}

	public Long getQuantidade01() {
		return quantidade01;
	}
	public void setQuantidade01(Long quantidade01) {
		this.quantidade01 = quantidade01;
	}
	public String getNome01() {
		return nome01;
	}
	public void setNome01(String nome01) {
		this.nome01 = nome01;
	}

	public Long getQuantidade02() {
		return quantidade02;
	}

	public void setQuantidade02(Long quantidade02) {
		this.quantidade02 = quantidade02;
	}

	public String getNome02() {
		return nome02;
	}

	public void setNome02(String nome02) {
		this.nome02 = nome02;
	}

	public Long getQuantidade03() {
		return quantidade03;
	}

	public void setQuantidade03(Long quantidade03) {
		this.quantidade03 = quantidade03;
	}

	public String getNome03() {
		return nome03;
	}

	public void setNome03(String nome03) {
		this.nome03 = nome03;
	}
	
	public Double getValorMedio() {
		return valorMedio;
	}

	public void setValorMedio(Double valorMedio) {
		this.valorMedio = valorMedio;
	}
	
	

	public Long getQuantidade04() {
		return quantidade04;
	}

	public void setQuantidade04(Long quantidade04) {
		this.quantidade04 = quantidade04;
	}

	public String getNome04() {
		return nome04;
	}

	public void setNome04(String nome04) {
		this.nome04 = nome04;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome01 == null) ? 0 : nome01.hashCode());
		result = prime * result + ((nome02 == null) ? 0 : nome02.hashCode());
		result = prime * result + ((nome03 == null) ? 0 : nome03.hashCode());
		result = prime * result + ((nome04 == null) ? 0 : nome04.hashCode());
		result = prime * result + ((quantidade01 == null) ? 0 : quantidade01.hashCode());
		result = prime * result + ((quantidade02 == null) ? 0 : quantidade02.hashCode());
		result = prime * result + ((quantidade03 == null) ? 0 : quantidade03.hashCode());
		result = prime * result + ((quantidade04 == null) ? 0 : quantidade04.hashCode());
		result = prime * result + ((valorMedio == null) ? 0 : valorMedio.hashCode());
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
		Relatorio other = (Relatorio) obj;
		if (nome01 == null) {
			if (other.nome01 != null)
				return false;
		} else if (!nome01.equals(other.nome01))
			return false;
		if (nome02 == null) {
			if (other.nome02 != null)
				return false;
		} else if (!nome02.equals(other.nome02))
			return false;
		if (nome03 == null) {
			if (other.nome03 != null)
				return false;
		} else if (!nome03.equals(other.nome03))
			return false;
		if (nome04 == null) {
			if (other.nome04 != null)
				return false;
		} else if (!nome04.equals(other.nome04))
			return false;
		if (quantidade01 == null) {
			if (other.quantidade01 != null)
				return false;
		} else if (!quantidade01.equals(other.quantidade01))
			return false;
		if (quantidade02 == null) {
			if (other.quantidade02 != null)
				return false;
		} else if (!quantidade02.equals(other.quantidade02))
			return false;
		if (quantidade03 == null) {
			if (other.quantidade03 != null)
				return false;
		} else if (!quantidade03.equals(other.quantidade03))
			return false;
		if (quantidade04 == null) {
			if (other.quantidade04 != null)
				return false;
		} else if (!quantidade04.equals(other.quantidade04))
			return false;
		if (valorMedio == null) {
			if (other.valorMedio != null)
				return false;
		} else if (!valorMedio.equals(other.valorMedio))
			return false;
		return true;
	}

	
	
}
