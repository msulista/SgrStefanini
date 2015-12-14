package com.stefanini.manager;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Celula;
import com.stefanini.service.CelulaService;

@ManagedBean
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "celula", pattern = "/celula", viewId = "/pages/celula/celula-listar.xhtml"),
		@URLMapping(id = "celula-incluir", pattern = "/incluir", viewId = "/pages/celula/celula-incluir.xhtml", parentId = "celula"),
		@URLMapping(id = "celula-editar", pattern = "/#{celulaManager.celula.id}/editar", viewId = "/pages/celula/celula-editar.xhtml", parentId = "celula") })
public class CelulaManager {

	private Celula celula = new Celula();
	private CelulaService service = new CelulaService();

	public CelulaManager() {

	}

	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}

	public CelulaService getService() {
		return service;
	}

	public void setService(CelulaService service) {
		this.service = service;
	}

	public String save() {
		if (service.save(celula)) {
			return "pretty:celula";
		} else {
			return null;
		}
	}

	public String update() {
		if (service.update(celula)) {
			return "pretty:celula";
		} else {
			return null;
		}
	}

	public List<Celula> listarAtivos() {
		return service.listarAtivo();
	}

	public void desativar(Long id) {
		service.desativar(id);
	}

	@URLActions(actions = { @URLAction(mappingId = "celula-editar", onPostback = false) })
	public void load() throws IOException {
		celula = service.getCelulaById(celula.getId());
	}
}
