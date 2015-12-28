package com.stefanini.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Relatorio;
import com.stefanini.service.RelatorioService;

@ManagedBean
@SessionScoped
@URLMappings(mappings = {
		@URLMapping(id = "relatorio", pattern = "/profissional-por-equipe", viewId = "/pages/relatorio/relatorio-profissional-equipe.xhtml")
})
public class RelatorioManager {
	
	private Relatorio relatorio = new Relatorio();
	private RelatorioService service = new RelatorioService();
	private BarChartModel profissionalPorEquipe;
	private List<Relatorio> relatorios = new ArrayList<>();

	public RelatorioManager() {
	}	
	
	@PostConstruct
	public void init() {
	   criaGrafico();
	}
	
	public Relatorio getRelatorio() {
		return relatorio;
	}	
	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}
	public RelatorioService getService() {
		return service;
	}
	public void setService(RelatorioService service) {
		this.service = service;
	}		
	public BarChartModel getProfissionalPorEquipe() {
		return profissionalPorEquipe;
	}
	public void setProfissionalPorEquipe(BarChartModel profissionalPorEquipe) {
		this.profissionalPorEquipe = profissionalPorEquipe;
	}	
	public List<Relatorio> getRelatorios() {
		relatorios = this.service.profissionaisPorEquipe();
		return relatorios;
	}
	public void setRelatorios(List<Relatorio> relatorios) {
		this.relatorios = relatorios;
	}
	
	//Graficos
	
	public void criaGrafico(){
	   	createProfissionalPorEquipe();
	}
	
	//Profissional por Equipe
	private BarChartModel initProfissionalPorEquipe() {
	    BarChartModel model = new BarChartModel();
	 
	    ChartSeries grafico = new ChartSeries();
	    grafico.setLabel("Equipe");
	    for (Relatorio relatorio : this.service.profissionaisPorEquipe()) {
	    	grafico.set(relatorio.getNome(), relatorio.getQuantidade());
		}	 
	    model.addSeries(grafico);
	         
	    return model;
	}	     
	private void createProfissionalPorEquipe() {
	   profissionalPorEquipe = initProfissionalPorEquipe();
	         
	   profissionalPorEquipe.setTitle("Profissionais por equipe");
	   profissionalPorEquipe.setAnimate(true);
	   profissionalPorEquipe.setLegendPosition("ne");
	       
	   Axis xAxis = profissionalPorEquipe.getAxis(AxisType.X);
	   xAxis.setLabel("Equipes");
	         
	   Axis yAxis = profissionalPorEquipe.getAxis(AxisType.Y);
	   yAxis.setLabel("N° Profissionais");
	   yAxis.setMin(0);
	   yAxis.setMax(50);
	}
	
	public void itemSelect(ItemSelectEvent event){
		System.out.println("############ Evento: " + event.getItemIndex());
		System.out.println("############ Evento: " + relatorios.get(event.getItemIndex()).getNome() );
		
		
	}

}
