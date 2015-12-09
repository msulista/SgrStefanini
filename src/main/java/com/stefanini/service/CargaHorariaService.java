package com.stefanini.service;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import com.stefanini.entidade.CargaHoraria;
import com.stefanini.entidade.Modulo;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class CargaHorariaService {
	
	
	public void save(CargaHoraria cargaHoraria){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(cargaHoraria);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public void update(CargaHoraria cargaHoraria) {
		
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.merge(cargaHoraria);
		manager.getTransaction().commit();
		manager.close();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CargaHoraria> listar(){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM apr_modulo", Modulo.class);
		List<CargaHoraria> cargaHorarias = q.getResultList();
		manager.close();
		return cargaHorarias;
	}
	
	public CargaHoraria getCargaHorariaById(Long id){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM apr_modulo WHERE ID_MODULO = :idModulo", CargaHoraria.class);
		q.setParameter("idModulo", id);
		CargaHoraria cargaHoraria = (CargaHoraria)q.getSingleResult();
		manager.close();
		return cargaHoraria;
	}
	
	public void remove(Long id) throws ConverterException{		
		EntityManager manager = JPAUtil.getEntityManager();
		CargaHoraria cargaHoraria = getCargaHorariaById(id);		
		try {
			manager.getTransaction().begin();
			manager.remove(manager.getReference(CargaHoraria.class, cargaHoraria.getIdCargaHoraria()));
			manager.getTransaction().commit();
		} catch (RollbackException rbe) {
			// TODO: handle exception
			FacesMessage message = new FacesMessage("Módulo esta em uso, não é possível deletar.", " O tempo acabou.");
            FacesContext.getCurrentInstance().addMessage(null, message);
		}finally {
			manager.close();
		}
	}
}
