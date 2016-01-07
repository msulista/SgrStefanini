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
import com.stefanini.entidade.FormaContratacao;
import com.stefanini.service.FormaContratacaoService;

@ManagedBean
@RequestScoped
@URLMappings(mappings = {
		@URLMapping(id = "formaContratacao", pattern = "/formaContratacao", viewId = "/pages/formaContratacao/formaContratacao-listar.xhtml"),
		@URLMapping(id = "formaContratacao-incluir", pattern = "/incluir", viewId = "/pages/formaContratacao/formaContratacao-incluir.xhtml", parentId = "formaContratacao"),
		@URLMapping(id = "formaContratacao-historico", pattern = "/historico", viewId = "/pages/formaContratacao/formaContratacao-historico.xhtml", parentId = "formaContratacao") })

public class FormaContratacaoManager {

	private FormaContratacao formaContratacao = new FormaContratacao();
	private FormaContratacaoService service = new FormaContratacaoService();
	private List<FormaContratacao>historico;
	private List<FormaContratacao> lista;

	public FormaContratacaoManager() {
		populaLista();
	}

	public void populaLista() {
		lista = new ArrayList<FormaContratacao>();
		lista = service.listarAtivos();
	}

	public List<FormaContratacao> getLista() {
		return lista;
	}

	public void setLista(List<FormaContratacao> lista) {
		this.lista = lista;
	}

	public FormaContratacao getFormaContratacao() {
		return formaContratacao;
	}

	public void setFormaContratacao(FormaContratacao formaContratacao) {
		this.formaContratacao = formaContratacao;
	}

	public FormaContratacaoService getService() {
		return service;
	}

	public void setService(FormaContratacaoService service) {
		this.service = service;
	}

	
	
	public List<FormaContratacao> getHistorico() {
		return historico;
	}

	public void setHistorico(List<FormaContratacao> historico) {
		this.historico = historico;
	}

	public String save() {
		if (this.service.save(formaContratacao)) {
			return "pretty:formaContratacao";
		} else {
			return null;
		}
	}

	public List<FormaContratacao> listarAtivos() {
		return this.service.listarAtivos();
	}

	public String desativar(Long id) {
		this.service.desativar(id);
		return "pretty:formaContratacao";
	}

	@URLActions(actions = { @URLAction(mappingId = "formaContratacao-historico", onPostback = false) })
	public void loadHistorico() throws IOException {
		historico = service.listarHistorico();
	}
	
}
