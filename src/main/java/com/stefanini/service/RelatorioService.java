package com.stefanini.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Celula;
import com.stefanini.entidade.Profissional;
import com.stefanini.entidade.Relatorio;
import com.stefanini.util.JPAUtil;

public class RelatorioService {
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> profissionaisPorEquipe(Celula celula){
		
		
		
		
		
		if(celula!=null){
		EntityManager manager = JPAUtil.getEntityManager();
		String profisionalPorEquipe = "SELECT new com.stefanini.entidade.Relatorio(p.equipe.nome, COUNT(p)) FROM Profissional p WHERE p.celula.id =:celula AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) GROUP BY p.equipe.nome";
		Query q = manager.createQuery(profisionalPorEquipe);
		q.setParameter("celula", celula.getId());
		List<Relatorio> relatorioProfissionalPorEquipe = (List<Relatorio>)q.getResultList();
		manager.close();
		return relatorioProfissionalPorEquipe;
		
		}else{
			
			List<Relatorio> relatorioProfissionalPorEquipe = new ArrayList<>();
			return relatorioProfissionalPorEquipe;
		}
	
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> contratacaoPorEquipe(Celula celula){
		
		if(celula!=null){
		EntityManager manager = JPAUtil.getEntityManager();
		String contratacaoPorEquipe = "SELECT new com.stefanini.entidade.Relatorio (v.nome,v.clt, v.est) "
				+ "FROM ViewCltXEstagio v WHERE v.id =:id";
			
		Query q = manager.createQuery(contratacaoPorEquipe);
		q.setParameter("id", celula.getId());
		List<Relatorio> relatorioContratacaoPorEquipe = (List<Relatorio>)q.getResultList();
		manager.close();

		return relatorioContratacaoPorEquipe;
		}else{
			List<Relatorio> relatorioContratacaoPorEquipe = new ArrayList<>();
			return relatorioContratacaoPorEquipe;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> contratacaoPorCelula(Celula celula){
		
		if(celula != null){
		EntityManager manager = JPAUtil.getEntityManager();
		String contratacaoPorCelula = "SELECT new com.stefanini.entidade.Relatorio (v.nome,v.clt, v.est) "
				+ "FROM ViewCltXEstagioCelula v WHERE v.id = :id";
			
		 
		Query q = manager.createQuery(contratacaoPorCelula);
		q.setParameter("id", celula.getId());
		List<Relatorio> relatorioContratacaoPorCelula = (List<Relatorio>)q.getResultList();
		manager.close();

		return relatorioContratacaoPorCelula;
		}else{
			List<Relatorio> relatorioContratacaoPorCelula = new ArrayList<>();
			return relatorioContratacaoPorCelula;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Relatorio> valorPorEquipe(Celula celula){
		
		if(celula!=null){
		EntityManager manager = JPAUtil.getEntityManager();
		String valorPorEquipe = "SELECT new com.stefanini.entidade.Relatorio(p.equipe.nome, AVG(p.valorHora)) FROM Profissional p WHERE p.celula.id = :id AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) GROUP BY p.equipe.nome";
		Query q = manager.createQuery(valorPorEquipe);
		q.setParameter("id", celula.getId());
		List<Relatorio> relatorioValorPorEquipe = (List<Relatorio>)q.getResultList();
		manager.close();
		
		return relatorioValorPorEquipe;
	}else{
		List<Relatorio> relatorioValorPorEquipe = new ArrayList<>();
		
		return relatorioValorPorEquipe;
	}
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> valorPorCelula(Celula celula){
		if(celula != null){
		EntityManager manager = JPAUtil.getEntityManager();
		String valorPorCelula = "SELECT new com.stefanini.entidade.Relatorio(p.celula.nome, AVG(p.valorHora)) FROM Profissional p WHERE p.celula.id = :id AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE)";
		Query q = manager.createQuery(valorPorCelula);
		q.setParameter("id", celula.getId());
		List<Relatorio> relatorioValorPorCelula = (List<Relatorio>)q.getResultList();
		manager.close();
		return relatorioValorPorCelula;
		}else{
			List<Relatorio> relatorioValorPorCelula = new ArrayList<>();
			return relatorioValorPorCelula;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> perfilPorEquipe(Celula celula){
		
		if(celula !=null){
		EntityManager manager = JPAUtil.getEntityManager();
		String perfilPorEquipe = "SELECT new com.stefanini.entidade.Relatorio(v.nome,v.estagio,v.junior,v.pleno,v.senior)FROM ViewPerfilXEquipe v WHERE v.id = :id";
		Query q = manager.createQuery(perfilPorEquipe);
		q.setParameter("id", celula.getId());
		List<Relatorio> relatorioValorPorEquipe = (List<Relatorio>)q.getResultList();
		manager.close();
		return relatorioValorPorEquipe;
	}else{
		List<Relatorio> relatorioValorPorEquipe = new ArrayList<>();
		return relatorioValorPorEquipe;
	}
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> perfilPorCelula(Celula celula){
		if(celula != null){
		EntityManager manager = JPAUtil.getEntityManager();
		String perfilPorCelula = "SELECT new com.stefanini.entidade.Relatorio(v.nome,v.estagio,v.junior,v.pleno,v.senior)FROM ViewPerfilXCelula v WHERE v.id = :id";
		Query q = manager.createQuery(perfilPorCelula);
		q.setParameter("id", celula.getId());
		List<Relatorio> relatorioPerfilPorCelula = (List<Relatorio>)q.getResultList();
		manager.close();
		return relatorioPerfilPorCelula;
		}else{
			List<Relatorio> relatorioPerfilPorCelula = new ArrayList<>();
			
			return relatorioPerfilPorCelula;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> listaDeProfissionaisPorEquipe(Long celula, String equipe){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByCelulaANDEquipe");
		q.setParameter("celula", celula);
		q.setParameter("equipe", equipe);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> listaDeProfissionaisPorCelula(Long id,String nome){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByCelulaNome");
		q.setParameter("nome", nome);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> listaDeCLTXEstagio(Long celula, String nome, int serie){
		String serieString;
		if(serie ==0){
			 serieString = "CLT";
		}else{
			serieString = "Estágio";
		}
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByCelulaANDEquipeEContratacao");
		q.setParameter("nome", nome);
		q.setParameter("serie", serieString);
		q.setParameter("celula", celula);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> listaDeCLTXEstagioCelula(Long celula,int serie){
		String serieString = null;
		String query;
		if(serie ==0){
			 serieString = "CLT";
			 query = "Profissional.findProfissionalByCelulaEContratacao";
		}else if(serie ==1){
			serieString = "Estágio";
			query = "Profissional.findProfissionalByCelulaEContratacao";
		}else{
			query = "Profissional.findProfissionalByCelula";
		}
		EntityManager manager = JPAUtil.getEntityManager();
		
		if(serie==0||serie==1){
		Query q = manager.createNamedQuery(query);
		q.setParameter("celula", celula);
	
		q.setParameter("serie", serieString);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
		}else{
			Query q = manager.createNamedQuery(query);
			q.setParameter("id", celula);
			List<Profissional> profissionais = q.getResultList();
			manager.close();
			return profissionais;
		}
	}
	@SuppressWarnings("unchecked")
	public List<Profissional> listaDePerfilPorEquipe(Long id,String nome, int serie){

		String serieString;
		if(serie == 0){
			 serieString = "Estágio";
		}else if(serie == 1){
			serieString = "Júnior";
		}else if(serie ==2){
			serieString = "Pleno";
		}else if(serie ==3){
			serieString = "Sênior";
		}else{
			serieString = "";
		}
		EntityManager manager = JPAUtil.getEntityManager();
		System.out.println("#####################################################"
				+ "#############################################################"
				+ "#############################################################"+"    "+nome);
		if(!nome.equals("Total Resultados")){
		
		Query q = manager.createNamedQuery("Profissional.findProfissionalByCelulaANDEquipeEPerfil");
		q.setParameter("nome", nome);
		q.setParameter("serie", serieString);
		q.setParameter("id", id);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
		}else{
			Query q = manager.createNamedQuery("Profissional.findProfissionalByCelulaEPerfil");
			q.setParameter("celula", id);
			q.setParameter("serie", serieString);
			List<Profissional> profissionais = q.getResultList();
			manager.close();
			return profissionais;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> listaDePerfilPorCelula(Long celula, int serie){
		String serieString = "";
		String queryString ="";
		if(serie == 0){
			 serieString = "Estágio";
			 queryString= "Profissional.findProfissionalByCelulaEPerfil";
		}else if(serie == 1){
			serieString = "Júnior";
			queryString= "Profissional.findProfissionalByCelulaEPerfil";
		}else if (serie ==2){
			serieString = "Pleno";
			queryString= "Profissional.findProfissionalByCelulaEPerfil";
		}else if(serie ==3){
			serieString ="Sênior";
			queryString= "Profissional.findProfissionalByCelulaEPerfil";
		}else{
			serieString="";
			queryString = "Profissional.findProfissionalByCelula";
		}
		EntityManager manager = JPAUtil.getEntityManager();
		if(serie==0||serie==1||serie==2||serie==3){
		
		Query q = manager.createNamedQuery(queryString);
		q.setParameter("celula", celula);
		q.setParameter("serie", serieString);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
		}else{
			Query q = manager.createNamedQuery(queryString);
			q.setParameter("id", celula);
			List<Profissional> profissionais = q.getResultList();
			manager.close();
			return profissionais;
		}
	}
}
