package com.stefanini.entidade;

public class Relatorio {
	
	private int quantidade;
	private String nome;
		
	public Relatorio() {
	}

	public Relatorio(int quantidade, String nome) {
		this.quantidade = quantidade;
		this.nome = nome;
	}

	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
