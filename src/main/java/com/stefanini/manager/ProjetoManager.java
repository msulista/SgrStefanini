package com.stefanini.manager;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Projeto;
import com.stefanini.service.ProjetoService;

@ManagedBean
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "projeto", pattern = "/projeto", viewId = "/pages/projeto/projeto-listar.xhtml"),
		@URLMapping(id = "projeto-incluir", pattern = "/incluir", viewId = "/pages/projeto/projeto-incluir.xhtml", parentId = "projeto"),
 })
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
			return "pretty:projeto";
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