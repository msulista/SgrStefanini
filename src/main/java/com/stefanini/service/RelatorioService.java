package com.stefanini.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Profissional;
import com.stefanini.entidade.Relatorio;
import com.stefanini.util.JPAUtil;

public class RelatorioService {
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> profissionaisPorEquipe(){		
		EntityManager manager = JPAUtil.getEntityManager();
		
		String profisionalPorEquipe = "SELECT new com.stefanini.entidade.Relatorio(p.equipe.nome, COUNT(p)) FROM Profissional p WHERE p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE GROUP BY p.equipe.nome";
		
		Query q = manager.createQuery(profisionalPorEquipe);
//		Query q = manager.createNamedQuery("Relatorio.profissionalPorEquipe");
		List<Relatorio> relatorioProfissionalPorEquipe = (List<Relatorio>)q.getResultList();
		manager.close();
		return relatorioProfissionalPorEquipe;		
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> listaDeProfissionaisPorEquipe(String nome){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByEquipeNome");
		q.setParameter("nome", nome);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
}
