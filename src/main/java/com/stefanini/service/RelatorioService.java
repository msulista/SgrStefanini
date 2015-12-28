package com.stefanini.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.stefanini.entidade.Relatorio;
import com.stefanini.util.JPAUtil;

public class RelatorioService {
	
//	    private BarChartModel profissionalPorEquipe;
//	    
//	    public void criaGrafico(){
//	    	createBarModel();
//	    }
//	    
//	    public BarChartModel getProfissionalPorEquipe() {
//	        return profissionalPorEquipe;
//	    }
//	 
//	    private BarChartModel initBarModel() {
//	        BarChartModel model = new BarChartModel();
//	 
//	        ChartSeries grafico = new ChartSeries();
//	        for (Relatorio relatorio : profissionaisPorEquipe()) {
//				grafico.set(relatorio.getNome(), relatorio.getQuantidade());
//			}	 
//	        model.addSeries(grafico);
//	         
//	        return model;
//	    }
//	     
//	    private void createBarModel() {
//	        profissionalPorEquipe = initBarModel();
//	         
//	        profissionalPorEquipe.setTitle("Profissionais por equipe");
//	        profissionalPorEquipe.setLegendPosition("ne");
//	         
//	        Axis xAxis = profissionalPorEquipe.getAxis(AxisType.X);
//	        xAxis.setLabel("Equipes");
//	         
//	        Axis yAxis = profissionalPorEquipe.getAxis(AxisType.Y);
//	        yAxis.setLabel("N° Profissionais");
//	        yAxis.setMin(0);
//	        yAxis.setMax(50);
//	    }
	
//	@SuppressWarnings("unchecked")
//	public List<Relatorio> profissionaisPorEquipe(){
//		EntityManager manager = JPAUtil.getEntityManager();
//		Query q = manager.createNativeQuery(
//				"SELECT COUNT(PROF.ID_EQUIPE), EQUI.NOME "
//				+ "FROM  SGR_PROFISSIONAL PROF "
//				+ "LEFT JOIN SGR_EQUIPE EQUI "
//				+ "ON PROF.ID_EQUIPE = EQUI.ID_EQUIPE "
//				+ "GROUP BY PROF.ID_EQUIPE", Relatorio.class);
//		
//		List<Relatorio> relatorioProfissionalPorEquipe =  q.getResultList();
//		return relatorioProfissionalPorEquipe;		
//	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> profissionaisPorEquipe(){		
		EntityManager manager = JPAUtil.getEntityManager();
		
		String profisionalPorEquipe = "SELECT new com.stefanini.entidade.Relatorio(p.equipe.nome, COUNT(p)) FROM Profissional p "
									+ "GROUP BY p.equipe.nome";
		
		Query q = manager.createQuery(profisionalPorEquipe);
		
		List<Relatorio> relatorioProfissionalPorEquipe = (List<Relatorio>)q.getResultList();
		return relatorioProfissionalPorEquipe;		
	}
	
	@SuppressWarnings("unchecked")
	public List<Relatorio> profissionaisPorEquipeView(){		
		EntityManager manager = JPAUtil.getEntityManager();
		
		String profisionalPorEquipe = "SELECT new com.stefanini.entidade.Relatorio(v.equipe, v.qtd ) FROM sgr_view_profissional_equipe v";
		
		Query q = manager.createQuery(profisionalPorEquipe);	
		
		List<Relatorio> relatorioProfissionalPorEquipe = (List<Relatorio>)q.getResultList();
		return relatorioProfissionalPorEquipe;		
	}

}
