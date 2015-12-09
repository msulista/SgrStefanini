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
		
		Query query = manager.createNativeQuery("UPDATE SGR_EQUIPE SET REGISTRO_VALIDADE_FIM = :dataFim WHERE ID_EQUIPE = :id");
		query.setParameter("dataFim", equipe.getRegistroValidadeFim());
		query.setParameter("idEquipe", equipe.getIdEquipe());
		query.executeUpdate();
		
		manager.getTransaction().commit();
		manager.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Equipe> listarAtivos(){
		EntityManager manager = JPAUtil.getEntityManager();
		Query query = manager.createNativeQuery("SELECT * FROM SGR_EQUIPE WHERE REGISTRO_VALIDADE_INICIO = null", Equipe.class);
		List<Equipe> equipes = query.getResultList();
		manager.close();
		return equipes;
	}
	
	public Equipe getEquipeById(long id){
		EntityManager manager = JPAUtil.getEntityManager();
		Query query = manager.createNativeQuery("SELECT * FROM SGR_EQUIPE WHERE ID_EQUIPE = :idEquipe", Equipe.class);
		query.setParameter("idEquipe", id);
		Equipe equipe = (Equipe) query.getSingleResult();
		manager.close();
		return equipe;
	}
	
	public void Desativar(long id){
		EntityManager manager = JPAUtil.getEntityManager();
		Equipe equipe = getEquipeById(id);
		equipe.setRegistroValidadeFim(new Date());
		manager.getTransaction().begin();
		
		Query query = manager.createQuery("UPDATE SGR_EQUIPE SET REGISTRO_VALIDADE_FIM = :dataFim WHERE ID_EQUIPE = :id");
		query.setParameter("dataFim", equipe.getRegistroValidadeFim());
		query.setParameter("id", equipe.getIdEquipe());
		query.executeUpdate();
		manager.getTransaction().commit();
		manager.close();
		
	}
}
