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

		cargaHoraria.setRegistroValidadeFim(new Date());

		CargaHoraria novaCargaHoraria = new CargaHoraria();
		novaCargaHoraria.setCargaHoraria(cargaHoraria.getCargaHoraria());
		novaCargaHoraria.setRegistroValidadeInicio(cargaHoraria.getRegistroValidadeFim());

		Query query = manager.createNativeQuery(
				"UPDATE sgr_carga_horaria SET REGISTRO_VALIDADE_FIM = :dataFim WHERE ID_CARGA_HORARIA = :id");
		query.setParameter("dataFim", cargaHoraria.getRegistroValidadeFim());
		query.setParameter("id", cargaHoraria.getIdCargaHoraria());
		query.executeUpdate();
		save(novaCargaHoraria);
		manager.close();
	}

	@SuppressWarnings("unchecked")
	public List<CargaHoraria> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_carga_horaria WHERE REGISTRO_VALIDADE_FIM = null",
				CargaHoraria.class);
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

	public void remove(Long id) throws ConverterException {
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
		} finally {
			manager.close();
		}
	}
}
