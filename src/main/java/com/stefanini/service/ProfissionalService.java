package com.stefanini.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Profissional;
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

	public boolean save(Profissional profissional) {
		EntityManager manager = JPAUtil.getEntityManager();

		if (verificaDiaUtil(profissional.getRegistroValidadeInicio())) {

			manager.getTransaction().begin();
			manager.persist(profissional);
			manager.getTransaction().commit();
			manager.close();
			return true;
		} else {
			Mensagem.add("Data informada não é um dia util!");
			manager.close();
			return false;
		}
	}

	public boolean update(Profissional profissional) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		if (verificaDiaUtil(profissional.getRegistroValidadeInicio())) {
			Profissional profissionalMerge = (Profissional) getProfissionalById(profissional.getId());
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
			profissionalPersist
					.setCargaHoraria(cargaHorariaService.getCargaHorariaById(profissional.getCargaHoraria().getId()));
			profissionalPersist.setFormaContratacao(
					formaContratacaoService.getFormaContratacaoById(profissional.getFormaContratacao().getId()));
			profissionalPersist.setStatus(statusService.getStatusById(profissional.getStatus().getId()));

			save(profissionalPersist);
			return true;
		} else {
			Mensagem.add("Data informada não é um dia util!");
			manager.close();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Profissional> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery(
				"SELECT * FROM sgr_profissional WHERE REGISTRO_VALIDADE_FIM IS NULL ORDER BY REGISTRO_VALIDADE_INICIO ASC",
				Profissional.class);
		List<Profissional> profissionais = q.getResultList();
		return profissionais;
	}

	public Profissional getProfissionalById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_profissional WHERE ID_PROFISSIONAL = :idProfissional",
				Profissional.class);
		q.setParameter("idProfissional", id);
		Profissional profissional = (Profissional)q.getSingleResult();
		manager.close();
		return profissional;
	}

	public void desativar(Long id) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		Profissional profissionalDesativar = (Profissional) getProfissionalById(id);
		profissionalDesativar.setRegistroValidaeFim(new Date());
		manager.merge(profissionalDesativar);
		manager.getTransaction().commit();
		manager.close();
	}

	public boolean verificaDiaUtil(Date data) {
		GregorianCalendar calendar = new GregorianCalendar();

		calendar.setTime(data);

		if (calendar.get(GregorianCalendar.DAY_OF_WEEK) == 1 || calendar.get(GregorianCalendar.DAY_OF_WEEK) == 7) {
			return false;
		} else {
			return true;

		}
	}

}
