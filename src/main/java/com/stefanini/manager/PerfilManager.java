package com.stefanini.manager;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Perfil;
import com.stefanini.service.PerfilService;

@ManagedBean
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "perfil", pattern = "/perfil", viewId = "/pages/perfil/perfil-listar.xhtml"),
		@URLMapping(id = "perfil-incluir", pattern = "/incluir", viewId = "/pages/perfil/perfil-incluir.xhtml", parentId = "perfil"),
		@URLMapping(id = "perfil-editar", pattern = "#{perfilManager.perfil.id}/editar", viewId = "/pages/perfil/perfil-editar.xhtml", parentId = "perfil") 
		})

public class PerfilManager {
	
	private Perfil perfil = new Perfil();
	private PerfilService service = new PerfilService();
	
	public PerfilManager(){
		
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public PerfilService getService() {
		return service;
	}

	public void setService(PerfilService service) {
		this.service = service;
	}
	
	public String save(){
		service.save(perfil);
		return "pretty:perfil";
	}
	
	public String update(){
		service.update(perfil);
		return "pretty:perfil";
	}
	
	public List<Perfil> listarAtivos(){
		return service.listarAtivos();
	}
	
	public void desativar(Long id){
		service.desativar(id);
	}
	
	@URLActions(actions = {@URLAction(mappingId = "perfil-editar" , onPostback = false)})
	public void load() throws IOException{
		perfil = service.getPerfilById(perfil.getId());
	}
}
