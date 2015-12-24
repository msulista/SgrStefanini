package com.stefanini.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Status;
import com.stefanini.service.StatusService;

@ManagedBean
@RequestScoped
@URLMappings(mappings = { @URLMapping(id = "status", pattern = "/status", viewId = "/pages/status/status-listar.xhtml"),
		@URLMapping(id = "status-incluir", pattern = "/incluir", viewId = "/pages/status/status-incluir.xhtml", parentId = "status")
})
public class StatusManager {

	private Status status = new Status();
	private StatusService service = new StatusService();
	private List<Status> lista;

	public StatusManager() {

		populaLista();
	}

	public void populaLista() {
		lista = new ArrayList<Status>();
		lista = service.listarAtivos();
	}

	public List<Status> getLista() {
		return lista;
	}

	public void setLista(List<Status> lista) {
		this.lista = lista;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public StatusService getService() {
		return service;
	}

	public void setService(StatusService service) {
		this.service = service;
	}

	public String save() {
		if (this.service.save(status)) {
			return "pretty:status";
		} else {
			return null;
		}
	}

	public List<Status> listarAtivos() {
		return this.service.listarAtivos();
	}

	public String desativar(Long id) {
		this.service.desativar(id);
		return "pretty:status";
	}

}
