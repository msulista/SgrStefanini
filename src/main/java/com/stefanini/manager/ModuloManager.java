package com.stefanini.manager;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Modulo;
import com.stefanini.service.ModuloService;

@ManagedBean
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "modulo", pattern = "/modulo", viewId = "/pages/modulo/modulo-listar.xhtml"),
		@URLMapping(id = "modulo-incluir", pattern = "/incluir", viewId = "/pages/modulo/modulo-incluir.xhtml", parentId = "modulo"),
		@URLMapping(id = "modulo-editar", pattern = "/#{moduloManager.modulo.id}/editar", viewId = "/pages/modulo/modulo-editar.xhtml", parentId = "modulo")
})
public class ModuloManager {
	
	private Modulo modulo = new Modulo();
	private ModuloService service = new ModuloService();
	
	public ModuloManager(){		
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public ModuloService getService() {
		return service;
	}

	public void setService(ModuloService service) {
		this.service = service;
	}
	
	public String save(){
		service.save(modulo);
		return "pretty:modulo";
	}
	
	public String update(){
		service.update(modulo);
		return "pretty:modulo";
	}
	
	public List<Modulo> listar(){
		return service.listar();
	}
	
	public void remove(Long id) {
		service.remove(id);
	}
	
	@URLActions(actions = {@URLAction(mappingId = "modulo-editar", onPostback = false)})
	public void load() throws IOException{
		modulo = service.getModuloById(modulo.getId());
	}
	
	
	

}
