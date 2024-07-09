package entidades;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQuery(name = "Jogo.buscarJogos", query = "SELECT j FROM Jogo j WHERE j.time1 = :team OR j.time2 = :team")
//selecione da tabela jogo o time 1 ou time 2 que forem iguais a time
@Entity
public class Jogo {

	@Id //indica chave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) //gera automaticamente o valor do id
	private Integer id;
	@Temporal(TemporalType.DATE) //serve para pegar apenas a data
	private Date dataPartida;
	@Temporal(TemporalType.DATE)
	private Date dataCadastro = new Date(); //preenche automaticamente o valor da dataCadastro
	@Column(nullable = false)
	private String time1;
	@Column(nullable = false)
	private String time2;
	/*@ManyToOne relacionamento de um pra muitos
	 cascade type all tudo que eu fizer no campeonato se repete no jogo
	*/
	@ManyToOne(cascade = CascadeType.ALL)
	private Campeonato campeonato;
	private Integer golsTime1;
	private Integer golsTime2;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDataPartida() {
		return dataPartida;
	}

	public void setDataPartida(Date dataPartida) {
		this.dataPartida = dataPartida;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	public Integer getGolsTime1() {
		return golsTime1;
	}

	public void setGolsTime1(Integer golsTime1) {
		this.golsTime1 = golsTime1;
	}

	public Integer getGolsTime2() {
		return golsTime2;
	}

	public void setGolsTime2(Integer golsTime2) {
		this.golsTime2 = golsTime2;
	}

}