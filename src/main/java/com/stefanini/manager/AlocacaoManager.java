package com.stefanini.manager;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Alocacao;
import com.stefanini.entidade.Celula;
import com.stefanini.entidade.Equipe;
import com.stefanini.entidade.Projeto;
import com.stefanini.entidade.Recurso;
import com.stefanini.service.AlocacaoService;
import com.stefanini.service.ProjetoService;

@ManagedBean
@ViewScoped
@URLMappings(mappings = {
		@URLMapping(id = "alocacao", pattern = "/alocacao", viewId = "/pages/alocacao/alocacao-listar.xhtml"),
		@URLMapping(id = "alocacao-incluir", pattern = "/incluir", viewId = "/pages/alocacao/alocacao-incluir.xhtml", parentId = "alocacao"),
		@URLMapping(id = "alocacao-editar", pattern = "/#{alocacaoManager.alocacao.id}/editar", viewId = "/pages/alocacao/alocacao-editar.xhtml", parentId = "alocacao")
})
public class AlocacaoManager {

	private Alocacao alocacao;
	private List<Projeto> listaProjetos;
	private List<Projeto> listaProjetosEAlocacoes;
	private AlocacaoService service = new AlocacaoService();
	private ProjetoService serviceProjeto = new ProjetoService();
	
	private List<Recurso> listaRecursosAtivos;
	
	private Equipe equipe ;
	private Celula celula ;
	
	public AlocacaoManager(){
		this.alocacao = new Alocacao();
		this.listaProjetosEAlocacoes = new ArrayList<>();
		this.listaRecursosAtivos = new ArrayList<>();
	}
/*	
	public List<Recurso> listaTodosRecursosAtivos(){
		return this.listaRecursosAtivos;
	}
	public List<Alocacao> listaAlocacoes(){
		return this.listaAlocacoes;
	}*/
		
	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}

	public Alocacao getAlocacao() {
		return alocacao;
	}

	public void setAlocacao(Alocacao alocacao) {
		this.alocacao = alocacao;
	}
	
	public List<Projeto> getListaProjetosEAlocacoes() {
		return listaProjetosEAlocacoes;
	}

	public void setListaProjetosEAlocacoes(List<Projeto> listaProjetosEAlocacoes) {
		this.listaProjetosEAlocacoes = listaProjetosEAlocacoes;
	}

	public List<Recurso> getListaRecursosAtivos() {
		return listaRecursosAtivos;
	}

	public void setListaRecursosAtivos(List<Recurso> listaRecursosAtivos) {
		this.listaRecursosAtivos = listaRecursosAtivos;
	}
	
	

	public List<Projeto> getListaProjetos() {
		return listaProjetos;
	}

	public void setListaProjetos(List<Projeto> listaProjetos) {
		this.listaProjetos = listaProjetos;
	}

	public String save(){
		if(service.save(alocacao)){
			return "pretty:alocacao";
		} else {
			return null;
		}
	}
	
	public String update(){
		if(service.update(alocacao)){
			return "pretty:alocacao";
		} else {
			return null;
		}
	}
	
	public List<Alocacao> listar(){
		return service.listarAtivos();
	}
	
	public String desativar(Long id){
		service.desativar(id);
		return "pretty:alocacao";
	}
			
	public void valueChangeEquipe(ValueChangeEvent event) {
	       equipe = (Equipe) event.getNewValue();
	       this.listaRecursosAtivos = service.listarTodosRecursos(this.equipe,this.celula);
	       this.listaProjetos = serviceProjeto.listarTodos(this.equipe, this.celula);
	       this.listaProjetosEAlocacoes = service.listarProjetosEAlocacoes( this.listaProjetos);
	       
	}

	public void valueChangeCelula(ValueChangeEvent event) {
	       celula = (Celula) event.getNewValue();
	       this.listaRecursosAtivos = service.listarTodosRecursos(this.equipe,this.celula);
	       this.listaProjetos = serviceProjeto.listarTodos(this.equipe, this.celula);
	       this.listaProjetosEAlocacoes = service.listarProjetosEAlocacoes(this.listaProjetos);
	}

	/*@URLActions(actions = { @URLAction(mappingId = "projeto-editar", onPostback = false) })
	public void load() throws IOException {
		alocacao = service.getProjetoParaEdicao(alocacao.getId());
	}*/
}
