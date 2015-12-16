package com.stefanini.service;

import java.util.Date;
import java.util.List;

import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.stefanini.entidade.Cargo;
import com.stefanini.util.DateUtil;
import com.stefanini.util.JPAUtil;
import com.stefanini.util.Mensagem;

public class CargoService {

	public boolean save(Cargo cargo) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		if (DateUtil.verificaDiaUtil(cargo.getRegistroValidadeInicio())) {
			manager.persist(cargo);
			manager.getTransaction().commit();
			manager.close();
			return true;
		}
		Mensagem.add("Data informada n�o � um dia util!");
		manager.close();
		return false;
	}

	public boolean update(Cargo cargo) {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		if (DateUtil.verificaDiaUtil(cargo.getDataManipulacao())) {
			if(DateUtil.verificaNovaDataInicio(cargo.getRegistroValidadeInicio(), cargo.getDataManipulacao())){
			Cargo cargoMerge = (Cargo) getCargoById(cargo.getId());
			cargoMerge.setRegistroValidadeFim(cargo.getDataManipulacao());
			manager.merge(cargoMerge);
			manager.getTransaction().commit();
			manager.close();

			Cargo cargoPersist = new Cargo();
			cargoPersist.setNome(cargo.getNome());
			cargoPersist.setRegistroValidadeInicio(cargo.getDataManipulacao());
			save(cargoPersist);
			return true;
		}else{
			Mensagem.add("Erro, nova data � anterior a cadastrada originalmente!");
			manager.close();
			return false;
		}
	}else {
		Mensagem.add("Data informada n�o � um dia util!");
		manager.close();
		return false;
	}
	}

	@SuppressWarnings("unchecked")
	public List<Cargo> listarAtivos() {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery(
				"SELECT * FROM sgr_cargo WHERE REGISTRO_VALIDADE_FIM IS NULL ORDER BY REGISTRO_VALIDADE_INICIO ASC",
				Cargo.class);
		List<Cargo> cargos = q.getResultList();
		return cargos;
	}

	public Cargo getCargoById(Long id) {
		EntityManager manager = JPAUtil.getEntityManager();
		Query q = manager.createNativeQuery("SELECT * FROM sgr_cargo WHERE ID_CARGO = :idCargo", Cargo.class);
		q.setParameter("idCargo", id);
		Cargo cargo = (Cargo) q.getSingleResult();
		manager.close();
		return cargo;
	}

	public void desativar(Long id) throws ConverterException {
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();
		Cargo cargoMerge = (Cargo) getCargoById(id);
		cargoMerge.setRegistroValidadeFim(new Date());
		manager.merge(cargoMerge);
		manager.getTransaction().commit();
		manager.close();
	}
}
