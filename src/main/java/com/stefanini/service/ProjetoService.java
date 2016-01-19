package com.stefanini.service;

import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Projeto;
import com.stefanini.util.JPAUtil;

public class ProjetoService {
	
	@SuppressWarnings("unchecked")
	public  boolean save(Projeto projeto){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Projeto.findCodigo");
		q.setParameter("codigo", projeto.getCodigo());
		List<Projeto> projetos = q .getResultList();
		if(projetos.isEmpty()){
			manager.getTransaction().begin();
			manager.persist(projeto);
			manager.getTransaction().commit();
			manager.close();
			return true;
		}else{
			manager.close();
			return false;
		}
	}
	
	public boolean update(Projeto projeto){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.merge(projeto);
		manager.getTransaction().commit();
		manager.close();
		return true;		
	}
	
	public Projeto getProjetoById(Long id){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Projeto.findId");
		q.setParameter("id", id);
		Projeto projeto = (Projeto) q.getSingleResult();
		manager.close();
		return projeto;
	}

	public void desativar(Long id) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		Projeto projeto =  getProjetoById(id);
		manager.getTransaction().begin();
 		manager.merge(projeto);
		manager.getTransaction().commit();
		manager.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Projeto> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Projeto.findAtivos");
		List<Projeto> projetos = q.getResultList();
		manager.close();
		return projetos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Projeto> listarTodos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Projeto.findAll");
		List<Projeto> projetos = q.getResultList();
		manager.close();
		return projetos;
	}
}
