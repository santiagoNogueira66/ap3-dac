package bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import DAO.CampeonatoDAO;
import DAO.JogoDAO;

import entidades.Campeonato;
import entidades.Jogo;

import org.primefaces.event.RowEditEvent;

@ManagedBean
public class JogoBean {

	private Campeonato campeonato = new Campeonato(); // instancia da entidade campeonato
	private Jogo jogo = new Jogo(); // instancia entidade jogo
	private List<Jogo> lista; // array list com a entidade jogo
	private List<Campeonato> campeonatos; // array list com a entidade campeonato
	private Integer campeonatoId;
	private String timeSelecionado;

	// inicializa a classe com a lista de jogos e campeonatos
	@PostConstruct
	public void init() {
		lista = JogoDAO.listar();
		campeonatos = CampeonatoDAO.listar();
	}

	public void salvar() {
		try {
			// verifica se o time 1 e igual ao time 2
			if (jogo.getTime1().equals(jogo.getTime2())) {

				// se for ele lança uma exceção que é capturada pelo catch
				throw new Exception("Os times não podem ser iguais.");
			}

			// crio uma instancia da entidade campeonato e o valor dela o id do campeonato
			Campeonato campeonato = CampeonatoDAO.buscarPorId(campeonatoId);

			// passando a instancia campeonato para a variavel campeonato da entidade jogo
			jogo.setCampeonato(campeonato);

			// chama o metodo de salvar do jogoDao com o parametro objeto jogo
			JogoDAO.salvar(jogo);

			/* reseta o objeto jogo, 
			serve para limpar os inputs do formulario*/
			jogo = new Jogo();
			
			lista = null; //força a atualização da lista

			
			//mensagem pro usuario
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Jogo salvo com sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao salvar o jogo: " + e.getMessage()));
			//caso exista erro, o mesmo e capturado e enviado em uma mensagem
		}
	}

	public List<Jogo> getLista() {
		if (lista == null) { //se a lista for nula
			JogoDAO.listar(); //chama a listagem do jogoDao
		}
		return lista;
	}

	public void editar(RowEditEvent event) { // RowEditEvent parametro do xhtml
		Jogo jogoEditado = (Jogo) event.getObject(); //obtem o objeto que está sendo editado

		try {
			//verifica se os times são iguais
			if (jogoEditado.getTime1().equals(jogoEditado.getTime2())) {
				
				throw new Exception("Os times não podem ser iguais.");
			}

			//chama o editar da jogoDao
			JogoDAO.editar(jogoEditado);

			//força a atualização da lista
			lista = JogoDAO.listar();

			//mensagem pro usuario
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Jogo editado com sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao editar o jogo: " + e.getMessage()));
			//caso exista erro, o mesmo e capturado e enviado em uma mensagem
		}
	}

	public void excluir(Jogo jogo) {
		try {
			JogoDAO.excluir(jogo); //remove o jogo do banco de dados 

			lista.remove(jogo); //remove o jogo da lista local (lista feita pelo array list)

			//mensagem pro usuario
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Jogo excluído com sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao excluir o jogo: " + e.getMessage()));
			//caso exista erro, o mesmo e capturado e enviado em uma mensagem
		}
	}

	public void resumo() {
		//array list de 3 possições com times recebendo a,b, c
		List<String> times = Arrays.asList("A", "B", "C");
		//array list de 3 posições, com os valores em 0
		// ps: todos sao iguais
		List<Integer> pontuacao = new ArrayList<>(Collections.nCopies(3, 0));
		List<Integer> numVitorias = new ArrayList<>(Collections.nCopies(3, 0));
		List<Integer> numDerrotas = new ArrayList<>(Collections.nCopies(3, 0));
		List<Integer> numEmpates = new ArrayList<>(Collections.nCopies(3, 0));
		List<Integer> golsMarcados = new ArrayList<>(Collections.nCopies(3, 0));
		List<Integer> golsSofridos = new ArrayList<>(Collections.nCopies(3, 0));
		List<Integer> saldoGols = new ArrayList<>(Collections.nCopies(3, 0));

		//verifica se a lista não é nula ou vazia
		if (lista != null && !lista.isEmpty()) {// isEmpty, Retorna verdadeiro se esta lista não contiver elementos.
			
			//salve tudo da minha lista no jogo
			for (Jogo jogo : lista) {
				
				//retorna o nome do time convertido para maisculo
				int indexTime1 = times.indexOf(jogo.getTime1().toUpperCase());
				int indexTime2 = times.indexOf(jogo.getTime2().toUpperCase());

				//verifica se o time 1 está na lista
				if (indexTime1 != -1) {
					
					//se os gols do time 1 for MAIOR que o do time 2
					if (jogo.getGolsTime1() > jogo.getGolsTime2()) {
						
						//aumentamos em 1 o contado de vitorias
						numVitorias.set(indexTime1, numVitorias.get(indexTime1) + 1);
						
						//aumentamos em 3 a pontuacao
						pontuacao.set(indexTime1, pontuacao.get(indexTime1) + 3);
						
					// se os gols do time 1 for MENOR que o do time 2	
					} else if (jogo.getGolsTime1() < jogo.getGolsTime2()) {
						
						//aumentamos em 1 o contado de derrotas
						numDerrotas.set(indexTime1, numDerrotas.get(indexTime1) + 1);

					} else {
						/* caso nem uma das alternativas anteriores,
						  isso significa que o os gols do time 1 são IGUAIS ao do time 2*/
						
						//aumentamos em 1 o contado de empantes
						numEmpates.set(indexTime1, numEmpates.get(indexTime1) + 1);
						
						//aumentamos em 1 a pontuacao
						pontuacao.set(indexTime1, pontuacao.get(indexTime1) + 1);
					}
					//pegamos o total de gols marcados 
					golsMarcados.set(indexTime1, golsMarcados.get(indexTime1) + jogo.getGolsTime1());
					
					//pegamos o total de gols sofridos
					golsSofridos.set(indexTime1, golsSofridos.get(indexTime1) + jogo.getGolsTime2());
				}

				if (indexTime2 != -1) {
					if (jogo.getGolsTime2() > jogo.getGolsTime1()) {
						numVitorias.set(indexTime2, numVitorias.get(indexTime2) + 1);
						pontuacao.set(indexTime2, pontuacao.get(indexTime2) + 3);
					} else if (jogo.getGolsTime2() < jogo.getGolsTime1()) {
						numDerrotas.set(indexTime2, numDerrotas.get(indexTime2) + 1);
					} else {
						numEmpates.set(indexTime2, numEmpates.get(indexTime2) + 1);
						pontuacao.set(indexTime2, pontuacao.get(indexTime2) + 1);
					}
					golsMarcados.set(indexTime2, golsMarcados.get(indexTime2) + jogo.getGolsTime2());
					golsSofridos.set(indexTime2, golsSofridos.get(indexTime2) + jogo.getGolsTime1());
				}
			}

			//calculo o saldo de gols
			for (int i = 0; i < times.size(); i++) {
				saldoGols.set(i, golsMarcados.get(i) - golsSofridos.get(i));
			}

			//cria a mensagem com os valores do resumo
			for (int i = 0; i < times.size(); i++) {
				FacesMessage message = new FacesMessage("Estatísticas do Time " + times.get(i),
						"Pontuação: " + pontuacao.get(i) + ", Vitórias: " + numVitorias.get(i) + ", Derrotas: "
								+ numDerrotas.get(i) + ", Empates: " + numEmpates.get(i) + ", Gols Marcados: "
								+ golsMarcados.get(i) + ", Gols Sofridos: " + golsSofridos.get(i) + ", Saldo de Gols: "
								+ saldoGols.get(i));

				//exibe a mensagem
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		} else {
			//cria a mensagem
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info",
					"A lista de jogos está vazia ou nula.");
			
			//exibe a mensagem
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void localizar() {
		try {
			//colocando na lista de jogos o valor retornado pelo metodo selecionar todos os jogos
			lista = JogoDAO.selecionarTodoJogos(timeSelecionado);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Erro ao localizar jogos: " + e.getMessage()));
		}
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public List<Campeonato> getCampeonatos() {
		return campeonatos;
	}

	public void setCampeonatos(List<Campeonato> campeonatos) {
		this.campeonatos = campeonatos;
	}

	public Integer getCampeonatoId() {
		return campeonatoId;
	}

	public void setCampeonatoId(Integer campeonatoId) {
		this.campeonatoId = campeonatoId;
	}

	public String getTimeSelecionado() {
		return timeSelecionado;
	}

	public void setTimeSelecionado(String timeSelecionado) {
		this.timeSelecionado = timeSelecionado;
	}

	public void setLista(List<Jogo> lista) {
		this.lista = lista;
	}

}
