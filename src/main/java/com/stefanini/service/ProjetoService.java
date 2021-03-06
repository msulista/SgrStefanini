package com.stefanini.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Celula;
import com.stefanini.entidade.Equipe;
import com.stefanini.entidade.Profissional;
import com.stefanini.entidade.Projeto;
import com.stefanini.entidade.Recurso;
import com.stefanini.util.DateUtil;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class ProjetoService {
	
	@SuppressWarnings("unchecked")
	public  boolean save(Projeto projeto){
		EntityManager manager = JPAUtil.getEntityManager();
		if (DateUtil.verificaDiaUtil(projeto.getRegistroValidadeInicio())&&DateUtil.verificaDiaUtil(projeto.getRegistroValidadeFim())&&DateUtil.verificaDiaUtil(projeto.getDataInicio())&&DateUtil.verificaDiaUtil(projeto.getDataFim())) {	
			if(DateUtil.verificaDataValida(projeto.getRegistroValidadeInicio(), projeto.getRegistroValidadeFim())){
				if(DateUtil.verificaDataValida(projeto.getDataInicio(), projeto.getDataFim())){
			Query q = manager.createNamedQuery("Projeto.findCodigo");
			q.setParameter("codigo", projeto.getCodigo());
			List<Projeto> projetos = q.getResultList();
			if(projetos.isEmpty()){
				manager.getTransaction().begin();
				projeto.setRegistroValidadeInicio(projeto.getDataInicio());
				manager.persist(projeto);
				manager.getTransaction().commit();
				manager.close();
				return true;
			}else{
				Mensagem.add("C�digo j� cadastrado!");
				manager.close();
				return false;
			}
				}else{
					Mensagem.add("Data inicio anterior ao fim!");
					manager.close();
					return false;
				}
			}else{
				Mensagem.add("Data registro fim anterior ao registro inicio!");
				manager.close();
				return false;
			}
		} else {
			Mensagem.add("Data informada n�o � um dia util!");
			manager.close();
			return false;
		}
	}
	
	public boolean update(Projeto projeto){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		
		if (DateUtil.verificaDiaUtil(projeto.getRegistroValidadeInicio())&&DateUtil.verificaDiaUtil(projeto.getRegistroValidadeFim())&&DateUtil.verificaDiaUtil(projeto.getDataInicio())&&DateUtil.verificaDiaUtil(projeto.getDataFim())) {	
			
			Projeto projetoMerge = getProjetoParaEdicao(projeto.getCodigo());
			
			if(DateUtil.verificaDataValida(projetoMerge.getDataInicio(), projeto.getDataInicio())){
				
				if(DateUtil.verificaDataValida(projeto.getDataInicio(), projeto.getDataFim())){
					
					if(DateUtil.verificaDataValida(projeto.getRegistroValidadeInicio(),projeto.getRegistroValidadeFim())){
						
						if(projeto.getRegistroValidadeInicio().compareTo(projetoMerge.getRegistroValidadeInicio())==0){
							projetoMerge.setRegistroValidadeFim(new Date());
							manager.merge(projetoMerge);
						}else{
							projetoMerge.setRegistroValidadeFim(DateUtil.retornaDataFimAntesDoNovoInicio(projeto.getRegistroValidadeInicio()));
							manager.merge(projetoMerge);
						}
						
						Projeto projetoPersist = new Projeto();
						projetoPersist.setBudget(projeto.getBudget());
						projetoPersist.setCelula(projeto.getCelula());
						projetoPersist.setCodigo(projeto.getCodigo());
						projetoPersist.setCustoPrevisto(projeto.getCustoPrevisto());
						projetoPersist.setDataFim(projeto.getDataFim());
						projetoPersist.setDataInicio(projeto.getDataInicio());
						projetoPersist.setEquipe(projeto.getEquipe());
						projetoPersist.setNome(projeto.getNome());
						projetoPersist.setRegistroValidadeFim(projeto.getRegistroValidadeFim());
						projetoPersist.setRegistroValidadeInicio(projeto.getRegistroValidadeInicio());
						projetoPersist.setSaldo(projeto.getSaldo());
						manager.persist(projetoPersist);
		
		manager.getTransaction().commit();
		manager.close();
		return true;
		
					}else{
						Mensagem.add("Data final anterior a inicial");
						manager.close();
						return false;
					}
					
				}else{
					Mensagem.add("Data fim anterior a inicio");
					manager.close();
					return false;
				}
				
			}else{
				Mensagem.add("Data inicio anterior a altera��es ja cadastradas");
				manager.close();
				return false;
			}
			
		}else{
			Mensagem.add("Data informada n�o � um dia util!");
			manager.close();
			return false;
		}
		
	}
	
	public Projeto getProjetoById(Long id){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Projeto.findId");
		q.setParameter("id", id);
		Projeto projeto = (Projeto) q.getSingleResult();
		manager.close();
		return projeto;
	}
	
	public Projeto getProjetoByCodigo(Long codigo){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Projeto.findCodigo");
		q.setParameter("codigo", codigo);
		Projeto projeto = (Projeto) q.getSingleResult();
		manager.close();
		return projeto;
	}

	public void desativar(Long id) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		Projeto projeto =  getProjetoById(id);
		projeto.setRegistroValidadeFim(new Date());
		manager.getTransaction().begin();
 		manager.merge(projeto);
		manager.getTransaction().commit();
		manager.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<Projeto> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Projeto.findAtivos");
		List<Projeto> projetos = q.getResultList();
		manager.close();
		return projetos;
	}
	
	@SuppressWarnings("unchecked")
	public List<Projeto> listarTodos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Projeto.findByEquipeECelula");
		List<Projeto> projetos = q.getResultList();
		manager.close();
		return projetos;
	}
	
	public Long gerarCodigo(){
		if(!this.listarAtivos().isEmpty()){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("projeto.findMaxId");
		Projeto projeto = (Projeto) q.getSingleResult();
		manager.close();
		return projeto.getId()+1;
		}else{
			return (long) 1;
		}
	}
	
	public Projeto getProjetoParaEdicao(int codigo) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Projeto.checkCodigoParaEdicao");
		q.setParameter("codigo", codigo);
		Projeto projeto= (Projeto) q.getSingleResult();
		manager.close();
		return projeto;
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<Projeto> listarTodos(Equipe equipe, Celula celula) {
		EntityManager manager = JPAUtil.getEntityManager();
		if(equipe == null && celula !=null){
			Query q = manager.createNamedQuery("Projeto.findByCelula");
			q.setParameter("id", celula.getId());
			List<Projeto> projetos = q.getResultList();
			manager.close();
			return projetos;
		}else if(equipe != null && celula == null){
			Query q = manager.createNamedQuery("Projeto.findByEquipe");
			q.setParameter("id", equipe.getId());
			List<Projeto> projetos = q.getResultList();
			manager.close();
			return projetos;
		}else if(equipe != null && celula != null){
		Query q = manager.createNamedQuery("Projeto.findByEquipeECelula");
		q.setParameter("equipe", equipe.getId());
		q.setParameter("celula", celula.getId());
		List<Projeto> projetos= q.getResultList();
		manager.close();
		return projetos;
		}else{
			List<Projeto> projetos = new ArrayList<>();
			return projetos;
		}
	}
	
}
