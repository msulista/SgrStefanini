package com.stefanini.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.stefanini.util.DateUtil;

@Entity
@Table(name = "SGR_PROFISSIONAL")
@NamedQueries({
	@NamedQuery(name = "Profissional.findAll", query ="SELECT p FROM Profissional p ORDER BY p.nome ASC"),
	@NamedQuery(name = "Profissional.findMatriculaParaHistorico", query ="SELECT p FROM Profissional p WHERE p.matricula = :matricula ORDER BY p.registroValidadeInicio DESC"),
	@NamedQuery(name = "Profissional.findAtivos", query ="SELECT p FROM Profissional p WHERE p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),
	@NamedQuery(name = "Profissional.findNome", query ="SELECT p FROM Profissional p WHERE p.nome LIKE :nome AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),
	@NamedQuery(name = "Profissional.findId", query ="SELECT p FROM Profissional p WHERE p.id = :id AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),
	@NamedQuery(name = "Profissional.findMatricula", query ="SELECT p FROM Profissional p WHERE p.matricula = :matricula AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),
	@NamedQuery(name = "Profissional.findProfissionalByCargaHoraria", query ="SELECT p FROM Profissional p WHERE p.cargaHoraria.id = :id AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),	
	@NamedQuery(name = "Profissional.findProfissionalByCargo", query ="SELECT p FROM Profissional p WHERE p.cargo.id = :id AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),	
	@NamedQuery(name = "Profissional.findProfissionalByCelula", query ="SELECT p FROM Profissional p WHERE p.celula.id = :id AND (p.registroValidadeInicio <= CURRENT_DATE) AND (p.registroValidaeFim IS NULL OR P.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),	
	@NamedQuery(name = "Profissional.findProfissionalByCelulaNome", query ="SELECT p FROM Profissional p WHERE p.celula.nome = :nome AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),	
	@NamedQuery(name = "Profissional.findProfissionalByCelulaANDEquipe", query ="SELECT p FROM Profissional p WHERE p.celula.id = :celula AND p.equipe.nome = :equipe AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),
	@NamedQuery(name = "Profissional.findProfissionalByEquipe", query ="SELECT p FROM Profissional p WHERE p.equipe.id = :id AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),	
	@NamedQuery(name = "Profissional.findProfissionalByEquipeNome", query ="SELECT p FROM Profissional p WHERE p.equipe.nome = :nome AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),	
	@NamedQuery(name = "Profissional.findProfissionalByFormaContratacao", query ="SELECT p FROM Profissional p WHERE p.formaContratacao.id = :id AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),	
	@NamedQuery(name = "Profissional.findProfissionalByPerfil", query = "SELECT p FROM Profissional p WHERE p.perfil.id = :id AND  p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),	
	@NamedQuery(name = "Profissional.findProfissionalByStatus", query ="SELECT p FROM Profissional p WHERE p.status.id = :id AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC"),	
	@NamedQuery(name = "Profissional.checkMatriculaParaEdicao", query ="SELECT p FROM Profissional p WHERE p.id = (SELECT MAX(pr.id)FROM Profissional pr WHERE pr.matricula = :matricula AND pr.registroValidadeInicio <= CURRENT_DATE())"),
	@NamedQuery(name = "Profissional.checkPrimeiroProfissionalByMatricula", query ="SELECT p FROM Profissional p WHERE p.id = (SELECT MIN(pr.id)FROM Profissional pr WHERE pr.matricula = :matricula)"),
	@NamedQuery(name = "Profissional.findProfissionalByEquipeEContratacao", query ="SELECT p FROM Profissional p WHERE p.equipe.nome = :nome AND p.formaContratacao.nome = :serie AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC)"),
	@NamedQuery(name = "Profissional.findProfissionalByCelulaEContratacao", query ="SELECT p FROM Profissional p WHERE p.celula.nome = :nome AND p.formaContratacao.nome = :serie AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC)"),
	@NamedQuery(name = "Profissional.findProfissionalByEquipeEPerfil", query ="SELECT p FROM Profissional p WHERE p.equipe.nome = :nome AND p.perfil.nome = :serie AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC)"),
	@NamedQuery(name = "Profissional.findProfissionalByCelulaEPerfil", query ="SELECT p FROM Profissional p WHERE p.celula.nome = :nome AND p.perfil.nome = :serie AND p.registroValidadeInicio <= CURRENT_DATE AND (p.registroValidaeFim IS NULL OR p.registroValidaeFim > CURRENT_DATE) ORDER BY p.nome ASC)")

})
public class Profissional implements BaseEntity, Serializable {
	private static final long serialVersionUID = 1L;
	public Profissional() {
		this.dataAdmissao = DateUtil.getProximoDiaUtil();
		this.registroValidadeInicio = DateUtil.getProximoDiaUtil();
		this.dataManipulacao = DateUtil.getProximoDiaUtil();
	}
	
	@Transient
	private Date dataManipulacao;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PROFISSIONAL", nullable = false, precision = 32)
	private Long id;
	
	@Column(name = "NOME", nullable = false)
	private String nome;
	
	@Column(name = "MATRICULA", nullable = false)
	private int matricula;
	
	@Column(name = "SALARIO", nullable = false)
	private Double salario = null;
	
	@Column(name="BENEFICIOS", nullable = true)
	private Double beneficios;
	
	@Column(name = "VALOR_HORA", nullable = false)
	private Double valorHora;
	
	@Column(name = "DATA_ADMISSAO", nullable = false)
	private Date dataAdmissao;
	
	@Column(name = "DATA_DEMISSAO", nullable = true)
	private Date dataDemissao;
	
	@Column(name = "REGISTRO_VALIDADE_INICIO", nullable = false)
	private Date registroValidadeInicio;
	
	@Column(name = "REGISTRO_VALIDADE_FIM", nullable = true)
	private Date registroValidaeFim;
	
	@JoinColumn(name = "ID_CELULA", referencedColumnName = "ID_CELULA")
	@ManyToOne
	private Celula celula;
	
	@JoinColumn(name = "ID_EQUIPE", referencedColumnName = "ID_EQUIPE")
	@ManyToOne
	private Equipe equipe;
	
	@JoinColumn(name = "ID_CARGO", referencedColumnName = "ID_CARGO")
	@ManyToOne
	private Cargo cargo;
	
	@JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID_PERFIL")
	@ManyToOne
	private Perfil perfil;
	
	@JoinColumn(name = "ID_CARGA_HORARIA", referencedColumnName = "ID_CARGA_HORARIA")
	@ManyToOne
	private CargaHoraria cargaHoraria;
	
	@JoinColumn(name = "ID_FORMA_CONTRATACAO", referencedColumnName = "ID_FORMA_CONTRATACAO")
	@ManyToOne
	private FormaContratacao formaContratacao;
	
	@JoinColumn(name = "ID_STATUS", referencedColumnName = "ID_STATUS")
	@ManyToOne
	private Status status;
	
	@Column(name = "DATA_SAIDA", nullable=true)
	private Date dataSaida;
	
	@Column(name = "DATA_RETORNO", nullable=true)
	private Date dataRetorno;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public int getMatricula() {
		return matricula;
	}

	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Double getBeneficios() {
		return beneficios;
	}

	public void setBeneficios(Double beneficios) {
		this.beneficios = beneficios;
	}

	public Double getValorHora() {
		return valorHora;
	}

	public void setValorHora(Double valorHora) {
		this.valorHora = valorHora;
	}

	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	public Date getDataDemissao() {
		return dataDemissao;
	}

	public void setDataDemissao(Date dataDemissao) {
		this.dataDemissao = dataDemissao;
	}

	public Date getRegistroValidadeInicio() {
		return registroValidadeInicio;
	}

	public void setRegistroValidadeInicio(Date registroValidadeInicio) {
		this.registroValidadeInicio = registroValidadeInicio;
	}

	public Date getRegistroValidaeFim() {
		return registroValidaeFim;
	}

	public void setRegistroValidaeFim(Date registroValidaeFim) {
		this.registroValidaeFim = registroValidaeFim;
	}

	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public CargaHoraria getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(CargaHoraria cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public FormaContratacao getFormaContratacao() {
		return formaContratacao;
	}

	public void setFormaContratacao(FormaContratacao formaContratacao) {
		this.formaContratacao = formaContratacao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}	

	public Date getDataManipulacao() {
		return dataManipulacao;
	}

	public void setDataManipulacao(Date dataManipulacao) {
		this.dataManipulacao = dataManipulacao;
	}

	public Date getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(Date dataSaida) {
		this.dataSaida = dataSaida;
	}

	public Date getDataRetorno() {
		return dataRetorno;
	}

	public void setDataRetorno(Date dataRetorno) {
		this.dataRetorno = dataRetorno;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beneficios == null) ? 0 : beneficios.hashCode());
		result = prime * result + ((cargaHoraria == null) ? 0 : cargaHoraria.hashCode());
		result = prime * result + ((cargo == null) ? 0 : cargo.hashCode());
		result = prime * result + ((celula == null) ? 0 : celula.hashCode());
		result = prime * result + ((dataAdmissao == null) ? 0 : dataAdmissao.hashCode());
		result = prime * result + ((dataDemissao == null) ? 0 : dataDemissao.hashCode());
		result = prime * result + ((dataRetorno == null) ? 0 : dataRetorno.hashCode());
		result = prime * result + ((dataSaida == null) ? 0 : dataSaida.hashCode());
		result = prime * result + ((equipe == null) ? 0 : equipe.hashCode());
		result = prime * result + ((formaContratacao == null) ? 0 : formaContratacao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + matricula;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((perfil == null) ? 0 : perfil.hashCode());
		result = prime * result + ((registroValidadeInicio == null) ? 0 : registroValidadeInicio.hashCode());
		result = prime * result + ((registroValidaeFim == null) ? 0 : registroValidaeFim.hashCode());
		result = prime * result + ((salario == null) ? 0 : salario.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((valorHora == null) ? 0 : valorHora.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Profissional other = (Profissional) obj;
		if (beneficios == null) {
			if (other.beneficios != null)
				return false;
		} else if (!beneficios.equals(other.beneficios))
			return false;
		if (cargaHoraria == null) {
			if (other.cargaHoraria != null)
				return false;
		} else if (!cargaHoraria.equals(other.cargaHoraria))
			return false;
		if (cargo == null) {
			if (other.cargo != null)
				return false;
		} else if (!cargo.equals(other.cargo))
			return false;
		if (celula == null) {
			if (other.celula != null)
				return false;
		} else if (!celula.equals(other.celula))
			return false;
		if (dataAdmissao == null) {
			if (other.dataAdmissao != null)
				return false;
		} else if (!dataAdmissao.equals(other.dataAdmissao))
			return false;
		if (dataDemissao == null) {
			if (other.dataDemissao != null)
				return false;
		} else if (!dataDemissao.equals(other.dataDemissao))
			return false;
		if (dataRetorno == null) {
			if (other.dataRetorno != null)
				return false;
		} else if (!dataRetorno.equals(other.dataRetorno))
			return false;
		if (dataSaida == null) {
			if (other.dataSaida != null)
				return false;
		} else if (!dataSaida.equals(other.dataSaida))
			return false;
		if (equipe == null) {
			if (other.equipe != null)
				return false;
		} else if (!equipe.equals(other.equipe))
			return false;
		if (formaContratacao == null) {
			if (other.formaContratacao != null)
				return false;
		} else if (!formaContratacao.equals(other.formaContratacao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (matricula != other.matricula)
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (perfil == null) {
			if (other.perfil != null)
				return false;
		} else if (!perfil.equals(other.perfil))
			return false;
		if (registroValidadeInicio == null) {
			if (other.registroValidadeInicio != null)
				return false;
		} else if (!registroValidadeInicio.equals(other.registroValidadeInicio))
			return false;
		if (registroValidaeFim == null) {
			if (other.registroValidaeFim != null)
				return false;
		} else if (!registroValidaeFim.equals(other.registroValidaeFim))
			return false;
		if (salario == null) {
			if (other.salario != null)
				return false;
		} else if (!salario.equals(other.salario))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (valorHora == null) {
			if (other.valorHora != null)
				return false;
		} else if (!valorHora.equals(other.valorHora))
			return false;
		return true;
	}

	
	
	
}
