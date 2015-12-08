package com.stefanini.service;

import javax.persistence.EntityManager;

import com.stefanini.entidade.Equipe;
import com.stefanini.util.JPAUtil;

public class EquipeService {

	public void save(Equipe equipe){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(equipe);
		manager.getTransaction().commit();
		manager.close();
	}
}
