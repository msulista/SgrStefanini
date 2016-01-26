package com.stefanini.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Celula;
import com.stefanini.entidade.Equipe;
import com.stefanini.entidade.Recurso;
import com.stefanini.service.RecursoService;

@ManagedBean
@ViewScoped
@URLMappings(mappings = {
		@URLMapping(id = "recursos", pattern="/alocacoes-recursos", viewId="/pages/alocacoes/alocacoes.xhtml")
})
public class RecursoManager implements Serializable{

	

	private static final long serialVersionUID = 1L;
	private Recurso recurso;
	private List<Recurso> recursos = new ArrayList<>();
	private RecursoService service = new RecursoService();
	private Equipe equipe ;
	private Celula celula ;
	
	public RecursoManager() {
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}

	public List<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}
	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}
	@SuppressWarnings("unused")
	private String save(){
		if(service.save(recurso)){
			return "";
		}else {
			return null;
		}
	}
	
	@SuppressWarnings("unused")
	private String update(){
		if(service.update(recurso)){
			return "";
		}else {
			return null;
		}
	}
	
	public List<Recurso> listarTudo(){
		return recursos;
	}
	
//	private List<Recurso> lista(){
//		return service.listarAtivos();
//	}
	
	@SuppressWarnings("unused")
	private String desativar(long id){
		service.desativar(id);
		return "";
	}
	
	public void valueChangeEquipe(ValueChangeEvent event) {
	       equipe = (Equipe) event.getNewValue();
	       recursos = service.listarTodos(this.equipe,this.celula);
	       listarTudo();
	    }

	public void valueChangeCelula(ValueChangeEvent event) {
	       celula = (Celula) event.getNewValue();
	       recursos = service.listarTodos(this.equipe,this.celula);
	       listarTudo();
	    }
	
	public RecursoService getService() {
		return service;
	}

	public void setService(RecursoService service) {
		this.service = service;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
}
