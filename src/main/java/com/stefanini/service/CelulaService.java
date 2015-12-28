package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Celula;
import com.stefanini.util.DateUtil;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class CelulaService {

	public boolean save(Celula celula) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		if (DateUtil.verificaDiaUtil(celula.getRegistroValidadeInicio())&&DateUtil.verificaDiaUtil(celula.getRegistroValidadeFim())) {
			if(DateUtil.verificaDataValida(celula.getRegistroValidadeInicio(), celula.getRegistroValidadeFim())){
			manager.persist(celula);
			manager.getTransaction().commit();
			manager.close();
			return true;
			}else{
				Mensagem.add("Data final do registro anterior a data inicial!");
				manager.close();
				return false;
			}
		} else {
			Mensagem.add("Data informada n�o � um dia util!");
			manager.close();
			return false;
		}

	}

	public boolean update(Celula celula) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		if (DateUtil.verificaDiaUtil(celula.getRegistroValidadeInicio())) {
			Celula celulaMerge = getCelulaById(celula.getId());
				if(DateUtil.verificaDataValida(celula.getRegistroValidadeInicio(), celula.getRegistroValidadeFim())){
				celulaMerge.setRegistroValidadeFim(new Date());
				manager.merge(celulaMerge);
				manager.getTransaction().commit();
				manager.close();

				Celula celulaPersist = new Celula();
				celulaPersist.setNome(celula.getNome());
				celulaPersist.setRegistroValidadeInicio(celula.getRegistroValidadeInicio());
				celulaPersist.setRegistroValidadeFim(celula.getRegistroValidadeFim());
				save(celulaPersist);
				return true;
				}else{
					Mensagem.add("Erro, data final menor que a inicial!");
					manager.close();
					return false;
				}
		} else {
			Mensagem.add("Data informada n�o � um dia util!");
			manager.close();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Celula> listarAtivo() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Celula.findAtivos");
		List<Celula> celulas = q.getResultList();
		manager.close();
		return celulas;
	}

	public Celula getCelulaById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Celula.findId");
		q.setParameter("id", id);
		Celula celula = (Celula) q.getSingleResult();
		manager.close();
		return celula;
	}

	public void desativar(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByCelula");
		q.setParameter("id", id);
		if(q.getResultList().isEmpty()){
			Celula celulaMerge = getCelulaById(id);
			celulaMerge.setRegistroValidadeFim(new Date());
			manager.getTransaction().begin();
			manager.merge(celulaMerge);
			manager.getTransaction().commit();
			manager.close();
		}else {
			Mensagem.add("Existem profissionais ativos vinculados a este Cargo, n�o pode ser exclu�da.");
			manager.close();
		}
	}
}
