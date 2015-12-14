package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Cargo;
import com.stefanini.entidade.Profissional;
import com.stefanini.util.JPAUtil;

public class ProfissionalService {
	
	private EquipeService equipeService = new EquipeService();
	private CargoService cargoService = new CargoService();
	private PerfilService perfilService = new PerfilService();
	private CargaHorariaService cargaHorariaService = new CargaHorariaService();
	private FormaContratacaoService formaContratacaoService = new FormaContratacaoService();
	private StatusService statusService = new StatusService();
	private CelulaService celulaService = new CelulaService();
	
	public void save(Profissional profissional){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		manager.persist(profissional);
		manager.getTransaction().commit();
		manager.close();
	}
	
	public void update(Profissional profissional){
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();			
		Profissional profissionalMerge = (Profissional)getProfissionalById(profissional.getId());
		profissionalMerge.setRegistroValidaeFim(profissional.getDataManipulacao());
		manager.merge(profissionalMerge);
		manager.getTransaction().commit();
		manager.close();
		
		Profissional profissionalPersist = new Profissional();
		profissionalPersist.setNome(profissional.getNome());
		profissionalPersist.setSalario(profissional.getSalario());
		profissionalPersist.setBeneficios(profissional.getBeneficios());
		profissionalPersist.setValorHora(profissional.getValorHora());
		profissionalPersist.setDataAdmissao(profissional.getDataAdmissao());
		profissionalPersist.setDataDemissao(profissional.getDataDemissao());
		profissionalPersist.setRegistroValidadeInicio(profissional.getDataManipulacao());
		profissionalPersist.setCelula(celulaService.getCelulaById(profissional.getCelula().getId()));
		profissionalPersist.setCargo(cargoService.getCargoById(profissional.getCargo().getId()));
		profissionalPersist.setEquipe(equipeService.getEquipeById(profissional.getEquipe().getId()));
		profissionalPersist.setPerfil(perfilService.getPerfilById(profissional.getPerfil().getId()));
		profissionalPersist.setCargaHoraria(cargaHorariaService.getCargaHorariaById(profissional.getCargaHoraria().getId()));
		profissionalPersist.setFormaContratacao(formaContratacaoService.getFormaContratacaoById(profissional.getFormaContratacao().getId()));
		profissionalPersist.setStatus(statusService.getStatusById(profissional.getStatus().getId()));
				
		save(profissionalPersist);
	}
	
	@SuppressWarnings("unchecked")
	public List<Profissional> listarAtivos(){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_profissional WHERE REGISTRO_VALIDADE_FIM IS NULL ORDER BY REGISTRO_VALIDADE_INICIO ASC", Cargo.class);
		List<Profissional> profissionais = q.getResultList();
		return profissionais;
	}
	
	public Profissional getProfissionalById(Long id){
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_profissional WHERE ID_PROFISSIONAL = :idProfissional", Profissional.class);
		q.setParameter("idProfissional", id);
		Profissional profissional = (Profissional)q.getSingleResult();
		manager.close();
		return profissional;
	}

	public void desativar(Long id) throws ConverterException{
		EntityManager manager = JPAUtil.getEntityManager();	
		manager.getTransaction().begin();	
		Profissional profissionalDesativar = (Profissional)getProfissionalById(id);
		profissionalDesativar.setRegistroValidaeFim(new Date());
		manager.merge(profissionalDesativar);
		manager.getTransaction().commit();
		manager.close();		
	}

}
