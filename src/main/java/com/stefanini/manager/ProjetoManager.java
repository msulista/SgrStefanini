package com.stefanini.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Celula;
import com.stefanini.entidade.Equipe;
import com.stefanini.entidade.Projeto;
import com.stefanini.entidade.Recurso;
import com.stefanini.service.ProjetoService;

@ManagedBean
@ViewScoped
@URLMappings(mappings = {
		@URLMapping(id = "projeto", pattern = "/projeto", viewId = "/pages/projeto/projeto-listar.xhtml"),
		@URLMapping(id = "projeto-incluir", pattern = "/incluir", viewId = "/pages/projeto/projeto-incluir.xhtml", parentId = "projeto"),
		@URLMapping(id = "projeto-editar", pattern = "/#{projetoManager.projeto.codigo}/editar", viewId = "/pages/projeto/projeto-editar.xhtml", parentId = "projeto")
})
public class ProjetoManager {
	
	private Projeto projeto;
	private List<Projeto> projetos;
	private ProjetoService service = new ProjetoService();
	private Equipe equipe ;
	private Celula celula ;
	
	public ProjetoManager(){
		this.projeto = new Projeto();
		this.projetos = new ArrayList<>();		
	}
	
	public Projeto getProjeto(){
		return projeto;
	}
	
	public void setProjeto(Projeto projeto){
		this.projeto = projeto;
	}
	
	public List<Projeto> getProjetos(){
		return projetos;
	}
	
	public void setProjetos(List<Projeto> projetos){
		this.projetos = projetos;
	}
	
	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}

	public String save(){
		if(service.save(projeto)){
			return "pretty:alocacao";
		} else {
			return null;
		}
	}
	
	public String update(){
		if(service.update(projeto)){
			return "pretty:alocacao";
		} else {
			return null;
		}
	}
	
	public List<Projeto> listarTodos(){
		return service.listarTodos();
	}
	
	public List<Projeto> listar(){
		return service.listarAtivos();
	}
	
	public void desativar(Long id){
		service.desativar(id);
		
	}
	
	public List<Projeto> listarTudo(){
		return projetos;
	}
	
	public void valueChangeEquipe(ValueChangeEvent event) {
	       equipe = (Equipe) event.getNewValue();
	       projetos = service.listarTodos(this.equipe,this.celula);
	       listarTudo();
	    }

	public void valueChangeCelula(ValueChangeEvent event) {
	       celula = (Celula) event.getNewValue();
	       projetos = service.listarTodos(this.equipe,this.celula);
	       listarTudo();
	    }

	@URLActions(actions = { @URLAction(mappingId = "projeto-editar", onPostback = false) })
	public void load() throws IOException {
		projeto = service.getProjetoParaEdicao(projeto.getCodigo());
	}
}