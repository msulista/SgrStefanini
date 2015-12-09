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
	
	
	public void save(Modulo modulo){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(modulo);
		manager.getTransaction().commit();
		manager.close();
	}
	
//	public void update(Modulo modulo) {
//		EntityManager manager = JPAUtil.getEntityManager();
//		modulo.setMappingId(dataAtualSistema);
//		manager.getTransaction().begin();
//		manager.merge(modulo);
//		manager.getTransaction().commit();
//		manager.close();
//		save(modulo);
//	}
	
	@SuppressWarnings("unchecked")
	public List<Modulo> listar(){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM apr_modulo", Modulo.class);
		List<Modulo> modulos = q.getResultList();
		manager.close();
		return modulos;
	}
	
	public Modulo getModuloById(Long id){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM apr_modulo WHERE ID_MODULO = :idModulo", Modulo.class);
		q.setParameter("idModulo", id);
		Modulo modulo = (Modulo)q.getSingleResult();
		manager.close();
		return modulo;
	}
	
	public void remove(Long id) throws ConverterException{		
		EntityManager manager = JPAUtil.getEntityManager();
		Modulo modulo = getModuloById(id);		
		try {
			manager.getTransaction().begin();
			manager.remove(manager.getReference(Modulo.class, modulo.getId()));
			manager.getTransaction().commit();
		} catch (RollbackException rbe) {
			// TODO: handle exception
			FacesMessage message = new FacesMessage("Módulo esta em uso, não é possível deletar.", " O tempo acabou.");
            FacesContext.getCurrentInstance().addMessage(null, message);
		}finally {
			manager.close();
		}
	}

	public void save(CargaHoraria cargaHoraria) {
		// TODO Auto-generated method stub
		
	}

	public void update(CargaHoraria cargaHoraria) {
		// TODO Auto-generated method stub
		
	}
	
//	public List<CargaHoraria> listar(){
//		return null;
//	}
//	

}
