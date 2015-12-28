package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.CargaHoraria;
import com.stefanini.util.DateUtil;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;


public class CargaHorariaService {
	

	public boolean save(CargaHoraria cargaHoraria) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		if(DateUtil.verificaDiaUtil(cargaHoraria.getRegistroValidadeInicio())&&DateUtil.verificaDiaUtil(cargaHoraria.getRegistroValidadeFim())) {
			if(DateUtil.verificaDataValida(cargaHoraria.getRegistroValidadeInicio(), cargaHoraria.getRegistroValidadeFim())){
			manager.persist(cargaHoraria);
			manager.getTransaction().commit();
			manager.close();
			return true;
			}else{
				Mensagem.add("Data final do registro anterior a data inicial!");
				manager.close();
				return false;
			}
		}else{
		Mensagem.add("Data informada n�o � um dia util!");
		manager.close();
		return false;
		}
	}

	public boolean update(CargaHoraria cargaHoraria) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		if(DateUtil.verificaDiaUtil(cargaHoraria.getRegistroValidadeInicio())&&DateUtil.verificaDiaUtil(cargaHoraria.getRegistroValidadeFim())){
			CargaHoraria cargaHorariaAntiga = getCargaHorariaById(cargaHoraria.getId());
			
				if (DateUtil.verificaDataValida(cargaHoraria.getRegistroValidadeInicio(),
						cargaHoraria.getRegistroValidadeFim())) {
					
		cargaHorariaAntiga.setRegistroValidadeFim(new Date());
		manager.merge(cargaHorariaAntiga);
		manager.getTransaction().commit();
		manager.close();

		CargaHoraria cargaHorariaNova = new CargaHoraria();
		cargaHorariaNova.setCargaHoraria(cargaHoraria.getCargaHoraria());
		cargaHorariaNova.setRegistroValidadeInicio(cargaHoraria.getRegistroValidadeInicio());
		cargaHorariaNova.setRegistroValidadeFim(cargaHoraria.getRegistroValidadeFim());
		save(cargaHorariaNova);
		return true;
				}else{
					Mensagem.add("Erro, data final menor que a inicial!");
					manager.close();
					return false;
				}
	
		}else{
		Mensagem.add("Data informada n�o � um dia util!");
		manager.close();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<CargaHoraria> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("CargaHoraria.findAtivos");
		List<CargaHoraria> cargaHorarias = q.getResultList();
		manager.close();
		return cargaHorarias;
	}

	public CargaHoraria getCargaHorariaById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("CargaHoraria.findId");
		q.setParameter("id", id);
		CargaHoraria cargaHoraria = (CargaHoraria) q.getSingleResult();
		manager.close();
		return cargaHoraria;
	}

	public void desativar(Long id) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByCargaHoraria");
		q.setParameter("id", id);
		if(q.getResultList().isEmpty()){
			CargaHoraria cargaHoraria = getCargaHorariaById(id);		
			cargaHoraria.setRegistroValidadeFim(new Date());
			manager.getTransaction().begin();
			manager.merge(cargaHoraria);
			manager.getTransaction().commit();
			manager.close();
		}else {
			Mensagem.add("Existem profissionais ativos vinculados a esta Carga Hor�ria, n�o pode ser exclu�da.");
			manager.close();
		}		
	}
}
