package com.stefanini.manager;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.CargaHoraria;
import com.stefanini.entidade.PerfilStefanini;
import com.stefanini.service.CargaHorariaService;
import com.stefanini.service.PerfilStefaniniService;

@ManagedBean
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "perfilStefanini", pattern = "/perfilStefanini", viewId = "/pages/perfilStefanini/perfilStefanini-listar.xhtml"),
		@URLMapping(id = "perfilStefanini-incluir", pattern = "/incluir", viewId = "/pages/perfilStefanini/perfilStefanini-incluir.xhtml", parentId = "perfilStefanini"),
		@URLMapping(id = "perfilStefanini-editar", pattern = "/#{perfilStefaniniManager.perfilStefanini.id}/editar", viewId = "/pages/perfilStefanini/perfilStefanini-editar.xhtml", parentId = "perfilStefanini") })
public class PerfilStefaniniManager {

	private PerfilStefanini perfilStefanini = new PerfilStefanini();
	private PerfilStefaniniService service = new PerfilStefaniniService();

	public PerfilStefaniniManager() {
	}
	
	public PerfilStefanini getPerfilStefanini() {
		return perfilStefanini;
	}

	public void setPerfilStefanini(PerfilStefanini perfilStefanini) {
		this.perfilStefanini = perfilStefanini;
	}

	public PerfilStefaniniService getService() {
		return service;
	}

	public void setService(PerfilStefaniniService service) {
		this.service = service;
	}

	public String save() {
		if (service.save(perfilStefanini)) {
			return "pretty:perfilStefanini";
		} else {
			return null;
		}

	}

	public String update() {
		if (service.update(perfilStefanini)) {
			return "pretty:perfilStefanini";
		} else {
			return null;
		}
	}

	public List<PerfilStefanini> listarAtivos() {
		return service.listarAtivos();
	}

	public void desativar(Long id) {
		service.desativar(id);
	}

	@URLActions(actions = { @URLAction(mappingId = "perfilStefanini-editar", onPostback = false) })
	public void load() throws IOException {
		perfilStefanini = service.getPerfilStefaniniById(perfilStefanini.getId());
	}

}
