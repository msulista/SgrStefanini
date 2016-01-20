package com.stefanini.manager;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.stefanini.entidade.Recurso;
import com.stefanini.service.RecursoService;

@ManagedBean
@ViewScoped
public class RecursoManager {

	private Recurso recurso;
	private List<Recurso> recursos;
	private RecursoService service = new RecursoService();
	
	public RecursoManager() {
		this.recurso = new Recurso();
		this.recursos = new ArrayList<>();
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
	
	@SuppressWarnings("unused")
	private List<Recurso> listarTudo(){
		return service.listarTodos();
	}
	
//	private List<Recurso> lista(){
//		return service.listarAtivos();
//	}
	
	@SuppressWarnings("unused")
	private String desativar(long id){
		service.desativar(id);
		return "";
	}
	
}
