package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Celula;
import com.stefanini.util.JPAUtil;

public class CelulaService {

	public void save(Celula celula){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(celula);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public void update(Celula celula){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		Celula celulaMerge = getCelulaById(celula.getId());
		celulaMerge.setRegistroValidadeFim(celula.getDataManipulacao());
		manager.merge(celulaMerge);
		manager.getTransaction().commit();
		manager.close();
		
		Celula celulaPersist = new Celula();
		celulaPersist.setNome(celula.getNome());
		celulaPersist.setRegistroValidadeInicio(celula.getDataManipulacao());
		save(celulaPersist);
	}
	
	@SuppressWarnings("unchecked")
	public List<Celula> listarAtivo(){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_celula WHERE REGISTRO_VALIDADE_FIM IS NULL ORDER BY REGISTRO_VALIDADE_INICIO ASC", Celula.class);
		List<Celula> celulas = q.getResultList();
		manager.close();
		return celulas;
	}
	
	public Celula getCelulaById(Long id){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_celula WHERE ID_CELULA = :idCelula",Celula.class);
		q.setParameter("idCelula", id);
		Celula celula = (Celula) q.getSingleResult();
		manager.close();
		return celula;
	}
	
	public void desativar(Long id){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		Celula celulaMerge = getCelulaById(id);
		celulaMerge.setRegistroValidadeFim(new Date());
		manager.merge(celulaMerge);
		manager.getTransaction().commit();
		manager.close();
	}
}
