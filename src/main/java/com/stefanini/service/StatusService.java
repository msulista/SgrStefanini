package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Status;
import com.stefanini.util.DateUtil;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class StatusService {

	public boolean save(Status status) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		if (DateUtil.verificaDiaUtil(status.getRegistroValidadeInicio())) {
			manager.persist(status);
			manager.getTransaction().commit();
			manager.close();
			return true;
		} else {
			Mensagem.add("Data informada n�o � um dia util!");
			manager.close();
			return false;
		}
	}

	public boolean update(Status status) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		if (DateUtil.verificaDiaUtil(status.getRegistroValidadeInicio())) {
			Status statusMerge = (Status) getStatusById(status.getId());
				if(DateUtil.verificaDataValida(status.getRegistroValidadeInicio(), status.getRegistroValidadeFim())||status.getRegistroValidadeFim()==null){
				statusMerge.setRegistroValidadeFim(new Date());
				manager.merge(statusMerge);
				manager.getTransaction().commit();
				manager.close();

				Status statusPersist = new Status();
				statusPersist.setNome(status.getNome());
				statusPersist.setRegistroValidadeInicio(status.getRegistroValidadeInicio());
				statusPersist.setRegistroValidadeFim(status.getRegistroValidadeFim());
				save(statusPersist);
				return true;
				}else{
					Mensagem.add("Erro, data final menor que a inicial!");
					manager.close();
					return false;
				}
		} else {
			Mensagem.add("Data informada n�o � um dia util!");
			manager.close();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Status> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Status.findAtivos");
		List<Status> listStatus = q.getResultList();
		return listStatus;
	}

	public Status getStatusById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Status.findId");
		q.setParameter("idStatus", id);
		Status status = (Status) q.getSingleResult();
		manager.close();
		return status;
	}

	public void desativar(Long id) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		Status statusMerge = (Status) getStatusById(id);
		statusMerge.setRegistroValidadeFim(new Date());
		manager.merge(statusMerge);
		manager.getTransaction().commit();
		manager.close();
	}
}
