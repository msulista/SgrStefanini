package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Status;
import com.stefanini.util.JPAUtil;

public class StatusService {

	public void save(Status status){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(status);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public void update(Status status){	
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();	
		
		Status statusMerge = (Status)getStatusById(status.getId());
		statusMerge.setRegistroValidadeFim(status.getDataManipulacao());
		manager.merge(statusMerge);
		manager.getTransaction().commit();
		manager.close();
		
		Status statusPersist = new Status();
		statusPersist.setNome(status.getNome());
		statusPersist.setRegistroValidadeInicio(status.getDataManipulacao());		
		save(statusPersist);
	}
	
	@SuppressWarnings("unchecked")
	public List<Status> listarAtivos(){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_status WHERE REGISTRO_VALIDADE_FIM IS NULL ORDER BY REGISTRO_VALIDADE_INICIO ASC", Status.class);
		List<Status> listStatus = q.getResultList();
		return listStatus;
	}
	
	public Status getStatusById(Long id){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_status WHERE ID_STATUS = :idStatus", Status.class);
		q.setParameter("idStatus", id);
		Status status = (Status)q.getSingleResult();
		manager.close();
		return status;
	}

	public void desativar(Long id) throws ConverterException{
		EntityManager manager = JPAUtil.getEntityManager();	
		manager.getTransaction().begin();		
		Status statusMerge = (Status)getStatusById(id);
		statusMerge.setRegistroValidadeFim(new Date());
		manager.merge(statusMerge);
		manager.getTransaction().commit();
		manager.close();		
	}
}
