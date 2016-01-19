package com.stefanini.manager;

import java.util.ArrayList;
import java.util.List;

import com.stefanini.entidade.Projeto;
import com.stefanini.service.ProjetoService;

public class ProjetoManager {
	
	private Projeto projeto;
	private List<Projeto> projetos;
	private ProjetoService service = new ProjetoService();
	
	public ProjetoManager(){
		this.projeto = new Projeto();
		this.projetos = new ArrayList<>();		
	}
	
	public Projeto getProjeto(){
		return projeto;
	}
	
	public void setProjeto(Projeto projeto){
		this.projeto = projeto;
	}
	
	public List<Projeto> getProjetos(){
		return projetos;
	}
	
	public void setProjetos(List<Projeto> projetos){
		this.projetos = projetos;
	}
	
	public String save(){
		if(service.save(projeto)){
			return "";
		} else {
			return null;
		}
	}
	
	public String update(){
		if(service.update(projeto)){
			return "";
		} else {
			return null;
		}
	}
	
	public List<Projeto> listarTodos(){
		return service.listarTodos();
	}
	
	public List<Projeto> listar(){
		return service.listarAtivos();
	}
	
	public String desativar(Long id){
		service.desativar(id);
		return "";
	}

}