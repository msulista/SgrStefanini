package com.stefanini.manager;

import java.io.IOException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Profissional;
import com.stefanini.service.ProfissionalService;

@ManagedBean
@ViewScoped
@URLMappings(mappings = { @URLMapping(id = "profissional", pattern = "/profissional", viewId = "/pages/profissional/profissional-listar.xhtml"),
		@URLMapping(id = "profissional-incluir", pattern = "/incluir", viewId = "/pages/profissional/profissional-incluir.xhtml", parentId = "profissional"),
		@URLMapping(id = "profissional-editar", pattern = "/#{profissionalManager.profissional.id}/editar", viewId = "/pages/profissional/profissional-editar.xhtml", parentId = "profissional") })
public class ProfissionalManager {

	private Profissional profissional = new Profissional();
	private ProfissionalService service = new ProfissionalService();
	private List<Profissional> lista;
	private String nome;

	public ProfissionalManager() {
		populaLista();
		this.nome = "";
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
	}

	public ProfissionalService getService() {
		return service;
	}

	public void setService(ProfissionalService service) {
		this.service = service;
	}

	public List<Profissional> getLista() {
		return lista;
	}

	public void setLista(List<Profissional> lista) {
		this.lista = lista;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String save() {
		if(service.save(profissional)){
			return "pretty:profissional";
		}else{
			return null;
		}
		
	}

	public String update() {
		if(service.update(profissional)){
			return "pretty:profissional";
		}else{
			return null;
		}
	
	}

	private List<Profissional> listarAtivos() {
		return service.listarAtivos();
	}

	public void buscarPorNome(){
		this.lista = this.service.buscaPorNome(this.nome);
		
	}
	
	public void populaLista(){	
		this.nome = "";
		this.lista = listarAtivos();
	}
	
	public List<Profissional>lista(){
		return this.lista;
	}
	
	public void desativar(Long id) {
		service.desativar(id);
	}

	@URLActions(actions = { @URLAction(mappingId = "profissional-editar", onPostback = false) })
	public void load() throws IOException {
		profissional = service.getProfissionalById(profissional.getId());
	}
	
	
	
}
