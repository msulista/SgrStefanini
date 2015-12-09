package com.stefanini.manager;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Equipe;
import com.stefanini.service.EquipeService;

@ManagedBean
@URLMappings(mappings = {
		@URLMapping(id = "equipe", pattern = "/equipe", viewId = "/pages/equipe/equipe-listar.xhtml"),
		@URLMapping(id = "equipe-incluir", pattern = "/incluir", viewId = "/pages/equipe/equipe-incluir.xhtml", parentId = "equipe"),
		@URLMapping(id = "equipe-editar", pattern = "/#{equipeManager.equipe.idEquipe}/editar", viewId = "/pages/equipe/equipe-editar.xhtml",parentId="equipe")
})
public class EquipeManager {
	
	private Equipe equipe;
	private EquipeService service;
	
	public EquipeManager(){
		
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public EquipeService getService() {
		return service;
	}

	public void setService(EquipeService service) {
		this.service = service;
	}
	
	public String save(){
		service.save(equipe);
		return "pretty:equipe";
	}
	
	public String update(){
		service.update(equipe);
		return "pretty:equipe";
	}
	public List<Equipe> listarAtivos(){
		return service.listarAtivos();
	}
	public void desativar(long id){
		service.Desativar(id);
	}
	
	@URLActions(actions = {@URLAction(mappingId = "equipe-editar", onPostback = false)})
	public void load() throws IOException{
		equipe = service.getEquipeById(equipe.getIdEquipe());
	}
}
