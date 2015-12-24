package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.FormaContratacao;
import com.stefanini.util.DateUtil;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class FormaContratacaoService {

	public boolean save(FormaContratacao formaContratacao) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		if (DateUtil.verificaDiaUtil(formaContratacao.getRegistroValidadeInicio())) {
			manager.persist(formaContratacao);
			manager.getTransaction().commit();
			manager.close();
			return true;
		} else {
			Mensagem.add("Data informada não é um dia util!");
			manager.close();
			return false;
		}
	}

	public boolean update(FormaContratacao formaContratacao) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		if (DateUtil.verificaDiaUtil(formaContratacao.getRegistroValidadeInicio())) {
			FormaContratacao formaContratacaoMerge = (FormaContratacao) getFormaContratacaoById(
					formaContratacao.getId());
				if(DateUtil.verificaDataValida(formaContratacao.getRegistroValidadeInicio(), formaContratacao.getRegistroValidadeFim())){
				formaContratacaoMerge.setRegistroValidadeFim(new Date());
				manager.merge(formaContratacaoMerge);
				manager.getTransaction().commit();
				manager.close();

				FormaContratacao formaContratacaoPersist = new FormaContratacao();
				formaContratacaoPersist.setNome(formaContratacao.getNome());
				formaContratacaoPersist.setRegistroValidadeInicio(formaContratacao.getRegistroValidadeInicio());
				formaContratacaoPersist.setRegistroValidadeFim(formaContratacao.getRegistroValidadeFim());
				save(formaContratacaoPersist);
				return true;
				}else{
					Mensagem.add("Erro, data final menor que a inicial!");
					manager.close();
					return false;
				}
		} else {
			Mensagem.add("Data informada não é um dia util!");
			manager.close();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<FormaContratacao> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("FormaContratacao.findAtivos");
		List<FormaContratacao> formaContratacoes = q.getResultList();
		return formaContratacoes;
	}

	public FormaContratacao getFormaContratacaoById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("FormaContratacao.findId");
		q.setParameter("idFormaContratacao", id);
		FormaContratacao formaContratacao = (FormaContratacao) q.getSingleResult();
		manager.close();
		return formaContratacao;
	}

	public void desativar(Long id) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		FormaContratacao formaContratacao = (FormaContratacao) getFormaContratacaoById(id);
		formaContratacao.setRegistroValidadeFim(new Date());
		manager.merge(formaContratacao);
		manager.getTransaction().commit();
		manager.close();
	}
}
