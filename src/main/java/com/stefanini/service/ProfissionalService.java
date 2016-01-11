package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Equipe;
import com.stefanini.entidade.Profissional;
import com.stefanini.entidade.Status;
import com.stefanini.util.DateUtil;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class ProfissionalService {

	private EquipeService equipeService = new EquipeService();
	private CargoService cargoService = new CargoService();
	private PerfilService perfilService = new PerfilService();
	private CargaHorariaService cargaHorariaService = new CargaHorariaService();
	private FormaContratacaoService formaContratacaoService = new FormaContratacaoService();
	private StatusService statusService = new StatusService();
	private CelulaService celulaService = new CelulaService();

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
				manager.persist(profissional);
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

		if (DateUtil.verificaDiaUtil(profissional.getDataAdmissao())&&DateUtil.verificaDiaUtil(profissional.getDataDemissao())&&DateUtil.verificaDiaUtil(profissional.getRegistroValidadeInicio())&&DateUtil.verificaDiaUtil(profissional.getRegistroValidaeFim())) {
			
			Profissional profissionalMerge = (Profissional) getProfissionalParaEdicao(profissional.getMatricula());
			
				if (DateUtil.verificaDataValida(profissional.getDataAdmissao(), profissional.getDataDemissao())) {
								
					if(DateUtil.verificaDataValida(DateUtil.getDataParaComparacao(new Date()),profissional.getRegistroValidadeInicio())){
									
						if(DateUtil.verificaDataValida(DateUtil.getDataParaComparacao(new Date()),profissional.getRegistroValidaeFim())){
										
							if(DateUtil.verificaDataValida(profissional.getRegistroValidadeInicio(),profissional.getRegistroValidaeFim())){
											
								if(profissional.getRegistroValidadeInicio().compareTo(profissionalMerge.getRegistroValidadeInicio())==0){
									profissionalMerge.setRegistroValidaeFim(new Date());
									manager.merge(profissionalMerge);
					
								}else{
									profissionalMerge.setRegistroValidaeFim(DateUtil.retornaDataFimAntesDoNovoInicio(profissional.getRegistroValidadeInicio()));
									manager.merge(profissionalMerge);
													
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
				
								manager.persist(profissionalPersist);
								manager.getTransaction().commit();
								manager.close();
				
								return true;
				
										}else{
											Mensagem.add("Data final do registro não pode ser anterior a de inicio!");
											manager.close();
											return false;
										}
										
									}else{
										Mensagem.add("Data final do regirstro nao pode ser anteriro ao dia atual");
										manager.close();
										return false;
									}
									
								}else{
									Mensagem.add("Nova data de registro nao pode ser anterior ao dia atual");
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
	
	public void desativar(Profissional profissional) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		Status status = statusService.getStatusById((long) 16);
		manager.getTransaction().begin();
		Profissional profissionalMerge = (Profissional) getProfissionalParaEdicao(profissional.getMatricula());		
		
		if(new Date().compareTo(profissionalMerge.getRegistroValidadeInicio())==0){
			profissionalMerge.setRegistroValidaeFim(new Date());
			manager.merge(profissionalMerge);

		}else{
			profissionalMerge.setRegistroValidaeFim(DateUtil.retornaDataFimAntesDoNovoInicio(new Date()));
			manager.merge(profissionalMerge);
							
		}
		
		Profissional profissionalPersist = new Profissional();
		profissionalPersist.setNome(profissionalMerge.getNome());
		profissionalPersist.setMatricula(profissionalMerge.getMatricula());
		profissionalPersist.setSalario(profissionalMerge.getSalario());
		profissionalPersist.setBeneficios(profissionalMerge.getBeneficios());
		profissionalPersist.setValorHora(profissionalMerge.getValorHora());
		profissionalPersist.setDataAdmissao(profissionalMerge.getDataAdmissao());
		profissionalPersist.setDataDemissao(profissionalMerge.getDataDemissao());
		profissionalPersist.setRegistroValidadeInicio(profissionalMerge.getRegistroValidadeInicio());
		profissionalPersist.setRegistroValidaeFim(profissionalMerge.getRegistroValidaeFim());
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
