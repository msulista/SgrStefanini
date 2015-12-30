package com.stefanini.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Profissional;
import com.stefanini.entidade.Relatorio;
import com.stefanini.service.RelatorioService;

@ManagedBean
@ViewScoped
@URLMappings(mappings = {
		@URLMapping(id = "relatorioProfissional", pattern = "/profissional-por-equipe", viewId = "/pages/relatorio/relatorio-profissional-equipe.xhtml"),
		@URLMapping(id = "relatorioContratacao", pattern = "/contratacao-por-equipe", viewId = "/pages/relatorio/relatorio-contratacao-equipe.xhtml")
})
public class RelatorioManager {
	
	private Relatorio relatorio = new Relatorio();
	private RelatorioService service = new RelatorioService();
	private BarChartModel profissionalPorEquipe;
	private BarChartModel contratacaoPorEquipe;
	
	private List<Relatorio> relatorioProfissionalEquipe = new ArrayList<>();
	private List<Relatorio> relatorioContratacaoEquipe = new ArrayList<>();
	private List<Profissional> profissionais = new ArrayList<>();
	
	private String equipe = "";
	private int quantidadeTotal = 0;

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
	public BarChartModel getContratacaoPorEquipe() {
		return contratacaoPorEquipe;
	}
	public void setContratacaoPorEquipe(BarChartModel contratacaoPorEquipe) {
		this.contratacaoPorEquipe = contratacaoPorEquipe;
	}

//	public List<Relatorio> getRelatorios() {
//		relatorioProfissionalEquipe = this.service.profissionaisPorEquipe();
//		return relatorioProfissionalEquipe;
//	}
//	public void setRelatorios(List<Relatorio> relatorios) {
//		this.relatorioProfissionalEquipe = relatorios;
//	}
	public List<Profissional> getProfissionais() {
		return profissionais;
	}
	public void setProfissionais(List<Profissional> profissionais) {
		this.profissionais = profissionais;
	}
	
	public List<Relatorio> getRelatorioProfissionalEquipe() {
		relatorioProfissionalEquipe = this.service.profissionaisPorEquipe();
		return relatorioProfissionalEquipe;
	}

	public void setRelatorioProfissionalEquipe(List<Relatorio> relatorioProfissionalEquipe) {
		this.relatorioProfissionalEquipe = relatorioProfissionalEquipe;
	}

	public List<Relatorio> getRelatorioContratacaoEquipe() {
		relatorioContratacaoEquipe = this.service.contratacaoPorEquipe();
		return relatorioContratacaoEquipe;
	}

	public void setRelatorioContratacaoEquipe(List<Relatorio> relatorioContratacaoEquipe) {
		this.relatorioContratacaoEquipe = relatorioContratacaoEquipe;
	}
	
	
	//Graficos

	public String getEquipe() {
		return equipe;
	}

	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}

	public void criaGrafico(){
	   	createProfissionalPorEquipe();
	   	createCltXestagioPorEquipe();
	}
	
	//Profissional por Equipe
	private BarChartModel initProfissionalPorEquipe() {
	    BarChartModel model = new BarChartModel();
	 
	    ChartSeries grafico = new ChartSeries();
	    grafico.setLabel("Equipe");
	    for (Relatorio relatorio : getRelatorioProfissionalEquipe()) {
	    	grafico.set(relatorio.getNome01(), relatorio.getQuantidade01());
	    	quantidadeTotal = (int)(quantidadeTotal + relatorio.getQuantidade01());
		}	
	    grafico.set("Total Resultados", quantidadeTotal);
	    model.addSeries(grafico);	         
	    return model;
	}	     
	private void createProfissionalPorEquipe() {
	   profissionalPorEquipe = initProfissionalPorEquipe();
	         
	   profissionalPorEquipe.setTitle("Selecione a coluna da equipe desejada");
	   profissionalPorEquipe.setAnimate(true);
	   profissionalPorEquipe.setLegendPosition("ne");
	       
	   Axis xAxis = profissionalPorEquipe.getAxis(AxisType.X);
	   xAxis.setLabel("Equipes");
	         
	   Axis yAxis = profissionalPorEquipe.getAxis(AxisType.Y);
	   yAxis.setLabel("N° Profissionais");
	   yAxis.setMin(0);
	   
	   yAxis.setTickCount(quantidadeTotal + 4);
	   yAxis.setMax(quantidadeTotal + 3);
	}
	
	public void itemSelectProfissionalPorEquipe(ItemSelectEvent event){
		profissionais = this.service.listaDeProfissionaisPorEquipe(relatorioProfissionalEquipe.get(event.getItemIndex()).getNome01());
		equipe = profissionais.get(0).getEquipe().getNome();
		
	}
	
	//CLT x Estagiario por equipe
	
	private BarChartModel initCltXestagioPorEquipe() {
	    BarChartModel model = new BarChartModel();
	 
	    ChartSeries clt = new ChartSeries();
	    ChartSeries estagio = new ChartSeries();
	    clt.setLabel("CLT");
	    estagio.setLabel("Estágio");
	    
	    for (Relatorio relatorio : getRelatorioContratacaoEquipe()) {
	    	System.out.println("########### Nome01: " + relatorio.getNome01());
	    	System.out.println("########### Quantidade01: " + relatorio.getQuantidade01());
	    	System.out.println("########### Quantidade02: " + relatorio.getQuantidade02());
	    	System.out.println("##########");
	    	
	    	System.out.println("########### Nome02: " + relatorio.getNome02());	    	
	    	System.out.println("########### Nome03: " + relatorio.getNome03());
	    	System.out.println("########### Quantidade3: " + relatorio.getQuantidade03());
	    	clt.set(relatorio.getNome01(), relatorio.getQuantidade01());
	    	estagio.set(relatorio.getNome01(), relatorio.getQuantidade02());
	    	
	    	//quantidadeTotal = (int)(quantidadeTotal + relatorio.getQuantidade01());
		}	
	   // clt.set("Total Resultados", quantidadeTotal);
	   // estagio.set("Total Resultados", quantidadeTotal);	    
	    
	    model.addSeries(clt);	         
	    model.addSeries(estagio);	         
	    return model;
	}	     
	private void createCltXestagioPorEquipe() {
		contratacaoPorEquipe = initCltXestagioPorEquipe();
		
		contratacaoPorEquipe.setTitle("Selecione a coluna da forma de contratação desejada");
		contratacaoPorEquipe.setAnimate(true);
		contratacaoPorEquipe.setLegendPosition("ne");
	       
	    Axis xAxis = contratacaoPorEquipe.getAxis(AxisType.X);
	    xAxis.setLabel("Equipes");
	         
	    Axis yAxis = profissionalPorEquipe.getAxis(AxisType.Y);
	    yAxis.setLabel("N° Profissionais");
	    yAxis.setMin(0);
	   
	    yAxis.setTickCount(quantidadeTotal + 4);
	    yAxis.setMax(quantidadeTotal + 3);
	}
	
	public void itemSelectCltXestagioPorEquipe(ItemSelectEvent event){
		profissionais = this.service.listaDeProfissionaisPorEquipe(relatorioProfissionalEquipe.get(event.getItemIndex()).getNome01());
		equipe = profissionais.get(0).getEquipe().getNome();
		
	}
}
