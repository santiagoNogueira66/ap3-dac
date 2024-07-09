package bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.RowEditEvent;

import DAO.CampeonatoDAO;
import entidades.Campeonato;

@ManagedBean
public class CampeonatoBean {

	Campeonato campeonato = new Campeonato();
	List<Campeonato> lista;

	@PostConstruct // serve pra dizer que vai ser a primeira coisa inicializada
    public void init() {
        lista = CampeonatoDAO.listar();
    }
	public void salvar() {
		CampeonatoDAO.salvar(campeonato);
		campeonato = new Campeonato();
		lista = null;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "info", "Campeonato salvo com sucesso"));
	}

	public List<Campeonato> getLista() {
		if (lista == null) {
			lista = CampeonatoDAO.listar();
		}
		return lista;
	}

	public void editar(RowEditEvent event) {
		Campeonato campeonatoEditado = (Campeonato) event.getObject();
		CampeonatoDAO.editar(campeonatoEditado);
		lista = null;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "info", "Jogo editado com sucesso"));
	}

	public void excluir(Campeonato campeonato) {
		CampeonatoDAO.excluir(campeonato);
		lista = null;
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "info", "Jogo excluido com sucesso"));
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	public void setLista(List<Campeonato> lista) {
		this.lista = lista;
	}

}
