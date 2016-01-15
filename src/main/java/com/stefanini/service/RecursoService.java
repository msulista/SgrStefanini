package com.stefanini.service;

import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Recurso;
import com.stefanini.util.JPAUtil;

public class RecursoService {

//	public boolean save (Recurso recurso){
//		EntityManager manager = JPAUtil.getEntityManager();
//		Query q = manager.createNamedQuery("Recurso.findMatricula");
//		q.setParameter("matricula", recurso.getProfissional().getMatricula());
//		List<Recurso> recursos = q.getResultList();
//		if(recursos.isEmpty()){
//			manager.getTransaction().begin();
//			manager.persist(recurso);
//			manager.getTransaction().commit();
//			manager.close();
//			return true;
//		}else{
//			manager.close();
//			return false;
//		}
//		
//	}
//	
//	public boolean update(Recurso recurso){
//		EntityManager manager = JPAUtil.getEntityManager();
//		manager.getTransaction().begin();
//		manager.merge(recurso);
//		manager.getTransaction().commit();
//		manager.close();
//		return true;		
//	}
//	
//	public Recurso getRecursoById(Long id){
//		EntityManager manager = JPAUtil.getEntityManager();
//		Query q = manager.createNamedQuery("Recurso.findId");
//		q.setParameter("id", id);
//		Recurso recurso = (Recurso) q.getSingleResult();
//		manager.close();
//		return recurso;
//	}
//	
//	
//	public void desativar(Long id) throws ConverterException {
//		EntityManager manager = JPAUtil.getEntityManager();
//		Recurso recurso = getRecursoById(id);
////		recurso.setRegistroValidadeFim(new Date());
//		manager.getTransaction().begin();
// 		save(recurso);
//		manager.getTransaction().commit();
//		manager.close();
//	}
//	
}
