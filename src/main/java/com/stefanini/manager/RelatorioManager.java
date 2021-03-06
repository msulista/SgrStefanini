package com.stefanini.manager;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LegendPlacement;
import org.primefaces.model.chart.LineChartSeries;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Celula;
import com.stefanini.entidade.Profissional;
import com.stefanini.entidade.Relatorio;
import com.stefanini.service.RelatorioService;

@ManagedBean
@ViewScoped
@URLMappings(mappings = {
		@URLMapping(id = "relatoriosLinks", pattern = "/relatorios-links", viewId = "/pages/relatorio/relatorio-listar.xhtml"),
		@URLMapping(id = "relatorioProfissional", pattern = "/profissional-por-equipe", viewId = "/pages/relatorio/relatorio-profissional-equipe.xhtml"),
		@URLMapping(id = "relatorioContratacao", pattern = "/contratacao-por-equipe", viewId = "/pages/relatorio/relatorio-contratacao-equipe.xhtml"),
		@URLMapping(id = "relatorioValorEquipe", pattern = "/valor-por-equipe", viewId = "/pages/relatorio/relatorio-valor-equipe.xhtml"),
		@URLMapping(id = "relatorioPerfilEquipe", pattern = "/perfil-por-equipe", viewId = "/pages/relatorio/relatorio-perfil-equipe.xhtml"),
		@URLMapping(id = "relatorioValorCelula", pattern = "/valor-por-celula", viewId = "/pages/relatorio/relatorio-valor-celula.xhtml"),
		@URLMapping(id = "relatorioContratacaoCelula", pattern = "/contratacao-por-celula", viewId = "/pages/relatorio/relatorio-contratacao-celula.xhtml"),
		@URLMapping(id = "relatorioPerfilCelula", pattern = "/perfil-por-celula", viewId = "/pages/relatorio/relatorio-perfil-celula.xhtml"),
		
})
public class RelatorioManager {
	

	private Celula celula;
	private Relatorio relatorio = new Relatorio();
	private RelatorioService service = new RelatorioService();
	private BarChartModel profissionalPorEquipe = new BarChartModel();
	private BarChartModel contratacaoPorEquipe = new BarChartModel();
	private BarChartModel valorPorEquipe = new BarChartModel();
	private BarChartModel valorPorCelula = new BarChartModel();
	private BarChartModel perfilPorEquipe = new BarChartModel();
	private BarChartModel contratacaoPorCelula = new BarChartModel();
	private BarChartModel perfilPorCelula = new BarChartModel();
	
	
	private List<Relatorio> relatorioProfissionalEquipe = new ArrayList<>();
	private List<Relatorio> relatorioContratacaoEquipe = new ArrayList<>();
	private List<Relatorio> relatorioContratacaoCelula = new ArrayList<>();
	private List<Relatorio> relatorioValorEquipe = new ArrayList<>();
	private List<Relatorio> relatorioValorCelula = new ArrayList<>();
	private List<Relatorio> relatorioPerfilPorEquipe = new ArrayList<>();
	private List<Profissional> profissionais = new ArrayList<>();
	private List<Relatorio> relatorioPerfilPorCelula = new ArrayList<>();
	
	
	private String equipe = "";
	private int quantidadeTotal = 0;
	private int quantidadeTotal2 = 0;
	private int quantidadeTotal3 = 0;
	private int quantidadeTotal4 = 0;
	private double porcentagem = 0;
	private double valorTotal = 0;
	
	private double valorMeta;

	public RelatorioManager() {
	}	
	
	@PostConstruct
	public void init() {
	    criaGrafico();
	}	
	
	public double getValorMeta() {
		return valorMeta;
	}

	public void setValorMeta(double valorMeta) {
		this.valorMeta = valorMeta;
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
		relatorioProfissionalEquipe = this.service.profissionaisPorEquipe(this.celula);
		return relatorioProfissionalEquipe;
	}

	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}

	public void setRelatorioProfissionalEquipe(List<Relatorio> relatorioProfissionalEquipe) {
		this.relatorioProfissionalEquipe = relatorioProfissionalEquipe;
	}

	public List<Relatorio> getRelatorioContratacaoEquipe() {
		relatorioContratacaoEquipe = this.service.contratacaoPorEquipe(this.celula);
		return relatorioContratacaoEquipe;
	}
	
	public void setRelatorioContratacaoEquipe(List<Relatorio> relatorioContratacaoEquipe) {
		this.relatorioContratacaoEquipe = relatorioContratacaoEquipe;
	}
	
	public List<Relatorio> getRelatorioValorEquipe() {
		relatorioValorEquipe = this.service.valorPorEquipe(this.celula);
		return relatorioValorEquipe;
	}

	public void setRelatorioValorEquipe(List<Relatorio> relatorioValorEquipe) {
		this.relatorioValorEquipe = relatorioValorEquipe;
	}
	
	public List<Relatorio> getRelatorioPerfilPorEquipe() {
		relatorioPerfilPorEquipe = this.service.perfilPorEquipe(this.celula);
		return relatorioPerfilPorEquipe;
	}

	public void setRelatorioPerfilPorEquipe(List<Relatorio> relatorioPerfilPorEquipe) {
		
		this.relatorioPerfilPorEquipe = relatorioPerfilPorEquipe;
	}
	
	
	public List<Relatorio> getRelatorioValorCelula() {
		relatorioValorCelula = this.service.valorPorCelula(this.celula);
		return relatorioValorCelula;
	}

	public void setRelatorioValorCelula(List<Relatorio> relatorioValorCelula) {
		
		this.relatorioValorCelula = relatorioValorCelula;
	}

	public BarChartModel getPerfilPorEquipe() {
		return perfilPorEquipe;
	}

	public void setPerfilPorEquipe(BarChartModel perfilPorEquipe) {
		this.perfilPorEquipe = perfilPorEquipe;
	}
	
	public List<Relatorio> getRelatorioContratacaoCelula() {
		relatorioContratacaoCelula = this.service.contratacaoPorCelula(this.celula);
		return relatorioContratacaoCelula;
	}

	public void setRelatorioContratacaoCelula(List<Relatorio> relatorioContratacaoCelula) {
		this.relatorioContratacaoCelula = relatorioContratacaoCelula;
	}

	public BarChartModel getValorPorCelula() {
		return valorPorCelula;
	}

	public void setValorPorCelula(BarChartModel valorPorCelula) {
		this.valorPorCelula = valorPorCelula;
	}

	public String getEquipe() {
		return equipe;
	}

	public void setEquipe(String equipe) {
		this.equipe = equipe;
	}
	
	public BarChartModel getContratacaoPorCelula() {
		return contratacaoPorCelula;
	}

	public void setContratacaoPorCelula(BarChartModel contratacaoPorCelula) {
		this.contratacaoPorCelula = contratacaoPorCelula;
	}
	
	public BarChartModel getPerfilPorCelula() {
		return perfilPorCelula;
	}

	public void setPerfilPorCelula(BarChartModel perfilPorCelula) {
		this.perfilPorCelula = perfilPorCelula;
	}

	public List<Relatorio> getRelatorioPerfilPorCelula() {
		relatorioPerfilPorCelula = this.service.perfilPorCelula(this.celula);
		return relatorioPerfilPorCelula;
	}

	public void setRelatorioPerfilPorCelula(List<Relatorio> relatorioPerfilPorCelula) {
		this.relatorioPerfilPorCelula = relatorioPerfilPorCelula;
	}

	
	//Graficos

	
	
	public void criaGrafico(){
		createValorPorCelula();
	   	createProfissionalPorEquipe();
	   	createCltXestagioPorEquipe();
	   	createValorPorEquipe();
	   	createPerfilPorEquipe();
	   	createCltXestagioPorCelula();
	   	createPerfilPorCelula();
	}
	
	//Profissional por Equipe
	private BarChartModel initProfissionalPorEquipe() {
		quantidadeTotal =0;
		BarChartModel model = new BarChartModel();
	    model.setLegendPosition("ne");
	    
    	model.setLegendPlacement(LegendPlacement.OUTSIDEGRID); 
    	model.setShowPointLabels(true);
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
	   
	       
	   Axis xAxis = profissionalPorEquipe.getAxis(AxisType.X);
	   xAxis.setLabel("Equipes");
	         
	   Axis yAxis = profissionalPorEquipe.getAxis(AxisType.Y);
	   yAxis.setLabel("N� Profissionais");
	   yAxis.setMin(0);
	   
	   yAxis.setTickCount(quantidadeTotal + 4);
	   yAxis.setMax(quantidadeTotal + 3);
	}
	
	public void itemSelectProfissionalPorEquipe(ItemSelectEvent event){
		if(!(event.getItemIndex()>relatorioProfissionalEquipe.size()-1)){
		profissionais = this.service.listaDeProfissionaisPorEquipe(this.celula.getId(),(relatorioProfissionalEquipe.get(event.getItemIndex()).getNome01()));
		equipe = profissionais.get(0).getEquipe().getNome();
		}else{
			profissionais = this.service.listaDeProfissionaisPorEquipe(this.celula.getId(),"Valor Total");
			equipe = profissionais.get(0).getEquipe().getNome();
		}
		
	}

	//CLT x Estagiario por equipe
	
	private BarChartModel initCltXestagioPorEquipe() {
		quantidadeTotal = 0;
		quantidadeTotal2 =0;
	    BarChartModel model = new BarChartModel();
	    model.setLegendPosition("ne");
    	model.setLegendPlacement(LegendPlacement.OUTSIDEGRID); 
    	model.setShowPointLabels(true);
    	model.setExtender("limpaLabel");
	    ChartSeries clt = new ChartSeries();
	    ChartSeries estagio = new ChartSeries();
	    
	    clt.setLabel("CLT");
	    estagio.setLabel("Est�gio");
	    
	    for (Relatorio relatorio : getRelatorioContratacaoEquipe()) {
	    	
	    	clt.set(relatorio.getNome01(), relatorio.getQuantidade01());
	    	estagio.set(relatorio.getNome01(), relatorio.getQuantidade02());
	    	quantidadeTotal = (int)(quantidadeTotal += relatorio.getQuantidade01());
	    	quantidadeTotal2 = (int)(quantidadeTotal2+= relatorio.getQuantidade02());
		}	
	  
	    clt.set("Total Resultados", quantidadeTotal);
	    estagio.set("Total Resultados" ,quantidadeTotal2);
	    model.addSeries(clt);	         
	    model.addSeries(estagio);	         
	    return model;
	}	     
	private void createCltXestagioPorEquipe() {
		quantidadeTotal = 0;
		quantidadeTotal2 =0;
		contratacaoPorEquipe = initCltXestagioPorEquipe();
		
		contratacaoPorEquipe.setTitle("Selecione a coluna da forma de contrata��o desejada");
		contratacaoPorEquipe.setAnimate(true);
		
	       
	    Axis xAxis = contratacaoPorEquipe.getAxis(AxisType.X);
	    xAxis.setLabel("Equipes");
	         
	    Axis yAxis = contratacaoPorEquipe.getAxis(AxisType.Y);
	    yAxis.setLabel("N� Profissionais");
	    yAxis.setMin(0);
	   
	    yAxis.setTickCount(quantidadeTotal + 4);
	    yAxis.setMax(quantidadeTotal + 3);
	}
	
	public void itemSelectCltXestagioPorEquipe(ItemSelectEvent event){
		if(!(event.getItemIndex()>relatorioContratacaoEquipe.size()-1)){
		profissionais = this.service.listaDeCLTXEstagio(this.celula.getId(),relatorioContratacaoEquipe.get(event.getItemIndex()).getNome01(), event.getSeriesIndex());
		equipe = profissionais.get(0).getEquipe().getNome();
		}else{
			profissionais = this.service.listaDeCLTXEstagio(this.celula.getId(),"Total Resultados", event.getSeriesIndex());
			equipe = profissionais.get(0).getEquipe().getNome();
		}
	}
	
	// Valor por Equipe
	public void atualizaGrafico(){
		createValorPorEquipe();
		createValorPorCelula();
	}
	private BarChartModel initValorPorEquipe() {
		valorTotal =0;
	    BarChartModel model = new BarChartModel();
	    model.setExtender("decimalConverter");
	    ChartSeries grafico = new ChartSeries();
	    LineChartSeries meta = new LineChartSeries();
	    model.setLegendPosition("ne");
    	model.setLegendPlacement(LegendPlacement.OUTSIDEGRID); 
    	model.setShowPointLabels(true);
    	meta.setShowMarker(false);
	    grafico.setLabel("Equipe");	    
	    meta.setLabel("Meta");
	    for (Relatorio relatorio : getRelatorioValorEquipe()) {
	    	grafico.set(relatorio.getNome01(), relatorio.getValorMedio().doubleValue());
	    	meta.set(relatorio.getNome01(), this.valorMeta);
	    	valorTotal = (double)(valorTotal += relatorio.getValorMedio().doubleValue());
		}	
	    grafico.set("Valor M�dio Total", valorTotal / getRelatorioValorEquipe().size());
	    model.addSeries(grafico);
	    model.addSeries(meta);
	    return model;
	}
	
	private void createValorPorEquipe() {
		valorPorEquipe = initValorPorEquipe();
		valorPorEquipe.setTitle("Selecione a coluna para listar os profissionais");
		valorPorEquipe.setAnimate(true);
	    Axis xAxis = valorPorEquipe.getAxis(AxisType.X);
	    xAxis.setLabel("Equipes");
	    Axis yAxis = valorPorEquipe.getAxis(AxisType.Y);
	    yAxis.setLabel("Valor R$");
	    yAxis.setMin(0);
	    yAxis.setTickCount(11);
	    yAxis.setMax(100);
	}
	
	public void itemSelectValorPorEquipe(ItemSelectEvent event){

		if(!(event.getItemIndex()>relatorioValorEquipe.size()-1)){
		profissionais = this.service.listaDeProfissionaisPorEquipe(this.celula.getId(),relatorioValorEquipe.get(event.getItemIndex()).getNome01());
		equipe = profissionais.get(0).getEquipe().getNome();
		}else{
			profissionais = this.service.listaDeProfissionaisPorEquipe(this.celula.getId(),"Valor Total");
			equipe = profissionais.get(0).getEquipe().getNome();
		}
		
	}
	
	//Perfil por equipe
	private BarChartModel initPerfilPorEquipe() {
		quantidadeTotal =0;
		quantidadeTotal2 =0;
		quantidadeTotal3 =0;
		quantidadeTotal4 = 0;
	    BarChartModel model = new BarChartModel(); 
    	model.setLegendPosition("ne");
    	model.setLegendPlacement(LegendPlacement.OUTSIDEGRID); 
    	model.setExtender("decimalConverter");
	    ChartSeries junior = new ChartSeries();
	    ChartSeries pleno = new ChartSeries();
	    ChartSeries senior = new ChartSeries();
	    ChartSeries estagio = new ChartSeries();
	    model.setShowPointLabels(true);
	    
	    estagio.setLabel("Est�gio");
	    junior.setLabel("Junior");
	    pleno.setLabel("Pleno");
	    senior.setLabel("S�nior");
	    
	    for (Relatorio relatorio : getRelatorioPerfilPorEquipe()) {
	    	estagio.set(relatorio.getNome01(), relatorio.getQuantidade01());
	    	junior.set(relatorio.getNome01(), relatorio.getQuantidade02());
	    	pleno.set(relatorio.getNome01(), relatorio.getQuantidade03());
	    	senior.set(relatorio.getNome01(), relatorio.getQuantidade04());
	    	quantidadeTotal = (int)(quantidadeTotal += relatorio.getQuantidade01());
	    	quantidadeTotal2 = (int)(quantidadeTotal2+= relatorio.getQuantidade02());
	    	quantidadeTotal3 = (int)(quantidadeTotal3 += relatorio.getQuantidade03());
	    	quantidadeTotal4 = (int)(quantidadeTotal4 += relatorio.getQuantidade04());
		}	
	
	    estagio.set("Total Resultados", quantidadeTotal);
	    junior.set("Total Resultados", quantidadeTotal2);
	    pleno.set("Total Resultados", quantidadeTotal3);
	    senior.set("Total Resultados", quantidadeTotal4);
	    model.addSeries(estagio);
	    model.addSeries(junior);	         
	    model.addSeries(pleno);	   
	    model.addSeries(senior);	  
	    return model;
	}	     
	private void createPerfilPorEquipe() {
		perfilPorEquipe = initPerfilPorEquipe();
		
		perfilPorEquipe.setTitle("Selecione a coluna da forma de contrata��o desejada");
		perfilPorEquipe.setAnimate(true);
		
	       
	    Axis xAxis = perfilPorEquipe.getAxis(AxisType.X);
	    xAxis.setLabel("Equipes");
	         
	    Axis yAxis = perfilPorEquipe.getAxis(AxisType.Y);
	    yAxis.setLabel("N� Profissionais");
	    yAxis.setMin(0);
	   
	    yAxis.setTickCount(quantidadeTotal + 4);
	    yAxis.setMax(quantidadeTotal+30);
	}
	
	public void itemSelectPerfilPorEquipe(ItemSelectEvent event){
		
		if(!(event.getItemIndex()>relatorioPerfilPorEquipe.size()-1)){
		profissionais = this.service.listaDePerfilPorEquipe(this.celula.getId(),relatorioPerfilPorEquipe.get(event.getItemIndex()).getNome01(), event.getSeriesIndex());
		equipe = profissionais.get(0).getEquipe().getNome();
		}else{
			profissionais = this.service.listaDePerfilPorEquipe(this.celula.getId(),"Total Resultados", event.getSeriesIndex());
			equipe = profissionais.get(0).getEquipe().getNome();
		}
		
	}
	
	//Valor medio por celula
	private BarChartModel initValorPorCelula() {
	    valorTotal =0;
	    BarChartModel model = new BarChartModel();
	    model.setExtender("decimalConverter");
	    ChartSeries grafico = new ChartSeries();
	    LineChartSeries metaCelula = new LineChartSeries();
	    model.setLegendPosition("ne");
    	model.setLegendPlacement(LegendPlacement.OUTSIDEGRID); 
    	model.setShowPointLabels(true);
    	metaCelula.setShowMarker(true);
    	
	    grafico.setLabel("Celula");
    	metaCelula.setLabel("Meta");
	    for (Relatorio relatorio : getRelatorioValorCelula()) {
	    	grafico.set(relatorio.getNome01(), relatorio.getValorMedio().doubleValue());
	    	metaCelula.set(relatorio.getNome01(), this.valorMeta);
	    	valorTotal = (double)(valorTotal + relatorio.getValorMedio().doubleValue());
	    	
		}	
	    if(getRelatorioValorCelula().size()<1){
    		grafico.set("Total", valorTotal);
    	}
	    model.addSeries(grafico);
	    model.addSeries(metaCelula);
	    return model;
	}
	
	private void createValorPorCelula() {
		this.valorPorCelula = initValorPorCelula();
		
		this.valorPorCelula.setTitle("Selecione a coluna para listar os profissionais");
		this.valorPorCelula.setAnimate(true);
		
	       
	    Axis xAxis = valorPorCelula.getAxis(AxisType.X);
	    xAxis.setLabel("Equipes");
	         
	    Axis yAxis = valorPorCelula.getAxis(AxisType.Y);
	    yAxis.setLabel("Valor R$");
	    yAxis.setMin(0);
	   
	    yAxis.setTickCount(11);
	    yAxis.setMax(100);
	}
	
	public void itemSelectValorPorCelula(ItemSelectEvent event){
		profissionais = this.service.listaDeProfissionaisPorCelula(this.celula.getId(),relatorioValorCelula.get(event.getItemIndex()).getNome01());
		equipe = profissionais.get(0).getCelula().getNome();
		
	}
	
	//CLT X ESTAGIO POR CELULA
	private BarChartModel initCltXestagioPorCelula() {
		quantidadeTotal = 0;
		quantidadeTotal2 =0;
	    BarChartModel model = new BarChartModel();
	    model.setLegendPosition("ne");
    	model.setLegendPlacement(LegendPlacement.OUTSIDEGRID); 
    	model.setShowPointLabels(true);
	    ChartSeries clt = new ChartSeries();
	    ChartSeries estagio = new ChartSeries();
	    ChartSeries total = new ChartSeries();
	    model.setExtender("limpaLabel");
	    
	    clt.setLabel("CLT");
	    estagio.setLabel("Est�gio");
	    total.setLabel("Total");
	    for (Relatorio relatorio : getRelatorioContratacaoCelula()) {
	    	
	    	clt.set(relatorio.getNome01(), relatorio.getQuantidade01());
	    	estagio.set(relatorio.getNome01(), relatorio.getQuantidade02());
	    	quantidadeTotal = (int)(quantidadeTotal += relatorio.getQuantidade01()+relatorio.getQuantidade02());
		}	
	    if(getRelatorioContratacaoCelula().size()<1){
    		clt.set("Total", 0);
    	}
	    total.set("Total Resultados", quantidadeTotal);
	    model.addSeries(clt);	         
	    model.addSeries(estagio);	
	    model.addSeries(total);
	    return model;
	}	     
	private void createCltXestagioPorCelula() {
		quantidadeTotal = 0;
		quantidadeTotal2 =0;
		contratacaoPorCelula = initCltXestagioPorCelula();
		
		contratacaoPorCelula.setTitle("Selecione a coluna da forma de contrata��o desejada");
		contratacaoPorCelula.setAnimate(true);
		
	       
	    Axis xAxis = contratacaoPorCelula.getAxis(AxisType.X);
	    xAxis.setLabel("C�lulas");
	         
	    Axis yAxis = contratacaoPorCelula.getAxis(AxisType.Y);
	    yAxis.setLabel("N� Profissionais");
	    yAxis.setMin(0);
	   
	    yAxis.setTickCount(quantidadeTotal + 4);
	    yAxis.setMax(quantidadeTotal + 3);
	}
	
	public void itemSelectCltXestagioPorCelula(ItemSelectEvent event){
		profissionais = this.service.listaDeCLTXEstagioCelula(this.celula.getId(), event.getSeriesIndex());
		equipe = profissionais.get(0).getEquipe().getNome();
	}
	
	//PERFIL POR CELULA
	private BarChartModel initPerfilPorCelula() {
	
	    BarChartModel model = new BarChartModel();
	    model.setExtender("decimalConverter");
	    model.setLegendPosition("ne");
    	model.setLegendPlacement(LegendPlacement.OUTSIDEGRID); 
    	model.setShowPointLabels(true);
	    ChartSeries junior = new ChartSeries();
	    ChartSeries pleno = new ChartSeries();
	    ChartSeries senior = new ChartSeries();
	    ChartSeries total = new ChartSeries();
	    ChartSeries estagio = new ChartSeries();
	    estagio.setLabel("Est�gio");
	    junior.setLabel("Junior");
	    pleno.setLabel("Pleno");
	    senior.setLabel("S�nior");
	    total.setLabel("Total Resultado");
	    for (Relatorio relatorio : getRelatorioPerfilPorCelula()) {
	    	porcentagem = (double) ((relatorio.getQuantidade01()+relatorio.getQuantidade02())+relatorio.getQuantidade03()+relatorio.getQuantidade04());
	    	System.out.println("ckdhfkasjka   "+porcentagem);
	    	estagio.set(relatorio.getNome01(), (relatorio.getQuantidade01()*100)/porcentagem);
	    	junior.set(relatorio.getNome01(),(relatorio.getQuantidade02()*100)/porcentagem);
	    	System.out.println("ckdhfkasjka   "+(relatorio.getQuantidade03()*100)/porcentagem);
	    	pleno.set(relatorio.getNome01(), (relatorio.getQuantidade03()*100)/porcentagem);
	    	senior.set(relatorio.getNome01(), (relatorio.getQuantidade04()*100)/porcentagem);
	    	total.set(relatorio.getNome01(), (porcentagem*100)/porcentagem);
	    	porcentagem = 0;
		}	
	
	    if(getRelatorioPerfilPorCelula().size()<1){
    		estagio.set("Total", 0);
    	}
	   
	   model.addSeries(estagio);
	    model.addSeries(junior);	         
	    model.addSeries(pleno);	   
	    model.addSeries(senior);	
	    model.addSeries(total);
	    return model;
	}	     
	private void createPerfilPorCelula() {
		
		perfilPorCelula = initPerfilPorCelula();
		
		perfilPorCelula.setTitle("Selecione a coluna da forma de contrata��o desejada");
		perfilPorCelula.setAnimate(true);
		
	       
	    Axis xAxis = perfilPorCelula.getAxis(AxisType.X);
	    xAxis.setLabel("Celula");
	         
	    Axis yAxis = perfilPorCelula.getAxis(AxisType.Y);
	    yAxis.setLabel("% Profissionais");
	    yAxis.setMin(0);
	   
	    yAxis.setTickCount(11);
	    yAxis.setMax(120);
	}
	
	public void itemSelectPerfilPorCelula(ItemSelectEvent event){
		profissionais = this.service.listaDePerfilPorCelula(this.celula.getId(), event.getSeriesIndex());
		equipe = profissionais.get(0).getEquipe().getNome();
		
	}
	
	// ValueChangeEvent dos gr�ficos
	
	public void stateChangeListenerProfissionalPorEquipe(ValueChangeEvent event) {
	       celula = (Celula) event.getNewValue();
	       profissionais = new ArrayList<>();
	     createProfissionalPorEquipe();
	    }
		
		public void stateChangeListenerContratacaoPorEquipe(ValueChangeEvent event) {
		       celula = (Celula) event.getNewValue();
		       profissionais = new ArrayList<>();
		     createCltXestagioPorEquipe();
		    }
		
		public void stateChangeListenerValorMedioPorEquipe(ValueChangeEvent event) {
		       celula = (Celula) event.getNewValue();
		       profissionais = new ArrayList<>();
		     createValorPorEquipe();
		    }
		
		public void stateChangeListenerPerfilPorEquipe(ValueChangeEvent event) {
		       celula = (Celula) event.getNewValue();
		       profissionais = new ArrayList<>();
		     createPerfilPorEquipe();
		    }
		
		public void stateChangeListenerValorPorCelula(ValueChangeEvent event) {
		       celula = (Celula) event.getNewValue();
		       profissionais = new ArrayList<>();
		     createValorPorCelula();
		    }
		
		public void stateChangeListenerContratcaoPorCelula(ValueChangeEvent event) {
		       celula = (Celula) event.getNewValue();
		       profissionais = new ArrayList<>();
		     createCltXestagioPorCelula();
		    }
		
		public void stateChangeListenerPerfilPorCelula(ValueChangeEvent event) {
		       celula = (Celula) event.getNewValue();
		       profissionais = new ArrayList<>();
		     createPerfilPorCelula();
		    }
}
