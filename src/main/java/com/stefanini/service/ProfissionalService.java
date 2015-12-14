package com.stefanini.service;

import java.util.Date;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.util.JPAUtil;

public class ProfissionalService {
	
//	public void save(){
//		EntityManager manager = JPAUtil.getEntityManager();
//		manager.getTransaction().begin();
//		manager.persist(arg0);
//		manager.getTransaction().commit();
//		manager.close();
//	}
//	
//	public void update(){
//		EntityManager manager = JPAUtil.getEntityManager();
//		manager.getTransaction().begin();	
//		
//		Cargo cargoMerge = (Cargo)getCargoById(cargo.getId());
//		cargoMerge.setRegistroValidadeFim(cargo.getDataManipulacao());
//		manager.merge(cargoMerge);
//		manager.getTransaction().commit();
//		manager.close();
//		
//		Cargo cargoPersist = new Cargo();
//		cargoPersist.setNome(cargo.getNome());
//		cargoPersist.setRegistroValidadeInicio(cargo.getDataManipulacao());		
//		save(cargoPersist);
//	}
//	
//	public Cargo getCargoById(Long id){
//		EntityManager manager = JPAUtil.getEntityManager();
//		Query q = manager.createNativeQuery("SELECT * FROM sgr_cargo WHERE ID_CARGO = :idCargo", Cargo.class);
//		q.setParameter("idCargo", id);
//		Cargo cargo = (Cargo) q.getSingleResult();
//		manager.close();
//		return cargo;
//	}
//
//	public void desativar(Long id) throws ConverterException{
//		EntityManager manager = JPAUtil.getEntityManager();	
//		manager.getTransaction().begin();		
//		Cargo cargoMerge = (Cargo)getCargoById(id);
//		cargoMerge.setRegistroValidadeFim(new Date());
//		manager.merge(cargoMerge);
//		manager.getTransaction().commit();
//		manager.close();		
//	}

}
