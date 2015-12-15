package com.stefanini.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Celula;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class CelulaService {

	public boolean save(Celula celula) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		if (verificaDiaUtil(celula.getRegistroValidadeInicio())) {
			manager.persist(celula);
			manager.getTransaction().commit();
			manager.close();
			return true;
		} else {
			Mensagem.add("Data informada não é um dia util!");
			manager.close();
			return false;
		}

	}

	public boolean update(Celula celula) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		if (verificaDiaUtil(celula.getDataManipulacao())) {
			Celula celulaMerge = getCelulaById(celula.getId());
			celulaMerge.setRegistroValidadeFim(celula.getDataManipulacao());
			manager.merge(celulaMerge);
			manager.getTransaction().commit();
			manager.close();

			Celula celulaPersist = new Celula();
			celulaPersist.setNome(celula.getNome());
			celulaPersist.setRegistroValidadeInicio(celula.getDataManipulacao());
			save(celulaPersist);
			return true;
		} else {
			Mensagem.add("Data informada não é um dia util!");
			manager.close();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Celula> listarAtivo() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery(
				"SELECT * FROM sgr_celula WHERE REGISTRO_VALIDADE_FIM IS NULL ORDER BY REGISTRO_VALIDADE_INICIO ASC", Celula.class);
		List<Celula> celulas = q.getResultList();
		manager.close();
		return celulas;
	}

	public Celula getCelulaById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_celula WHERE ID_CELULA = :idCelula", Celula.class);
		q.setParameter("idCelula", id);
		Celula celula = (Celula)q.getSingleResult();
		manager.close();
		return celula;
	}

	public void desativar(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		Celula celulaMerge = getCelulaById(id);
		celulaMerge.setRegistroValidadeFim(new Date());
		manager.merge(celulaMerge);
		manager.getTransaction().commit();
		manager.close();
	}

	public boolean verificaDiaUtil(Date data) {
		GregorianCalendar calendar = new GregorianCalendar();

		calendar.setTime(data);

		if (calendar.get(GregorianCalendar.DAY_OF_WEEK) == 1 || calendar.get(GregorianCalendar.DAY_OF_WEEK) == 7) {
			return false;
		} else {
			return true;

		}
	}
}
