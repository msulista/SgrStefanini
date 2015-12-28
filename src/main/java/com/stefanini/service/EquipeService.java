package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Equipe;
import com.stefanini.util.DateUtil;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class EquipeService {

	public boolean save(Equipe equipe) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		if (DateUtil.verificaDiaUtil(equipe.getRegistroValidadeInicio())&&DateUtil.verificaDiaUtil(equipe.getRegistroValidadeFim())) {
			if(DateUtil.verificaDataValida(equipe.getRegistroValidadeInicio(), equipe.getRegistroValidadeFim())){
			manager.persist(equipe);
			manager.getTransaction().commit();
			manager.close();
			return true;
			}else{
				Mensagem.add("Data final do registro anterior a data inicial!");
				manager.close();
				return false;
			}
		}else{
		Mensagem.add("Data informada não é um dia util!");
		manager.close();
		return false;
		}
	}
			

	public boolean update(Equipe equipe) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		if (DateUtil.verificaDiaUtil(equipe.getRegistroValidadeInicio())) {
			Equipe equipeMerge = getEquipeById(equipe.getId());
				if(DateUtil.verificaDataValida(equipe.getRegistroValidadeInicio(), equipe.getRegistroValidadeFim())){
			equipeMerge.setRegistroValidadeFim(new Date());
			manager.merge(equipeMerge);
			manager.getTransaction().commit();
			manager.close();

			Equipe equipePersist = new Equipe();
			equipePersist.setNome(equipe.getNome());
			equipePersist.setRegistroValidadeInicio(equipe.getRegistroValidadeInicio());
			equipePersist.setRegistroValidadeFim(equipe.getRegistroValidadeFim());
			save(equipePersist);
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
	public List<Equipe> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Equipe.findAtivos");
		List<Equipe> equipes = q.getResultList();
		manager.close();
		return equipes;
	}

	public Equipe getEquipeById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Equipe.findId");
		q.setParameter("id", id);
		Equipe equipe = (Equipe) q.getSingleResult();
		manager.close();
		return equipe;
	}

	public void desativar(Long id) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByCargo");
		q.setParameter("id", id);
		if(q.getResultList().isEmpty()){
			Equipe equipeMerge = getEquipeById(id);
			manager.getTransaction().begin();
			equipeMerge.setRegistroValidadeFim(new Date());
			manager.merge(equipeMerge);
			manager.getTransaction().commit();
			manager.close();
		}else {
			Mensagem.add("Existem profissionais ativos vinculados a esta Equipe, não pode ser excluída.");
			manager.close();
		}
	}
}
