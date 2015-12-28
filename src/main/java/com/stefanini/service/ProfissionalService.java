package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Profissional;
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

			if (DateUtil.verificaDiaUtil(profissional.getDataAdmissao())&&DateUtil.verificaDiaUtil(profissional.getDataDemissao())&&DateUtil.verificaDiaUtil(profissional.getRegistroValidadeInicio())&&DateUtil.verificaDiaUtil(profissional.getRegistroValidaeFim())) {
				
				if(DateUtil.verificaDataValida(profissional.getRegistroValidadeInicio(), profissional.getDataAdmissao())){
					
					if(DateUtil.verificaDataValida(profissional.getDataAdmissao(), profissional.getDataDemissao())){
						
						if(DateUtil.verificaDataValida(profissional.getRegistroValidadeInicio(),profissional.getRegistroValidaeFim())&&DateUtil.verificaDataValida(profissional.getDataAdmissao() ,profissional.getRegistroValidaeFim())&&DateUtil.verificaDataValida(profissional.getDataDemissao(),profissional.getRegistroValidaeFim())){
				
							profissional.setRegistroValidadeInicio(profissional.getDataAdmissao());
				manager.getTransaction().begin();
				manager.persist(profissional);
				manager.getTransaction().commit();
				manager.close();
				return true;
						}else{
							Mensagem.add("Data final do registro não pode ser anterior as outras datas!");
							manager.close();
							return false;
						}
					}else{
						Mensagem.add("Data de demissão anterior a de admissão!");
						manager.close();
						return false;
					}
				}else{
					Mensagem.add("Data de admissão anterior ao registro inicial!");
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
			
			Profissional profissionalMerge = (Profissional) getProfissionalById(profissional.getId());

			if(DateUtil.verificaDataValida(profissionalMerge.getRegistroValidadeInicio(), profissional.getRegistroValidadeInicio())){
			
				if(DateUtil.verificaDataValida(profissionalMerge.getRegistroValidadeInicio(), profissional.getDataAdmissao())){
					
					if(DateUtil.verificaDataValida(profissionalMerge.getRegistroValidadeInicio(), profissional.getDataDemissao())){
				
						if (DateUtil.verificaDataValida(profissional.getDataAdmissao(), profissional.getDataDemissao())) {

							if(DateUtil.verificaDataValida(profissional.getRegistroValidadeInicio(),profissional.getRegistroValidaeFim())&&DateUtil.verificaDataValida(profissional.getDataAdmissao() ,profissional.getRegistroValidaeFim())&&DateUtil.verificaDataValida(profissional.getDataDemissao(),profissional.getRegistroValidaeFim())){
				
				profissionalMerge.setRegistroValidaeFim(DateUtil.retornaDataFimAntesDoNovoInicio(profissional.getRegistroValidadeInicio()));
				manager.merge(profissionalMerge);
				
			

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
								Mensagem.add("Data final do registro não pode ser anterior as outras datas!");
								manager.close();
								return false;
							}
					} else {
				Mensagem.add("Data de demissão anterior a de admissão!");
				manager.close();
				return false;
			}
					}else{
						Mensagem.add("Data de demissão é anterior a data de registro inicial!");
						manager.close();
						return false;
					}
					}else{
						Mensagem.add("Data de adimissão é anterior a data de registro inicial!");
						manager.close();
						return false;
				}
			}else{
				Mensagem.add("Nova data de registro é anterior a cadastrada originalmente!");
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
	public List<Profissional> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();		
		Query q = manager.createNamedQuery("Profissional.findAtivos");
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
	public List<Profissional> getProfissionalByMatricula(int matricula) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNamedQuery("Profissional.findMatricula");
		q.setParameter("matricula", matricula);
		List<Profissional> profissionais = q.getResultList();
		manager.close();
		return profissionais;
	}
	public void desativar(Long id) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		Profissional profissionalDesativar = (Profissional) getProfissionalById(id);
		profissionalDesativar.setRegistroValidaeFim(new Date());
		profissionalDesativar.setDataDemissao(profissionalDesativar.getRegistroValidaeFim());
		manager.merge(profissionalDesativar);
		manager.getTransaction().commit();
		manager.close();
	}	

}
