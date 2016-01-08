package com.stefanini.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Profissional;
import com.stefanini.service.ProfissionalService;

@ManagedBean
@ViewScoped
@URLMappings(mappings = {
		@URLMapping(id = "profissional", pattern = "/profissional", viewId = "/pages/profissional/profissional-listar.xhtml"),
		@URLMapping(id = "profissional-incluir", pattern = "/incluir", viewId = "/pages/profissional/profissional-incluir.xhtml", parentId = "profissional"),
		@URLMapping(id = "profissional-editar", pattern = "/#{profissionalManager.profissional.matricula}/editar", viewId = "/pages/profissional/profissional-editar.xhtml", parentId = "profissional"),
		@URLMapping(id = "profissional-historico", pattern = "/#{profissionalManager.profissional.matricula}/historico", viewId = "/pages/profissional/profissional-historico.xhtml", parentId = "profissional") })

public class ProfissionalManager {

	private Profissional profissional = new Profissional();
	private List<Profissional> profissionalHistorico;
	private ProfissionalService service = new ProfissionalService();
	private List<Profissional> lista;
	private List<String> queryDinamica = new ArrayList<>();
	private boolean temDataStatus = false;

	public ProfissionalManager() {		
		lista = new ArrayList<>();
		populaLista();
	}
	
	

	public List<String> getQueryDinamica() {
		return queryDinamica;
	}

	public void setQueryDinamica(List<String> queryDinamica) {
		this.queryDinamica = queryDinamica;
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

	public boolean isTemDataStatus() {
		return temDataStatus;
	}

	public void setTemDataStatus(boolean temDataStatus) {
		this.temDataStatus = temDataStatus;
	}

	public String save() {
		if (service.save(profissional)) {
			return "pretty:profissional";
		} else {
			return null;
		}

	}

	public String update() {
		if (service.update(profissional)) {
			return "pretty:profissional";
		} else {
			return null;
		}

	}

	private List<Profissional> listarAtivos() {
		return service.listarAtivos(this.queryDinamica);
	}
	
	
	public void populaLista() {

		if (queryDinamica.isEmpty()) {
			for (int i = 0; i <= 11; i++) {
				queryDinamica.add("");
			}
			queryDinamica.set(0, "SELECT p FROM Profissional p WHERE (p.registroValidadeInicio <= CURRENT_DATE) AND (p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE) ");
			queryDinamica.set(10, " ORDER BY p.nome ASC");
			System.out.println("$$$$$$$$$----------------- CRIEI NOVO ARRAY");
			this.lista = listarAtivos();
		}
	}
	
	public void executaPesquisa(){
		this.buscaPorMatricula();
		this.buscarPorNome();
		this.buscaPorEquipe();	
		this.lista = listarAtivos();
	}
	
	public void buscaPorMatricula() {
		if (!(this.profissional.getMatricula() == 0)) {
			String query = " AND p.matricula = " + this.profissional.getMatricula();
			queryDinamica.set(1, query);
		} else {
			queryDinamica.set(1, "");
		}
	}

	public void buscarPorNome() {
		if (!this.profissional.getNome().isEmpty()) {
			String query = " AND p.nome LIKE '" + this.profissional.getNome() + "_%'";
			queryDinamica.set(2, query);
		} else {
			queryDinamica.set(2, "");
		}
	}

	public void buscaPorEquipe() {
		
		if (!(this.profissional.getEquipe() == null)) {
			String query = " AND p.equipe.id = " + this.profissional.getEquipe().getId();
			queryDinamica.set(3, query);
		} else {
			queryDinamica.set(3, "");
		}
	}



	public List<Profissional> lista() {
		return this.lista;
	}

	public List<Profissional> listaHistorico() {
		return this.profissionalHistorico;
	}

	public String desativar(Long id) {
		service.desativar(id);
		return "pretty:profissional";
	}

	public void mudaStatus() {

		if (this.profissional.getStatus().getNome().equals("Afastado")
				|| this.profissional.getStatus().getNome().equals("Licença")) {
			this.temDataStatus = false;
		} else {
			this.temDataStatus = true;
		}

	}

	@URLActions(actions = { @URLAction(mappingId = "profissional-editar", onPostback = false) })
	public void load() throws IOException {
		profissional = service.getProfissionalParaEdicao(profissional.getMatricula());
	}

	@URLActions(actions = { @URLAction(mappingId = "profissional-historico", onPostback = false) })
	public void loadHistorico() throws IOException {
		profissionalHistorico = service.getProfissionalHistorico(profissional.getMatricula());
	}

	public List<Profissional> getProfissionalHistorico() {
		return profissionalHistorico;
	}

	public void setProfissionalHistorico(List<Profissional> profissionalHistorico) {
		this.profissionalHistorico = profissionalHistorico;
	}

}
