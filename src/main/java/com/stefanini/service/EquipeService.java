package com.stefanini.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Equipe;
import com.stefanini.util.JPAUtil;

public class EquipeService {

	public void save(Equipe equipe) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(equipe);
		manager.getTransaction().commit();
		manager.close();
	}

	public void update(Equipe equipe) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		Equipe equipeEdite = getEquipeById(equipe.getId());
		
		
		Equipe equipeNovo = new Equipe();
		equipeNovo.setNome(equipe.getNome());
		equipeNovo.setRegistroValidadeInicio(equipe.getRegistroValidadeInicio());
		equipeEdite.setRegistroValidadeFim(equipe.getRegistroValidadeFim());
		
		manager.merge(equipeEdite);
		manager.persist(equipeNovo);
		manager.getTransaction().commit();
		manager.close();
	
	}

	@SuppressWarnings("unchecked")
	public List<Equipe> listar() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_equipe WHERE REGISTRO_VALIDADE_FIM IS NULL ORDER BY REGISTRO_VALIDADE_INICIO ASC", Equipe.class);
		List<Equipe> equipes = q.getResultList();
		manager.close();
		return equipes;
	}

	public Equipe getEquipeById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_equipe WHERE ID_EQUIPE = :idEquipe", Equipe.class);
		q.setParameter("idEquipe", id);
		Equipe equipe = (Equipe) q.getSingleResult();
		manager.close();
		return equipe;
	}

	public void desativar(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();

		Equipe equipeMerge = getEquipeById(id);
		manager.getTransaction().begin();
		equipeMerge.setRegistroValidadeFim(equipeMerge.getDataManipulacaoFim());
		manager.merge(equipeMerge);
		manager.getTransaction().commit();
		manager.close();
	}
}
