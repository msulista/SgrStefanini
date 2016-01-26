package com.stefanini.manager;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import com.stefanini.entidade.Alocacao;
import com.stefanini.service.AlocacaoService;

@ManagedBean
@ViewScoped
@URLMappings(mappings = {
		@URLMapping(id = "alocacao", pattern = "/alocacao", viewId = "/pages/alocacao/alocacoes.xhtml"),
		@URLMapping(id = "alocacao-incluir", pattern = "/incluir", viewId = "/pages/alocacao/alocacao-incluir.xhtml", parentId = "alocacao"),
		@URLMapping(id = "alocacao-editar", pattern = "/#{alocacaoManager.alocacao.id}/editar", viewId = "/pages/alocacao/alocacao-editar.xhtml", parentId = "alocacao")
})
public class AlocacaoManager {

	private Alocacao alocacao;
	private List<Alocacao> alocacoes;
	private AlocacaoService service = new AlocacaoService();
	
	public AlocacaoManager(){
		this.alocacao = new Alocacao();
		this.alocacoes = new ArrayList<>();		
	}
	
	public Alocacao getAlocacao() {
		return alocacao;
	}

	public void setAlocacao(Alocacao alocacao) {
		this.alocacao = alocacao;
	}

	public List<Alocacao> getAlocacoes() {
		return alocacoes;
	}

	public void setAlocacoes(List<Alocacao> alocacoes) {
		this.alocacoes = alocacoes;
	}


	public String save(){
		if(service.save(alocacao)){
			return "pretty:alocacao";
		} else {
			return null;
		}
	}
	
	public String update(){
		if(service.update(alocacao)){
			return "pretty:alocacao";
		} else {
			return null;
		}
	}
	
	public List<Alocacao> listarTodos(){
		return service.listarTodos();
	}
	
	public List<Alocacao> listar(){
		return service.listarAtivos();
	}
	
	public String desativar(Long id){
		service.desativar(id);
		return "pretty:alocacao";
	}

	/*@URLActions(actions = { @URLAction(mappingId = "projeto-editar", onPostback = false) })
	public void load() throws IOException {
		alocacao = service.getProjetoParaEdicao(alocacao.getId());
	}*/
}
