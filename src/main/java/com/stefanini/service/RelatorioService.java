package com.stefanini.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Profissional;
import com.stefanini.entidade.Relatorio;
import com.stefanini.util.JPAUtil;

public class RelatorioService {
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> profissionaisPorEquipe(){		
		EntityManager manager = JPAUtil.getEntityManager();		
		String profisionalPorEquipe = "SELECT new com.stefanini.entidade.Relatorio(p.equipe.nome, COUNT(p)) FROM Profissional p WHERE p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE) GROUP BY p.equipe.nome";
		Query q = manager.createQuery(profisionalPorEquipe);
		List<Relatorio> relatorioProfissionalPorEquipe = (List<Relatorio>)q.getResultList();
		manager.close();
		return relatorioProfissionalPorEquipe;		
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> contratacaoPorEquipe(){
		EntityManager manager = JPAUtil.getEntityManager();
		String contratacaoPorEquipe = "SELECT new com.stefanini.entidade.Relatorio (v.nome,v.clt, v.est) "
				+ "FROM ViewCltXEstagio v";
			
		 
		Query q = manager.createQuery(contratacaoPorEquipe);
		List<Relatorio> relatorioContratacaoPorEquipe = (List<Relatorio>)q.getResultList();
		manager.close();

		return relatorioContratacaoPorEquipe;
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> contratacaoPorCelula(){
		EntityManager manager = JPAUtil.getEntityManager();
		String contratacaoPorCelula = "SELECT new com.stefanini.entidade.Relatorio (v.nome,v.clt, v.est) "
				+ "FROM ViewCltXEstagioCelula v";
			
		 
		Query q = manager.createQuery(contratacaoPorCelula);
		List<Relatorio> relatorioContratacaoPorCelula = (List<Relatorio>)q.getResultList();
		manager.close();

		return relatorioContratacaoPorCelula;
	}

	@SuppressWarnings("unchecked")
	public List<Relatorio> valorPorEquipe(){
		EntityManager manager = JPAUtil.getEntityManager();
		String valorPorEquipe = "SELECT new com.stefanini.entidade.Relatorio(p.equipe.nome, AVG(p.valorHora)) FROM Profissional p WHERE p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE) GROUP BY p.equipe.nome";
		Query q = manager.createQuery(valorPorEquipe);
		List<Relatorio> relatorioValorPorEquipe = (List<Relatorio>)q.getResultList();
		manager.close();
		return relatorioValorPorEquipe;
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> valorPorCelula(){
		EntityManager manager = JPAUtil.getEntityManager();
		String valorPorCelula = "SELECT new com.stefanini.entidade.Relatorio(p.celula.nome, AVG(p.valorHora)) FROM Profissional p WHERE p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE) GROUP BY p.celula.nome";
		Query q = manager.createQuery(valorPorCelula);
		List<Relatorio> relatorioValorPorCelula = (List<Relatorio>)q.getResultList();
		manager.close();
		return relatorioValorPorCelula;
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> perfilPorEquipe(){
		EntityManager manager = JPAUtil.getEntityManager();
		String perfilPorEquipe = "SELECT new com.stefanini.entidade.Relatorio(v.nome,v.junior,v.pleno,v.senior)FROM ViewPerfilXEquipe v";
		Query q = manager.createQuery(perfilPorEquipe);
		List<Relatorio> relatorioValorPorEquipe = (List<Relatorio>)q.getResultList();
		manager.close();
		return relatorioValorPorEquipe;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> perfilPorCelula(){
		EntityManager manager = JPAUtil.getEntityManager();
		String perfilPorCelula = "SELECT new com.stefanini.entidade.Relatorio(v.nome,v.junior,v.pleno,v.senior)FROM ViewPerfilXCelula v";
		Query q = manager.createQuery(perfilPorCelula);
		List<Relatorio> relatorioPerfilPorCelula = (List<Relatorio>)q.getResultList();
		manager.close();
		return relatorioPerfilPorCelula;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> listaDeProfissionaisPorEquipe(String nome){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByEquipeNome");
		q.setParameter("nome", nome);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> listaDeProfissionaisPorCelula(String nome){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByCelulaNome");
		q.setParameter("nome", nome);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> listaDeCLTXEstagio(String nome, int serie){
		String serieString;
		if(serie ==0){
			 serieString = "CLT";
		}else{
			serieString = "Estágio";
		}
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByEquipeEContratacao");
		q.setParameter("nome", nome);
		q.setParameter("serie", serieString);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> listaDeCLTXEstagioCelula(String nome, int serie){
		String serieString;
		if(serie ==0){
			 serieString = "CLT";
		}else{
			serieString = "Estágio";
		}
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByCelulaEContratacao");
		q.setParameter("nome", nome);
		q.setParameter("serie", serieString);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	@SuppressWarnings("unchecked")
	public List<Profissional> listaDePerfilPorEquipe(String nome, int serie){
		String serieString;
		if(serie == 0){
			 serieString = "Júnior";
		}else if(serie == 1){
			serieString = "Pleno";
		}else{
			serieString = "Sênior";
		}
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByEquipeEPerfil");
		q.setParameter("nome", nome);
		q.setParameter("serie", serieString);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> listaDePerfilPorCelula(String nome, int serie){
		String serieString;
		String queryString;
		if(serie == 0){
			 serieString = "Júnior";
			 queryString= "Profissional.findProfissionalByCelulaEPerfil";
		}else if(serie == 1){
			serieString = "Pleno";
			queryString= "Profissional.findProfissionalByCelulaEPerfil";
		}else if (serie ==2){
			serieString = "Sênior";
			queryString= "Profissional.findProfissionalByCelulaEPerfil";
		}else{
			serieString ="";
			queryString ="Profissional.findProfissionalByCelulaNome";
		}
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery(queryString);
		if(serie == 0||serie==1||serie==2){
		q.setParameter("nome", nome);
		q.setParameter("serie", serieString);
		}else{
			q.setParameter("nome", nome);
		}
		
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	
}
