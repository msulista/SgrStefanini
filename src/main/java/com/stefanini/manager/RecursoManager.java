package com.stefanini.manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
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
	
	
	
//	private List<Recurso> lista(){
//		return service.listarAtivos();
//	}
	
	@SuppressWarnings("unused")
	private String desativar(long id){
		service.desativar(id);
		return "";
	}
	
	public RecursoService getService() {
		return service;
	}

	public void setService(RecursoService service) {
		this.service = service;
	}

}
