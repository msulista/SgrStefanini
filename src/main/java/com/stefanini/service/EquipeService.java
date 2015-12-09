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
		Query query = manager.createQuery("UPDATE SGR_EQUIPE SET REGISTRO_VALIDADE_FIM = :dataFim WHERE ID_EQUIPE = :idEquipe");
		query.setParameter("idEquipe", equipe.getIdEquipe());
		query.setParameter("dataFim", equipe.getRegistroValidadeFim());
		Equipe novaEquipe = new Equipe();
		novaEquipe.setNome(equipe.getNome());
		novaEquipe.setRegistroValidadeInicio(equipe.getRegistroValidadeFim());
		save(novaEquipe);
		manager.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Equipe> listar(){
		EntityManager manager = JPAUtil.getEntityManager();
		Query query = manager.createNativeQuery("SELECT * FROM SGR_EQUIPE WHERE REGISTRO_VALIDADE_INICIO = null", Equipe.class);
		List<Equipe> equipe = (List<Equipe>) query.getSingleResult();
		manager.close();
		return equipe;
	}
}
