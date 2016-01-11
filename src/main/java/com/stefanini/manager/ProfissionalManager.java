package com.stefanini.manager;

import java.io.IOException;
import java.util.ArrayList;
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
	private String query = "";
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

	private List<Profissional> listarTudo() {
		return service.listarTudo(this.query);
	}

	public void populaLista() {
		if (query.equals("")) {
			query = "SELECT p FROM Profissional p WHERE p.id != 0 AND (p.registroValidadeInicio <= CURRENT_DATE) AND (p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome";
			this.lista = listarTudo();
		}
	}

	public void limparPesquisa() {
		profissional = new Profissional();
		query = "";
		populaLista();
	}

	public void executaPesquisa() {
		if (this.profissional.getMatricula() != 0 ||
				!this.profissional.getNome().isEmpty() || 
				!(this.profissional.getEquipe() == null) || 
				!(this.profissional.getCargo() == null)	||
				!(this.profissional.getPerfil() == null) || 
				!(this.profissional.getStatus() == null)) {

			query = "SELECT p FROM Profissional p WHERE p.id != 0";
			if (!(this.profissional.getMatricula() == 0)) {
				query = query + " AND p.matricula = " + this.profissional.getMatricula();
			}
			if (!this.profissional.getNome().isEmpty()) {
				query = query + " AND p.nome LIKE '" + this.profissional.getNome() + "_%'";
			}
			if (!(this.profissional.getEquipe() == null)) {
				query = query + " AND p.equipe.id = " + this.profissional.getEquipe().getId();
			}
			if (!(this.profissional.getCargo() == null)) {
				query = query + " AND p.cargo.id = " + this.profissional.getCargo().getId();
			}
			if (!(this.profissional.getPerfil() == null)) {
				query = query + " AND p.perfil.id = " + this.profissional.getPerfil().getId();
			}
			if (!(this.profissional.getStatus() == null)) {
				if (!this.profissional.getStatus().getNome().equalsIgnoreCase("Inativo")) {
					query = query + " AND p.status.id = " + this.profissional.getStatus().getId()
							+ " AND (p.registroValidadeInicio <= CURRENT_DATE) AND (p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE)";
					query = query + "   ORDER BY p.nome ASC";
					this.lista = listarTudo();
				} else {
					query = query + " AND p.status.id = " + this.profissional.getStatus().getId() + " ORDER BY p.nome ASC";
					this.lista = listarTudo();
				}

			}else{
				query = query + "  AND (p.registroValidadeInicio <= CURRENT_DATE) AND (p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC";
				this.lista = listarTudo();
			}
		} else {
			System.out.println("$$$$$$$$$$$------------- caí no else");
			query = "SELECT p FROM Profissional p WHERE p.id != 0 AND (p.registroValidadeInicio <= CURRENT_DATE) AND (p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome";
			this.lista = listarTudo();
		}
	}

	public List<Profissional> lista() {
		return this.lista;
	}

	public List<Profissional> listaHistorico() {
		return this.profissionalHistorico;
	}

	public String desativar(Profissional profissional) {
		service.desativar(profissional);
		return "pretty:profissional";
	}

	public void mudaStatus() {

		if (this.profissional.getStatus().getNome().equals("Afastado")
				|| this.profissional.getStatus().getNome().equals("Licença")
				|| this.profissional.getStatus().getNome().equals("Férias")) {
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
