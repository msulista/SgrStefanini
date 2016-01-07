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
import com.stefanini.entidade.CargaHoraria;
import com.stefanini.service.CargaHorariaService;

@ManagedBean
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "cargaHoraria", pattern = "/cargaHoraria", viewId = "/pages/cargaHoraria/cargaHoraria-listar.xhtml"),
		@URLMapping(id = "cargaHoraria-incluir", pattern = "/incluir", viewId = "/pages/cargaHoraria/cargaHoraria-incluir.xhtml", parentId = "cargaHoraria"),
		@URLMapping(id = "cargaHoraria-editar", pattern = "/#{cargaHorariaManager.cargaHoraria.id}/editar", viewId = "/pages/cargaHoraria/cargaHoraria-editar.xhtml", parentId = "cargaHoraria"),
		@URLMapping(id = "cargaHoraria-historico", pattern = "/historico", viewId = "/pages/cargaHoraria/cargaHoraria-historico.xhtml", parentId = "cargaHoraria") })

public class CargaHorariaManager {

	private CargaHoraria cargaHoraria = new CargaHoraria();
	private CargaHorariaService service = new CargaHorariaService();
	private List<CargaHoraria>historico;
	private List<CargaHoraria> lista;

	public CargaHorariaManager() {
		populaLista();
	}
	
	public void populaLista(){
		lista = new ArrayList<CargaHoraria>();
		this.lista = service.listarAtivos();
	}
		
	public List<CargaHoraria> getLista() {
		return lista;
	}

	public CargaHoraria getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(CargaHoraria cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public CargaHorariaService getService() {
		return service;
	}

	public void setService(CargaHorariaService service) {
		this.service = service;
	}

	
	public List<CargaHoraria> getHistorico() {
		return historico;
	}

	public void setHistorico(List<CargaHoraria> historico) {
		this.historico = historico;
	}

	public String save() {
		if (service.save(cargaHoraria)) {
			return "pretty:cargaHoraria";
		} else {
			return null;
		}

	}

	public String update() {
		if (service.update(cargaHoraria)) {
			return "pretty:cargaHoraria";
		} else {
			return null;
		}
	}

	public List<CargaHoraria> listarAtivos() {
		List<CargaHoraria> lista = new ArrayList<>();
		lista = service.listarAtivos();
		return lista;
	}

	public String desativar(Long id) {
		service.desativar(id);
		return "pretty:cargaHoraria";
	}

	@URLActions(actions = { @URLAction(mappingId = "cargaHoraria-editar", onPostback = false) })
	public void load() throws IOException {
		cargaHoraria = service.getCargaHorariaById(cargaHoraria.getId());
	}
	
	@URLActions(actions = { @URLAction(mappingId = "cargaHoraria-historico", onPostback = false) })
	public void loadHistorico() throws IOException {
		historico = service.listarHistorico();
	}

}
