package com.stefanini.service;

import javax.persistence.EntityManager;

import com.stefanini.entidade.Perfil;
import com.stefanini.util.JPAUtil;

public class PerfilService {

	public void save(Perfil perfil){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(perfil);
		manager.getTransaction().commit();
		manager.close();
		
	}
	
	public void update(Perfil perfil){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();	
		
		Perfil perfilMerge = getPerfilById(perfil.getId());
		perfilMerge.setRegistroValidadeFim(perfil.getDataManipulacao());
		manager.merge(perfilMerge);
		manager.getTransaction().commit();
		manager.close();
		
		Perfil perfilPersist = new Perfil();
		perfilPersist.setNome(perfil.getNome());
		perfilPersist.setRegistroValidadeInicio(perfil.getDataManipulacao());
		save(perfilPersist);
	}
	
	private Perfil getPerfilById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	//public List<Perfil> listarAtivos(){
	//	EntityManager
	//}
}
