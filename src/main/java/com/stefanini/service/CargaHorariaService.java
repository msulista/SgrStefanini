package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import com.stefanini.entidade.CargaHoraria;
import com.stefanini.util.JPAUtil;

public class CargaHorariaService {
	
	public void save(CargaHoraria cargaHoraria) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(cargaHoraria);
		manager.getTransaction().commit();
		manager.close();
	}

	public void update(CargaHoraria cargaHoraria) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		
		CargaHoraria cargaHorariaAntiga = getCargaHorariaById(cargaHoraria.getId());
		cargaHorariaAntiga.setRegistroValidadeFim(cargaHoraria.getDataManipulacaoFim());
		manager.merge(cargaHorariaAntiga);
		manager.getTransaction().commit();
		manager.close();
		
		CargaHoraria cargaHorariaNova = new CargaHoraria();
		cargaHorariaNova.setCargaHoraria(cargaHoraria.getCargaHoraria());
		cargaHorariaNova.setRegistroValidadeInicio(cargaHoraria.getDataManipulacaoFim());
		save(cargaHorariaNova);
	}

	@SuppressWarnings("unchecked")
	public List<CargaHoraria> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_carga_horaria WHERE REGISTRO_VALIDADE_FIM IS NULL", CargaHoraria.class);
		List<CargaHoraria> cargaHorarias = q.getResultList();
		manager.close();
		return cargaHorarias;
	}

	public CargaHoraria getCargaHorariaById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_carga_horaria WHERE ID_CARGA_HORARIA = :id",
				CargaHoraria.class);
		q.setParameter("id", id);
		CargaHoraria cargaHoraria = (CargaHoraria) q.getSingleResult();
		manager.close();
		return cargaHoraria;
	}

	public void desativar(Long id) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		CargaHoraria cargaHoraria = getCargaHorariaById(id);
		cargaHoraria.setRegistroValidadeFim(new Date());
		manager.getTransaction().begin();
		manager.remove(manager.getReference(CargaHoraria.class, cargaHoraria.getId()));
		manager.getTransaction().commit();
		manager.close();
		
	}
}
