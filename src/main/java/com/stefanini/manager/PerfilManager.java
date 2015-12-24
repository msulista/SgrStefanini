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
import com.stefanini.entidade.Perfil;
import com.stefanini.service.PerfilService;

@ManagedBean
@RequestScoped
@URLMappings(mappings = { @URLMapping(id = "perfil", pattern = "/perfil", viewId = "/pages/perfil/perfil-listar.xhtml"),
		@URLMapping(id = "perfil-incluir", pattern = "/incluir", viewId = "/pages/perfil/perfil-incluir.xhtml", parentId = "perfil")
})

public class PerfilManager {

	private Perfil perfil = new Perfil();
	private PerfilService service = new PerfilService();
	private List<Perfil> lista;

	public PerfilManager() {

		populaLista();
	}

	public void populaLista() {
		lista = new ArrayList<Perfil>();
		lista = service.listarAtivos();
	}

	public List<Perfil> getLista() {
		return lista;
	}

	public void setLista(List<Perfil> lista) {
		this.lista = lista;
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

	public String save() {
		if (service.save(perfil)) {
			return "pretty:perfil";
		} else {
			return null;
		}
	}

	public List<Perfil> listarAtivos() {
		return service.listarAtivos();
	}

	public String desativar(Long id) {
		service.desativar(id);
		return "pretty:perfil";
	}

}
