package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.FormaContratacao;
import com.stefanini.util.JPAUtil;

public class FormaContratacaoService {

	public void save(FormaContratacao formaContratacao){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(formaContratacao);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public void update(FormaContratacao formaContratacao){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();	
		
		FormaContratacao formaContratacaoMerge = (FormaContratacao)getFormaContratacaoById(formaContratacao.getId());
		formaContratacaoMerge.setRegistroValidadeFim(formaContratacao.getDataManipulacao());
		manager.merge(formaContratacaoMerge);
		manager.getTransaction().commit();
		manager.close();
		
		FormaContratacao formaContratacaoPersist = new FormaContratacao();
		formaContratacaoPersist.setNome(formaContratacao.getNome());
		formaContratacaoPersist.setRegistroValidadeInicio(formaContratacao.getDataManipulacao());		
		save(formaContratacaoPersist);
	}
	
	@SuppressWarnings("unchecked")
	public List<FormaContratacao> listarAtivos(){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_forma_contratacao WHERE REGISTRO_VALIDADE_FIM IS NULL ORDER BY REGISTRO_VALIDADE_INICIO ASC", FormaContratacao.class);
		List<FormaContratacao> formaContratacoes = q.getResultList();
		return formaContratacoes;
	}
	
	public FormaContratacao getFormaContratacaoById(Long id){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_forma_contratacao WHERE ID_FORMA_CONTRATACAO = :idFormaContratacao", FormaContratacao.class);
		q.setParameter("idFormaContratacao", id);
		FormaContratacao formaContratacao = (FormaContratacao) q.getSingleResult();
		manager.close();
		return formaContratacao;
	}

	public void desativar(Long id) throws ConverterException{
		EntityManager manager = JPAUtil.getEntityManager();	
		manager.getTransaction().begin();	
		FormaContratacao formaContratacao = (FormaContratacao)getFormaContratacaoById(id);
		formaContratacao.setRegistroValidadeFim(new Date());
		manager.merge(formaContratacao);
		manager.getTransaction().commit();
		manager.close();		
	}
}
