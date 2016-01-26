package com.stefanini.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Alocacao;
import com.stefanini.entidade.Projeto;
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
}
