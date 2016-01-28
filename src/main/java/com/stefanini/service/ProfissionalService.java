package com.stefanini.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Equipe;
import com.stefanini.entidade.Profissional;
import com.stefanini.entidade.Recurso;
import com.stefanini.entidade.Status;
import com.stefanini.manager.RecursoManager;
import com.stefanini.util.DateUtil;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class ProfissionalService implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private EquipeService equipeService = new EquipeService();
	private CargoService cargoService = new CargoService();
	private PerfilService perfilService = new PerfilService();
	private CargaHorariaService cargaHorariaService = new CargaHorariaService();
	private FormaContratacaoService formaContratacaoService = new FormaContratacaoService();
	private StatusService statusService = new StatusService();
	private CelulaService celulaService = new CelulaService();
	private RecursoService recursoService = new RecursoService();
	
	@SuppressWarnings("unchecked")
	public boolean save(Profissional profissional) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findMatricula");
		q.setParameter("matricula", profissional.getMatricula());
		List<Profissional> profissionais = q.getResultList();
		if (profissionais.isEmpty()) {

			if (DateUtil.verificaDiaUtil(profissional.getDataAdmissao())&&
					DateUtil.verificaDiaUtil(profissional.getDataDemissao())&&
					DateUtil.verificaDiaUtil(profissional.getRegistroValidadeInicio())&&
					DateUtil.verificaDiaUtil(profissional.getRegistroValidaeFim())&&
					DateUtil.verificaDiaUtil(profissional.getDataSaida())&&
					DateUtil.verificaDiaUtil(profissional.getDataRetorno())) {
				
					if(DateUtil.verificaDataValida(profissional.getDataAdmissao(), profissional.getDataDemissao())){
						
						if(DateUtil.verificaDataValida(profissional.getRegistroValidadeInicio(),profissional.getRegistroValidaeFim())){
								
								if(DateUtil.verificaDataValida(profissional.getDataSaida(), profissional.getDataRetorno())){
				
				manager.getTransaction().begin();
				profissional.setStatus(statusService.getStatusById((long) 13));
				Recurso recurso = new Recurso();
				recurso.setProfissional(profissional);
				recurso.setRegistroValidadeInicio(profissional.getRegistroValidadeInicio());
				recurso.setRegistroValidadeFim(profissional.getRegistroValidaeFim());
				manager.persist(profissional);
				manager.persist(recurso);
				manager.getTransaction().commit();
				manager.close();
				return true;
				
								}else{
									Mensagem.add("Data retorno nao pode ser anterior a de saida");
									manager.close();
									return false;
								}
						}else{
							Mensagem.add("Data final do registro não pode ser anterior a de inicio!");
							manager.close();
							return false;
						}
					}else{
						Mensagem.add("Data de demissão anterior a de admissão!");
						manager.close();
						return false;
					}
			} else {
				Mensagem.add("Data informada não é um dia util!");
				manager.close();
				return false;
			}
		} else {
			Mensagem.add("Matrícula já cadastrada!");
			manager.close();
			return false;
		}
}

	public boolean update(Profissional profissional) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		//verificando se todas as datas nao sao finais de semana
		if (DateUtil.verificaDiaUtil(profissional.getDataAdmissao())&&DateUtil.verificaDiaUtil(profissional.getDataDemissao())&&DateUtil.verificaDiaUtil(profissional.getRegistroValidadeInicio())&&DateUtil.verificaDiaUtil(profissional.getRegistroValidaeFim())) {
			
			Profissional profissionalMerge = (Profissional) getProfissionalParaEdicao(profissional.getMatricula());
			Recurso recursoMerge = (Recurso) recursoService.getRecursoParaEdicao(profissional.getMatricula());
			
			//verificando se data demissão e anterior a de admissão
				if (DateUtil.verificaDataValida(profissional.getDataAdmissao(), profissional.getDataDemissao())) {
					
					Profissional profissionalInicial = (Profissional) getPrimeiroProfissional(profissional.getMatricula());
							
					//verificando se o novo registro é anterior ao primeiro registro
					if(DateUtil.verificaDataValida(profissionalInicial.getRegistroValidadeInicio(),profissional.getRegistroValidadeInicio())){
						
					//verificando se a nova data de inicio é anterior a antiga
						if(DateUtil.verificaDataValida(profissionalMerge.getRegistroValidadeInicio(),profissional.getRegistroValidadeInicio())){
									
							//verificando se data final nao é anterior a inicial
							if(DateUtil.verificaDataValida(profissional.getRegistroValidadeInicio(),profissional.getRegistroValidaeFim())){
								
								//verificando se data retorno é anterior a saida
								if(DateUtil.verificaDataValida(profissional.getDataSaida(), profissional.getDataRetorno())){
									
								//comparando se o registro inicio do profissional é igual ao dia atual e igual a data inicio do antigo
								if(profissional.getRegistroValidadeInicio().compareTo(profissionalMerge.getRegistroValidadeInicio())==0){
									profissionalMerge.setRegistroValidaeFim(profissional.getRegistroValidadeInicio());
									recursoMerge.setRegistroValidadeFim(profissional.getRegistroValidadeInicio());
									manager.merge(profissionalMerge);
									manager.merge(recursoMerge);
								}else{
									profissionalMerge.setRegistroValidaeFim(DateUtil.retornaDataFimAntesDoNovoInicio(profissional.getRegistroValidadeInicio()));
									recursoMerge.setRegistroValidadeFim(DateUtil.retornaDataFimAntesDoNovoInicio(profissional.getRegistroValidadeInicio()));
									manager.merge(profissionalMerge);
									manager.merge(recursoMerge);
													
								}
				
			
								
								Profissional profissionalPersist = new Profissional();
								profissionalPersist.setNome(profissional.getNome());
								profissionalPersist.setMatricula(profissional.getMatricula());
								profissionalPersist.setSalario(profissional.getSalario());
								profissionalPersist.setBeneficios(profissional.getBeneficios());
								profissionalPersist.setValorHora(profissional.getValorHora());
								profissionalPersist.setDataAdmissao(profissional.getDataAdmissao());
								profissionalPersist.setDataDemissao(profissional.getDataDemissao());
								profissionalPersist.setRegistroValidadeInicio(profissional.getRegistroValidadeInicio());
								profissionalPersist.setRegistroValidaeFim(profissional.getRegistroValidaeFim());
								profissionalPersist.setCelula(celulaService.getCelulaById(profissional.getCelula().getId()));
								profissionalPersist.setCargo(cargoService.getCargoById(profissional.getCargo().getId()));
								profissionalPersist.setEquipe(equipeService.getEquipeById(profissional.getEquipe().getId()));
								profissionalPersist.setPerfil(perfilService.getPerfilById(profissional.getPerfil().getId()));
								profissionalPersist.setCargaHoraria(
										cargaHorariaService.getCargaHorariaById(profissional.getCargaHoraria().getId()));
								profissionalPersist.setFormaContratacao(
										formaContratacaoService.getFormaContratacaoById(profissional.getFormaContratacao().getId()));
								profissionalPersist.setStatus(statusService.getStatusById(profissional.getStatus().getId()));
								if(profissional.getStatus().getId()==13){
								profissionalPersist.setDataSaida(null);
								profissionalPersist.setDataRetorno(null);
								}else{
									profissionalPersist.setDataSaida(profissional.getDataSaida());
									profissionalPersist.setDataRetorno(profissional.getDataRetorno());
								}
								
								Recurso recursoPersist = new Recurso();
								recursoPersist.setProfissional(profissionalPersist);
								recursoPersist.setRegistroValidadeInicio(profissionalPersist.getRegistroValidadeInicio());
								recursoPersist.setRegistroValidadeFim(profissionalPersist.getRegistroValidaeFim());
								

								//regra da data retorno em caso de afastamento ou ferias
								if(profissional.getDataRetorno()!=null){
									
									recursoPersist.setRegistroValidadeFim(profissional.getDataRetorno());
									profissionalPersist.setRegistroValidaeFim(profissional.getDataRetorno());
									
									manager.persist(profissionalPersist);
									manager.persist(recursoPersist);
									
									Profissional profissionalRetorno = new Profissional();
									profissionalRetorno.setNome(profissional.getNome());
									profissionalRetorno.setMatricula(profissional.getMatricula());
									profissionalRetorno.setSalario(profissional.getSalario());
									profissionalRetorno.setBeneficios(profissional.getBeneficios());
									profissionalRetorno.setValorHora(profissional.getValorHora());
									profissionalRetorno.setDataAdmissao(profissional.getDataAdmissao());
									profissionalRetorno.setDataDemissao(profissional.getDataDemissao());
									profissionalRetorno.setRegistroValidadeInicio(profissional.getDataRetorno());
									profissionalRetorno.setRegistroValidaeFim(null);
									profissionalRetorno.setDataSaida(null);
									profissionalRetorno.setDataRetorno(null);
									profissionalRetorno.setStatus(statusService.getStatusById((long) 13));
									profissionalRetorno.setCelula(celulaService.getCelulaById(profissional.getCelula().getId()));
									profissionalRetorno.setCargo(cargoService.getCargoById(profissional.getCargo().getId()));
									profissionalRetorno.setEquipe(equipeService.getEquipeById(profissional.getEquipe().getId()));
									profissionalRetorno.setPerfil(perfilService.getPerfilById(profissional.getPerfil().getId()));
									profissionalRetorno.setCargaHoraria(
											cargaHorariaService.getCargaHorariaById(profissional.getCargaHoraria().getId()));
									profissionalRetorno.setFormaContratacao(
											formaContratacaoService.getFormaContratacaoById(profissional.getFormaContratacao().getId()));
										
									Recurso recursoRetorno = new Recurso();
									recursoRetorno.setProfissional(profissionalRetorno);
									recursoRetorno.setRegistroValidadeInicio(profissionalRetorno.getRegistroValidadeInicio());
									recursoRetorno.setRegistroValidadeFim(profissionalRetorno.getRegistroValidaeFim());
									
									manager.persist(profissionalRetorno);
									manager.persist(recursoRetorno);
									manager.getTransaction().commit();
									
								}else{
																		
									manager.persist(profissionalPersist);
									manager.persist(recursoPersist);
									manager.getTransaction().commit();
								}
						
								manager.close();
				
								return true;
								
								}else{
									Mensagem.add("Data retorno nao pode ser anterior a de saida");
									manager.close();
									return false;
								}
										}else{
											Mensagem.add("Data final do registro não pode ser anterior a de inicio!");
											manager.close();
											return false;
										}
									
								}else{
									Mensagem.add("Data nova nao pode ser anterior a alterações ja feitas ou programadas");
									manager.close();
									return false;
								}
					}else{
						Mensagem.add("Nova data de registro nao pode ser anterior ao primeiro cadastro");
						manager.close();
						return false;
						}
								
					} else {
				Mensagem.add("Data de demissão anterior a de admissão!");
				manager.close();
				return false;
					}
			
		} else {
			Mensagem.add("Data informada não é um dia util!");
			manager.close();
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	public List<Profissional> listarTudo(String query) {
		
		System.out.println("888888888888888888888888888888888888888");
		System.out.println("888888888888888888888888888888888888888");
		System.out.println("888888888888888888888888888888888888888");
		System.out.println(query);
		System.out.println("888888888888888888888888888888888888888");
		System.out.println("888888888888888888888888888888888888888");
		System.out.println("888888888888888888888888888888888888888");
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createQuery(query);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}

	public Profissional getProfissionalById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findId");
		q.setParameter("id", id);
		Profissional profissional = (Profissional) q.getSingleResult();
		manager.close();
		return profissional;
	}

	public Profissional getProfissionalParaEdicao(int matricula) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.checkMatriculaParaEdicao");
		q.setParameter("matricula", matricula);
		Profissional profissional = (Profissional) q.getSingleResult();
		manager.close();
		return profissional;
	}

	@SuppressWarnings("unchecked")
	public List<Profissional> buscaPorNome(String nome) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findNome");
		q.setParameter("nome", "%" + nome + "%");
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> buscaPorEquipe(Equipe equipe) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findProfissionalByEquipe");
		q.setParameter("id", equipe.getId());
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> getProfissionalByMatricula(int matricula) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findMatricula");
		q.setParameter("matricula", matricula);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> getProfissionalHistorico(int matricula) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findMatriculaParaHistorico");
		q.setParameter("matricula", matricula);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	
	private Profissional getPrimeiroProfissional(int matricula) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.checkPrimeiroProfissionalByMatricula");
		q.setParameter("matricula", matricula);
		Profissional profissional = (Profissional) q.getSingleResult();
		manager.close();
		return profissional;
	}
	
	public void desativar(Profissional profissional) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		Status status = statusService.getStatusById((long) 16);
		manager.getTransaction().begin();
		Profissional profissionalMerge = (Profissional) getProfissionalParaEdicao(profissional.getMatricula());		
		Recurso recursoMerge = (Recurso) recursoService.getRecursoParaEdicao(profissional.getMatricula());
		
		if(new Date().compareTo(profissionalMerge.getRegistroValidadeInicio())==0){
			
			profissionalMerge.setRegistroValidaeFim(new Date());
			recursoMerge.setRegistroValidadeFim(new Date());
			manager.merge(profissionalMerge);
			manager.merge(recursoMerge);
			
		}else{
			
			profissionalMerge.setRegistroValidaeFim(DateUtil.retornaDataFimAntesDoNovoInicio(new Date()));
			recursoMerge.setRegistroValidadeFim(DateUtil.retornaDataFimAntesDoNovoInicio(new Date()));
			manager.merge(profissionalMerge);
			manager.merge(recursoMerge);
			
		}
		
		Profissional profissionalPersist = new Profissional();
		profissionalPersist.setNome(profissionalMerge.getNome());
		profissionalPersist.setMatricula(profissionalMerge.getMatricula());
		profissionalPersist.setSalario(profissionalMerge.getSalario());
		profissionalPersist.setBeneficios(profissionalMerge.getBeneficios());
		profissionalPersist.setValorHora(profissionalMerge.getValorHora());
		profissionalPersist.setDataAdmissao(profissionalMerge.getDataAdmissao());
		profissionalPersist.setDataDemissao(new Date());
		profissionalPersist.setRegistroValidadeInicio(new Date());
		profissionalPersist.setRegistroValidaeFim(new Date());
		profissionalPersist.setCelula(celulaService.getCelulaById(profissionalMerge.getCelula().getId()));
		profissionalPersist.setCargo(cargoService.getCargoById(profissionalMerge.getCargo().getId()));
		profissionalPersist.setEquipe(equipeService.getEquipeById(profissionalMerge.getEquipe().getId()));
		profissionalPersist.setPerfil(perfilService.getPerfilById(profissionalMerge.getPerfil().getId()));
		profissionalPersist.setCargaHoraria(
				cargaHorariaService.getCargaHorariaById(profissionalMerge.getCargaHoraria().getId()));
		profissionalPersist.setFormaContratacao(
				formaContratacaoService.getFormaContratacaoById(profissionalMerge.getFormaContratacao().getId()));
		profissionalPersist.setStatus(status);
		manager.persist(profissionalPersist);
		
		manager.getTransaction().commit();
		manager.close();
	}	
		
}
