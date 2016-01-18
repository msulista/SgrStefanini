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

		if (DateUtil.verificaDiaUtil(perfilStefanini.getRegistroValidadeInicio())&&DateUtil.verificaDiaUtil(perfilStefanini.getRegistroValidadeFim())) {
			manager.getTransaction().begin();
			perfilStefanini.setCodigo(this.gerarCodigo());
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

		if (DateUtil.verificaDiaUtil(perfilStefanini.getRegistroValidadeInicio())&&DateUtil.verificaDiaUtil(perfilStefanini.getRegistroValidadeFim())) {
			
			PerfilStefanini perfilStefaniniAntigo = getPerfilStefaniniByCodigoParaEdicao(perfilStefanini.getCodigo());
			
				if(DateUtil.verificaDataValida(perfilStefanini.getRegistroValidadeInicio(), perfilStefanini.getRegistroValidadeFim())){
				manager.getTransaction().begin();
			
				if(DateUtil.verificaDataValida(DateUtil.getDataParaComparacao(new Date()), perfilStefanini.getRegistroValidadeInicio())){
				if(perfilStefaniniAntigo.getRegistroValidadeInicio().compareTo(perfilStefanini.getRegistroValidadeInicio())==0){
					perfilStefaniniAntigo.setRegistroValidadeFim(new Date());
					manager.merge(perfilStefaniniAntigo);
	
				}else{
					perfilStefaniniAntigo.setRegistroValidadeFim(DateUtil.retornaDataFimAntesDoNovoInicio(perfilStefanini.getRegistroValidadeInicio()));
					manager.merge(perfilStefaniniAntigo);
									
				}

				PerfilStefanini perfilStefaniniNova = new PerfilStefanini();
				perfilStefaniniNova.setNome(perfilStefanini.getNome());
				perfilStefaniniNova.setValorInicial(perfilStefanini.getValorInicial());
				perfilStefaniniNova.setValorFinal(perfilStefanini.getValorFinal());
				perfilStefaniniNova.setRegistroValidadeInicio(perfilStefanini.getRegistroValidadeInicio());
				perfilStefaniniNova.setRegistroValidadeFim(perfilStefanini.getRegistroValidadeFim());
				perfilStefaniniNova.setCodigo(perfilStefanini.getCodigo());
				manager.persist(perfilStefaniniNova);
				
				manager.getTransaction().commit();
				manager.close();
				return true;
				}else{
					Mensagem.add("Erro, Nova data de registro nao pode ser anterior ao dia atual!");
					manager.close();
					return false;
				}
				}else{
					Mensagem.add("Erro, data final anterior a inicial!");
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
		Query q = manager.createNamedQuery("PerfilStefanini.findAtivos");
		List<PerfilStefanini> perfisStefanini = q.getResultList();
		manager.close();
		return perfisStefanini;
	}
	@SuppressWarnings("unchecked")
	public List<PerfilStefanini> listarHistorico(Long codigo) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("PerfilStefanini.findCodigoParaHistorico");
		q.setParameter("codigo", codigo);
		List<PerfilStefanini> perfisStefanini = q.getResultList();
		manager.close();
		return perfisStefanini;
	}

	public PerfilStefanini getPerfilStefaniniById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("PerfilStefanini.findId");
		q.setParameter("id", id);
		PerfilStefanini perfilStefanini = (PerfilStefanini) q.getSingleResult();
		manager.close();
		return perfilStefanini;
	}
	
	public PerfilStefanini getPerfilStefaniniByCodigoParaEdicao(Long codigo){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("PerfilStefanini.findCodigoParaEdicao");
		q.setParameter("codigo", codigo);
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
	
	public Long gerarCodigo(){
		if(!this.listarAtivos().isEmpty()){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("PerfilStefanini.findMaxId");
		PerfilStefanini perfilStefanini = (PerfilStefanini) q.getSingleResult();
		manager.close();
		return perfilStefanini.getId()+1;
		}else{
			return (long) 1;
		}
	}
}
