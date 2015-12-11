package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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

	@SuppressWarnings("unchecked")
	public List<Perfil> listarAtivos(){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_perfil WHERE REGISTRO_VALIDADE_FIM IS NULL ORDER BY REGISTRO_VALIDADE_INICIO ASC", Perfil.class);
		List<Perfil> perfis = q.getResultList();
		return perfis;
	}
	
	public Perfil getPerfilById(Long id){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_perfil WHERE ID_PERFIL = :idPerfil",Perfil.class);
		q.setParameter("idPerfil", id);
		Perfil perfil = (Perfil)q.getSingleResult();
		manager.close();
		return perfil;
	}
	
	public void desativar(Long id)throws ConverterException{
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		Perfil perfilMerge = getPerfilById(id);
		perfilMerge.setRegistroValidadeFim(new Date());
		manager.merge(perfilMerge);
		manager.getTransaction().commit();
		manager.close();
	}
}
