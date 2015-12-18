package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Perfil;
import com.stefanini.util.DateUtil;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class PerfilService {

	public boolean save(Perfil perfil) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		if (DateUtil.verificaDiaUtil(perfil.getRegistroValidadeInicio())) {
			manager.persist(perfil);
			manager.getTransaction().commit();
			manager.close();
			return true;
		} else {
			Mensagem.add("Data informada não é um dia util!");
			manager.close();
			return false;
		}

	}

	public boolean update(Perfil perfil) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		if (DateUtil.verificaDiaUtil(perfil.getRegistroValidadeInicio())) {
			Perfil perfilMerge = getPerfilById(perfil.getId());
			if (DateUtil.verificaDataValida(perfilMerge.getRegistroValidadeInicio(), perfil.getRegistroValidadeInicio())) {
				if(DateUtil.verificaDataValida(perfil.getRegistroValidadeInicio(), perfil.getRegistroValidadeFim())||perfil.getRegistroValidadeFim()==null){
				perfilMerge.setRegistroValidadeFim(new Date());
				manager.merge(perfilMerge);
				manager.getTransaction().commit();
				manager.close();

				Perfil perfilPersist = new Perfil();
				perfilPersist.setNome(perfil.getNome());
				perfilPersist.setRegistroValidadeInicio(perfil.getRegistroValidadeInicio());
				perfilPersist.setRegistroValidadeFim(perfil.getRegistroValidadeFim());
				save(perfilPersist);
				return true;
				}else{
					Mensagem.add("Erro, data final menor que a inicial!");
					manager.close();
					return false;
				}
			} else {
				Mensagem.add("Erro, nova data é anterior a cadastrada originalmente!");
				manager.close();
				return false;
			}
		} else {
			Mensagem.add("Data informada não é um dia util!");
			manager.close();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Perfil> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery(
				"SELECT * FROM sgr_perfil WHERE REGISTRO_VALIDADE_FIM IS NULL OR REGISTRO_VALIDADE_FIM > CURRENT_DATE() ORDER BY REGISTRO_VALIDADE_INICIO ASC",
				Perfil.class);
		List<Perfil> perfis = q.getResultList();
		return perfis;
	}

	public Perfil getPerfilById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_perfil WHERE ID_PERFIL = :idPerfil", Perfil.class);
		q.setParameter("idPerfil", id);
		Perfil perfil = (Perfil) q.getSingleResult();
		manager.close();
		return perfil;
	}

	public void desativar(Long id) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		Perfil perfilMerge = getPerfilById(id);
		perfilMerge.setRegistroValidadeFim(new Date());
		manager.merge(perfilMerge);
		manager.getTransaction().commit();
		manager.close();
	}
}
