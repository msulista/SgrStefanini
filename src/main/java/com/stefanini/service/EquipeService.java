package com.stefanini.service;

import java.util.Date;
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

		equipe.setRegistroValidadeFim(new Date());
		Equipe novaEquipe = new Equipe();
		novaEquipe.setNome(equipe.getNome());
		novaEquipe.setRegistroValidadeInicio(equipe.getRegistroValidadeFim());
		manager.persist(novaEquipe);

		Query q = manager.createNativeQuery(
				"UPDATE sgr_equipe SET REGISTRO_VALIDADE_FIM = :dataFim WHERE ID_EQUIPE = :id", Equipe.class);
		q.setParameter("dataFim", equipe.getRegistroValidadeFim());
		q.setParameter("idEquipe", equipe.getId());
		q.executeUpdate();
		manager.close();
	}

	@SuppressWarnings("unchecked")
	public List<Equipe> listar() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_equipe WHERE REGISTRO_VALIDADE_FIM IS NULL", Equipe.class);
		List<Equipe> equipes = q.getResultList();
		return equipes;
	}

	public Equipe getEquipeById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_equipe WHERE ID_EQUIPE = :idEquipe");
		q.setParameter("idEquipe", id);
		Equipe equipe = (Equipe) q.getSingleResult();
		manager.close();
		return equipe;
	}

	public void desativar(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();

		Query q = manager.createNativeQuery("UPDATE sgr_equipe SET REGISTRO_VALIDADE_FIM = :dataFim WHERE ID_EQUIPE = :id");
		q.setParameter("dataFim", new Date());
		q.setParameter("id",id);
		q.executeUpdate();
		manager.close();
	}
}
