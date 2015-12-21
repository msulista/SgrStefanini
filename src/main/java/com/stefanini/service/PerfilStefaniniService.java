package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import com.stefanini.entidade.PerfilStefanini;
import com.stefanini.util.DateUtil;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class PerfilStefaniniService {

	public boolean save(PerfilStefanini perfilStefanini) {
		EntityManager manager = JPAUtil.getEntityManager();

		if (DateUtil.verificaDiaUtil(perfilStefanini.getRegistroValidadeInicio())) {
			manager.getTransaction().begin();
			manager.persist(perfilStefanini);
			manager.getTransaction().commit();
			manager.close();
			return true;
		} else {
			Mensagem.add("Data informada não é um dia util!");
			manager.close();
			return false;
		}
	}

	public boolean update(PerfilStefanini perfilStefanini) {
		EntityManager manager = JPAUtil.getEntityManager();

		if (DateUtil.verificaDiaUtil(perfilStefanini.getRegistroValidadeInicio())) {
			PerfilStefanini perfilStefaniniAntigo = getPerfilStefaniniById(perfilStefanini.getId());
				if(DateUtil.verificaDataValida(perfilStefanini.getRegistroValidadeInicio(), perfilStefanini.getRegistroValidadeFim())||perfilStefanini.getRegistroValidadeFim()==null){
				manager.getTransaction().begin();
			
				perfilStefaniniAntigo.setRegistroValidadeFim(new Date());
				manager.merge(perfilStefaniniAntigo);
				manager.getTransaction().commit();
				manager.close();

				PerfilStefanini perfilStefaniniNova = new PerfilStefanini();
				perfilStefaniniNova.setNome(perfilStefanini.getNome());
				perfilStefaniniNova.setValorInicial(perfilStefanini.getValorInicial());
				perfilStefaniniNova.setValorFinal(perfilStefanini.getValorFinal());
				perfilStefaniniNova.setRegistroValidadeInicio(perfilStefanini.getRegistroValidadeInicio());
				perfilStefaniniNova.setRegistroValidadeFim(perfilStefanini.getRegistroValidadeFim());
				save(perfilStefaniniNova);
				return true;
				}else{
					Mensagem.add("Erro, data final menor que a inicial!");
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
	public List<PerfilStefanini> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery(
				"SELECT * FROM sgr_perfil_stefanini WHERE REGISTRO_VALIDADE_FIM IS NULL OR REGISTRO_VALIDADE_FIM > CURRENT_DATE() ORDER BY REGISTRO_VALIDADE_INICIO ASC",
				PerfilStefanini.class);
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
}
