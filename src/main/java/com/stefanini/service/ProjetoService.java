package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Profissional;
import com.stefanini.entidade.Projeto;
import com.stefanini.util.DateUtil;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class ProjetoService {
	
	@SuppressWarnings("unchecked")
	public  boolean save(Projeto projeto){
		EntityManager manager = JPAUtil.getEntityManager();
		if (DateUtil.verificaDiaUtil(projeto.getRegistroValidadeInicio())&&DateUtil.verificaDiaUtil(projeto.getRegistroValidadeFim())) {	
			Query q = manager.createNamedQuery("Projeto.findCodigo");
			q.setParameter("codigo", projeto.getCodigo());
			List<Projeto> projetos = q.getResultList();
			if(projetos.isEmpty()){
				manager.getTransaction().begin();
				projeto.setRegistroValidadeInicio(projeto.getDataInicio());
				manager.persist(projeto);
				manager.getTransaction().commit();
				manager.close();
				return true;
			}else{
				Mensagem.add("Código já cadastrado!");
				manager.close();
				return false;
			}
		} else {
			Mensagem.add("Data informada não é um dia util!");
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
		projeto.setRegistroValidadeFim(new Date());
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
	
	public Long gerarCodigo(){
		if(!this.listarAtivos().isEmpty()){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("projeto.findMaxId");
		Projeto projeto = (Projeto) q.getSingleResult();
		manager.close();
		return projeto.getId()+1;
		}else{
			return (long) 1;
		}
	}
	
	public Projeto getProjetoParaEdicao(int codigo) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Projeto.checkCodigoParaEdicao");
		q.setParameter("codigo", codigo);
		Projeto projeto= (Projeto) q.getSingleResult();
		manager.close();
		return projeto;
	}
}
