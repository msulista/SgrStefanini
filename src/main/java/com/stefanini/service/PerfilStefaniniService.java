package com.stefanini.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.CargaHoraria;
import com.stefanini.entidade.PerfilStefanini;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class PerfilStefaniniService {

	public boolean save(PerfilStefanini perfilStefanini) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		if (verificaDiaUtil(perfilStefanini.getRegistroValidadeInicio())) {
			manager.persist(perfilStefanini);
			manager.getTransaction().commit();
			manager.close();
			return true;
		}
		Mensagem.add("Data informada não é um dia util!");
		manager.close();
		return false;
	}

	public boolean update(PerfilStefanini perfilStefanini) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		if(verificaDiaUtil(perfilStefanini.getDataManipulacaoFim())){
		PerfilStefanini perfilStefaniniAntigo = getPerfilStefaniniById(perfilStefanini.getId());
		perfilStefaniniAntigo.setRegistroValidadeFim(perfilStefanini.getDataManipulacaoFim());
		manager.merge(perfilStefaniniAntigo);
		manager.getTransaction().commit();
		manager.close();

		PerfilStefanini perfilStefaniniNova = new PerfilStefanini();
		perfilStefaniniNova.setNome(perfilStefanini.getNome());
		perfilStefaniniNova.setValorInicial(perfilStefanini.getValorInicial());
		perfilStefaniniNova.setValorFinal(perfilStefanini.getValorFinal());
		perfilStefaniniNova.setRegistroValidadeInicio(perfilStefanini.getDataManipulacaoFim());
		save(perfilStefaniniNova);
		return true;
		}
		Mensagem.add("Data informada não é um dia util!");
		manager.close();
		return false;
	}

	@SuppressWarnings("unchecked")
	public List<PerfilStefanini> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery(
				"SELECT * FROM sgr_perfil_stefanini WHERE REGISTRO_VALIDADE_FIM IS NULL ORDER BY REGISTRO_VALIDADE_INICIO ASC",
				CargaHoraria.class);
		List<PerfilStefanini> perfisStefanini = q.getResultList();
		manager.close();
		return perfisStefanini;
	}

	public PerfilStefanini getPerfilStefaniniById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_perfil_stefanini WHERE ID_PERFIL_STEFANINI = :id",
				PerfilStefanini.class);
		q.setParameter("id", id);
		PerfilStefanini perfilStefanini = (PerfilStefanini) q.getSingleResult();
		manager.close();
		return perfilStefanini;
	}

	public void desativar(Long id) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		PerfilStefanini perfilStefanini = getPerfilStefaniniById(id);
		perfilStefanini.setRegistroValidadeFim(new Date());
		manager.getTransaction().begin();
		manager.merge(perfilStefanini);
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
