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
		String profisionalPorEquipe = "SELECT new com.stefanini.entidade.Relatorio(p.equipe.nome, COUNT(p)) FROM Profissional p WHERE p.registroValidadeInicio <= CURRENT_DATE AND p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE GROUP BY p.equipe.nome";
		Query q = manager.createQuery(profisionalPorEquipe);
//		Query q = manager.createNamedQuery("Relatorio.profissionalPorEquipe");
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
	public List<Profissional> listaDeProfissionaisPorEquipe(String nome){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByEquipeNome");
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
	public List<Relatorio> valorPorEquipe(){
		EntityManager manager = JPAUtil.getEntityManager();
		String valorPorEquipe = "SELECT new com.stefanini.entidade.Relatorio(p.equipe.nome, AVG(p.valorHora)) FROM Profissional p WHERE p.registroValidadeInicio <= CURRENT_DATE AND p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE GROUP BY p.equipe.nome";
		Query q = manager.createQuery(valorPorEquipe);
		List<Relatorio> relatorioValorPorEquipe = (List<Relatorio>)q.getResultList();
		manager.close();
		return relatorioValorPorEquipe;
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> perfilPorEquipe(){
		EntityManager manager = JPAUtil.getEntityManager();
		String valorPorEquipe = "SELECT new com.stefanini.entidade.Relatorio(v.nome,v.junior,v.pleno,v.senior)FROM ViewPerfilXEquipe v";
		Query q = manager.createQuery(valorPorEquipe);
		List<Relatorio> relatorioValorPorEquipe = (List<Relatorio>)q.getResultList();
		manager.close();
		return relatorioValorPorEquipe;
		
	}
}
