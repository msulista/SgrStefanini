package com.stefanini.manager;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Cargo;
import com.stefanini.service.CargoService;

@ManagedBean
@URLMappings(mappings = {
		@URLMapping(id = "cargo", pattern = "/cargo", viewId = "/pages/cargo/cargo-listar.xhtml"),
		@URLMapping(id = "cargo-incluir", pattern = "/incluir", viewId = "/pages/cargo/cargo-incluir.xhtml", parentId = "cargo"),
		@URLMapping(id = "cargo-editar", pattern = "/#{cargoManager.cargo.id}/editar", viewId = "/pages/cargo/cargo-editar.xhtml", parentId = "cargo")
})
public class CargoManager {

	private Cargo cargo;
	private CargoService service;
	
	public CargoManager(Cargo cargo, CargoService service) {
		this.cargo = new Cargo();
		this.service = new CargoService();
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public CargoService getService() {
		return service;
	}

	public void setService(CargoService service) {
		this.service = service;
	}
	
	public String save(){
		service.save(cargo);
		return "pretty:cargo";
	}
	
	public String update(){
		service.update(cargo);
		return "pretty:cargo";
	}
	
	public List<Cargo> listarAtivos(){
		return service.listar();		
	}
	
	public void desativar(Long id){
		service.desativar(id);
	}
	
	@URLActions(actions = {@URLAction(mappingId = "cargo-editar", onPostback = false)})
	public void load() throws IOException{
		cargo = service.getCargoById(cargo.getIdCargo());		
	}
}
