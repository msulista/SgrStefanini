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
import com.stefanini.entidade.Modulo;
import com.stefanini.service.CargaHorariaService;
import com.stefanini.service.ModuloService;

@ManagedBean
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "cargaHoraria", pattern = "/cargaHoraria", viewId = "/pages/cargaHoraria/cargaHoraria-listar.xhtml"),
		@URLMapping(id = "cargaHoraria-incluir", pattern = "/incluir", viewId = "/pages/cargaHoraria/cargaHoraria-incluir.xhtml", parentId = "cargaHoraria"),
		@URLMapping(id = "cargaHoraria-editar", pattern = "/#{cargaHorariaManager.cargaHoraria.id}/editar", viewId = "/pages/cargaHoraria/cargaHoraria-editar.xhtml", parentId = "cargaHoraria")
})
public class CargaHorariaManager {
	
	private CargaHoraria cargaHoraria= new CargaHoraria();
	private CargaHorariaService service = new CargaHorariaService();
	
	public CargaHorariaManager(){		
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

	public String save(){
		service.save(cargaHoraria);
		return "pretty:cargaHoraria";
	}
	
	public String update(){
		service.update(cargaHoraria);
		return "pretty:cargaHoraria";
	}
	
	public List<CargaHoraria> listar(){
		return service.listar();
	}
	
	public void remove(Long id) {
		service.remove(id);
	}
//	
//	@URLActions(actions = {@URLAction(mappingId = "cargaHoraria-editar", onPostback = false)})
//	public void load() throws IOException{
//		cargaHoraria = service.getCargaHorariaById(cargaHoraria.getIdCargaHoraria());
//	}
	
	
	

}
