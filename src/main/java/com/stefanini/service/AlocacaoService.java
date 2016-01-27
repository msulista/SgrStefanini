package com.stefanini.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.stefanini.entidade.Alocacao;
import com.stefanini.entidade.Celula;
import com.stefanini.entidade.Equipe;
import com.stefanini.entidade.Recurso;
import com.stefanini.util.DateUtil;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class AlocacaoService {

	@SuppressWarnings("unchecked")
	public  boolean save(Alocacao alocacao){
		EntityManager manager = JPAUtil.getEntityManager();
		if (DateUtil.verificaDiaUtil(alocacao.getDataInicio())&&DateUtil.verificaDiaUtil(alocacao.getDataFim())) {	
			Query q = manager.createNamedQuery("Alocacao.findRecursoAtivosDoProjeto");
			q.setParameter("codigo", alocacao.getProjeto().getCodigo());
			q.setParameter("matricula", alocacao.getRecurso().getProfissional().getMatricula());
			List<Alocacao> alocacoes = q.getResultList();
			if(alocacoes.isEmpty()){
				manager.getTransaction().begin();
				manager.persist(alocacao);
				manager.getTransaction().commit();
				manager.close();
				return true;
			}else{
				Mensagem.add("Profissional já está neste Projeto!");
				manager.close();
				return false;
			}
		} else {
			Mensagem.add("Data informada não é um dia util!");
			manager.close();
			return false;
		}
	}
	
	public boolean update(Alocacao alocacao){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.merge(alocacao);
		manager.getTransaction().commit();
		manager.close();
		return true;		
	}
	
	public Alocacao getAlocacaoById(Long id){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Alocacao.findPorId");
		q.setParameter("id", id);
		Alocacao alocacao = (Alocacao) q.getSingleResult();
		manager.close();
		return alocacao;
	}
	
	public void desativar(Long id) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		Alocacao alocacao =  getAlocacaoById(id);
		alocacao.setRegistroValidadeFim(new Date());
		manager.getTransaction().begin();
 		manager.merge(alocacao);
		manager.getTransaction().commit();
		manager.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Alocacao> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Alocacao.findAtivos");
		List<Alocacao> alocacoes = q.getResultList();
		manager.close();
		return alocacoes;
	}
	
	@SuppressWarnings("unchecked")
	public List<Recurso> listarTodosRecursos(Equipe equipe, Celula celula) {
		EntityManager manager = JPAUtil.getEntityManager();
		if(equipe != null&&celula ==null){
			Query q = manager.createNamedQuery("Recurso.findByEquipe");
			q.setParameter("id", equipe.getId());
			List<Recurso> recursos = q.getResultList();
			manager.close();
			return recursos;
		}else if(equipe == null && celula != null){
			Query q = manager.createNamedQuery("Recurso.findByCelula");
			q.setParameter("id", celula.getId());
			List<Recurso> recursos = q.getResultList();
			manager.close();
			return recursos;
		}else if(equipe != null && celula != null){
		Query q = manager.createNamedQuery("Recurso.findByEquipeECelula");
		q.setParameter("id", equipe.getId());
		q.setParameter("celula", celula.getId());
		List<Recurso> recursos = q.getResultList();
		manager.close();
		return recursos;
		}else{
			List<Recurso> recursos = new ArrayList<>();
			return recursos;
		}
	}
	/*
	@SuppressWarnings("unchecked")
	public List<Alocacao> listarTodosAlocados(Equipe equipe, Celula celula) {
		EntityManager manager = JPAUtil.getEntityManager();
		if(equipe != null&&celula ==null){
			Query q = manager.createNamedQuery("Alocacao.findProjetoPorEquipe");
			q.setParameter("id", equipe.getId());
			List<Alocacao> alocacoes = q.getResultList();
			manager.close();
			return alocacoes;
		}else if(equipe == null && celula != null){
			Query q = manager.createNamedQuery("Alocacao.findProjetoPorCelula");
			q.setParameter("id", celula.getId());
			List<Alocacao> alocacoes = q.getResultList();
			manager.close();
			return alocacoes;
		}else if(equipe != null && celula != null){
		Query q = manager.createNamedQuery("Alocacao.findProjetoPorCelulaEEquipe");
		q.setParameter("idCelula", celula.getId());
		q.setParameter("idEquipe", equipe.getId());
		List<Alocacao> alocacoes = q.getResultList();
		manager.close();
		return alocacoes;
		}else{
			List<Alocacao> alocacoes = new ArrayList<>();
			return alocacoes;
		}
	}*/
}
