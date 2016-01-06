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
		@URLMapping(id = "relatorioContratacao", pattern = "/contratacao-por-equipe", viewId = "/pages/relatorio/relatorio-contratacao-equipe.xhtml"),
		@URLMapping(id = "relatorioValorEquipe", pattern = "/valor-por-equipe", viewId = "/pages/relatorio/relatorio-valor-equipe.xhtml"),
		@URLMapping(id = "relatorioPerfilEquipe", pattern = "/perfil-por-equipe", viewId = "/pages/relatorio/relatorio-perfil-equipe.xhtml")

})
public class RelatorioManager {
	
	private Relatorio relatorio = new Relatorio();
	private RelatorioService service = new RelatorioService();
	private BarChartModel profissionalPorEquipe;
	private BarChartModel contratacaoPorEquipe;
	private BarChartModel valorPorEquipe;
	private BarChartModel perfilPorEquipe;
	
	private List<Relatorio> relatorioProfissionalEquipe = new ArrayList<>();
	private List<Relatorio> relatorioContratacaoEquipe = new ArrayList<>();
	private List<Relatorio> relatorioValorEquipe = new ArrayList<>();
	private List<Relatorio> relatorioPerfilPorEquipe = new ArrayList<>();
	private List<Profissional> profissionais = new ArrayList<>();
	
	private String equipe = "";
	private int quantidadeTotal = 0;
	private double valorTotal = 0;

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
		
	public BarChartModel getValorPorEquipe() {
		return valorPorEquipe;
	}

	public void setValorPorEquipe(BarChartModel valorPorEquipe) {
		this.valorPorEquipe = valorPorEquipe;
	}
	
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
	
	public List<Relatorio> getRelatorioValorEquipe() {
		relatorioValorEquipe = this.service.valorPorEquipe();
		return relatorioValorEquipe;
	}

	public void setRelatorioValorEquipe(List<Relatorio> relatorioValorEquipe) {
		this.relatorioValorEquipe = relatorioValorEquipe;
	}
	
	public List<Relatorio> getRelatorioPerfilPorEquipe() {
		relatorioPerfilPorEquipe = this.service.perfilPorEquipe();
		return relatorioPerfilPorEquipe;
	}

	public void setRelatorioPerfilPorEquipe(List<Relatorio> relatorioPerfilPorEquipe) {
		
		this.relatorioPerfilPorEquipe = relatorioPerfilPorEquipe;
	}
	public BarChartModel getPerfilPorEquipe() {
		return perfilPorEquipe;
	}

	public void setPerfilPorEquipe(BarChartModel perfilPorEquipe) {
		this.perfilPorEquipe = perfilPorEquipe;
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
	   	createValorPorEquipe();
	   	createPerfilPorEquipe();
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
	    	
	    	clt.set(relatorio.getNome01(), relatorio.getQuantidade01());
	    	estagio.set(relatorio.getNome01(), relatorio.getQuantidade02());
	    	
		}	
	  
	    
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
		profissionais = this.service.listaDeCLTXEstagio(relatorioContratacaoEquipe.get(event.getItemIndex()).getNome01(), event.getSeriesIndex());
		equipe = profissionais.get(0).getEquipe().getNome();
		
	}
	
	// Valor por Equipe
	
	private BarChartModel initValorPorEquipe() {
	    BarChartModel model = new BarChartModel();
	    model.setExtender("decimalConverter");
	    ChartSeries grafico = new ChartSeries();
	    grafico.setLabel("Equipe");
	    for (Relatorio relatorio : getRelatorioValorEquipe()) {
	    	grafico.set(relatorio.getNome01(), relatorio.getValorMedio().doubleValue());
	    	valorTotal = (double)(valorTotal + relatorio.getValorMedio().doubleValue());
		}	
	    grafico.set("Valor Médio Total", valorTotal / getRelatorioValorEquipe().size());
	    model.addSeries(grafico);	
	    
	    return model;
	}
	
	private void createValorPorEquipe() {
		valorPorEquipe = initValorPorEquipe();
		
		valorPorEquipe.setTitle("Selecione a coluna para listar os profissionais");
		valorPorEquipe.setAnimate(true);
		valorPorEquipe.setLegendPosition("ne");
	       
	    Axis xAxis = valorPorEquipe.getAxis(AxisType.X);
	    xAxis.setLabel("Equipes");
	         
	    Axis yAxis = valorPorEquipe.getAxis(AxisType.Y);
	    yAxis.setLabel("Valor R$");
	    yAxis.setMin(0);
	   
	    yAxis.setTickCount(11);
	    yAxis.setMax(100);
	}
	
	public void itemSelectValorPorEquipe(ItemSelectEvent event){
		profissionais = this.service.listaDeProfissionaisPorEquipe(relatorioValorEquipe.get(event.getItemIndex()).getNome01());
		equipe = profissionais.get(0).getEquipe().getNome();
		
	}
	
	//Perfil por equipe
	private BarChartModel initPerfilPorEquipe() {
	    BarChartModel model = new BarChartModel();
	 
	    ChartSeries junior = new ChartSeries();
	    ChartSeries pleno = new ChartSeries();
	    ChartSeries senior = new ChartSeries();
	    junior.setLabel("JUNIOR");
	    pleno.setLabel("PLENO");
	    senior.setLabel("SENIOR");
	    
	    for (Relatorio relatorio : getRelatorioPerfilPorEquipe()) {
	    	
	    	junior.set(relatorio.getNome01(), relatorio.getQuantidade01());
	    	pleno.set(relatorio.getNome01(), relatorio.getQuantidade02());
	    	senior.set(relatorio.getNome01(), relatorio.getQuantidade03());
	    	
		}	
	
	    
	    model.addSeries(junior);	         
	    model.addSeries(pleno);	   
	    model.addSeries(senior);	  
	    return model;
	}	     
	private void createPerfilPorEquipe() {
		perfilPorEquipe = initPerfilPorEquipe();
		
		perfilPorEquipe.setTitle("Selecione a coluna da forma de contratação desejada");
		perfilPorEquipe.setAnimate(true);
		perfilPorEquipe.setLegendPosition("ne");
	       
	    Axis xAxis = getPerfilPorEquipe().getAxis(AxisType.X);
	    xAxis.setLabel("Equipes");
	         
	    Axis yAxis = profissionalPorEquipe.getAxis(AxisType.Y);
	    yAxis.setLabel("N° Profissionais");
	    yAxis.setMin(0);
	   
	    yAxis.setTickCount(quantidadeTotal + 4);
	    yAxis.setMax(quantidadeTotal + 3);
	}
	
	public void itemSelectPerfilPorEquipe(ItemSelectEvent event){
		profissionais = this.service.listaDePerfilPorEquipe(relatorioPerfilPorEquipe.get(event.getItemIndex()).getNome01(), event.getSeriesIndex());
		equipe = profissionais.get(0).getEquipe().getNome();
		
	}
	
}
