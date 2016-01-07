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
import com.stefanini.entidade.Celula;
import com.stefanini.service.CelulaService;

@ManagedBean
@RequestScoped
@URLMappings(mappings = { @URLMapping(id = "celula", pattern = "/celula", viewId = "/pages/celula/celula-listar.xhtml"),
		@URLMapping(id = "celula-incluir", pattern = "/incluir", viewId = "/pages/celula/celula-incluir.xhtml", parentId = "celula"),
		@URLMapping(id = "celula-historico", pattern = "/historico", viewId = "/pages/celula/celula-historico.xhtml", parentId = "celula") })

public class CelulaManager {

	private Celula celula = new Celula();
	private CelulaService service = new CelulaService();
	private List<Celula> historico;
	private List<Celula> lista;

	public CelulaManager() {
		populaLista();
	}

	public void populaLista() {
		lista = new ArrayList<Celula>();
		lista = service.listarAtivo();
	}

	public List<Celula> getLista() {
		return lista;
	}

	public void setLista(List<Celula> lista) {
		this.lista = lista;
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

	
	public List<Celula> getHistorico() {
		return historico;
	}

	public void setHistorico(List<Celula> historico) {
		this.historico = historico;
	}

	public String save() {
		if (service.save(celula)) {
			return "pretty:celula";
		} else {
			return null;
		}
	}

	public List<Celula> listarAtivos() {
		return service.listarAtivo();
	}

	public String desativar(Long id) {
		service.desativar(id);
		return "pretty:celula";
	}

	@URLActions(actions = { @URLAction(mappingId = "celula-historico", onPostback = false) })
	public void loadHistorico() throws IOException {
		historico = service.listarHistorico();
	}
}
