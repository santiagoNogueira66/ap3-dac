package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Campeonato;
import util.JPAutil;

public class CampeonatoDAO {

	public static void salvar(Campeonato campeonato) {
		EntityManager em = JPAutil.criarEntityManager(); //instanticia do jpa
		em.getTransaction().begin(); //inicia a transação
		em.merge(campeonato); //prepara o salvamento 
		em.getTransaction().commit(); //envia a transação
		em.close(); //fecha a transação
	}

	public static List<Campeonato> listar() {
		EntityManager em = JPAutil.criarEntityManager(); //instancia do jpa
		Query q = em.createQuery("SELECT c FROM Campeonato c"); 
		//criação da query que seleciona todos os campos da tabela campeonato
		List<Campeonato> resultado = q.getResultList(); //pegando o resultado da query
		return resultado; //retornando o resultado
	}

	public static Campeonato buscarPorId(Integer id) {
		EntityManager em = JPAutil.criarEntityManager();
		Campeonato campeonato = null; //cria a variavel campeonato com o valor nulo
		try {
			campeonato = em.find(Campeonato.class, id); //procura o id do campeonato
		} finally {
			em.close(); // fecha a transação
		}
		return campeonato; //retorna o id do campeonato
	}

	public static void editar(Campeonato campeonato) {
		EntityManager em = JPAutil.criarEntityManager(); //instancia jpa util
		em.getTransaction().begin(); //inicia a transação
		em.merge(campeonato); //prepara o salvamento
		em.flush(); // sicroniza os dados que estão no estado de salvamento
		em.getTransaction().commit(); //envia a transação
		em.close(); //fecha a transação
	}

	public static void excluir(Campeonato campeonato) {
		EntityManager em = JPAutil.criarEntityManager(); //instancia jpa
		em.getTransaction().begin(); //inicia a trasação
		campeonato = em.find(Campeonato.class, campeonato.getId()); //procura o id do campeonato
		if (campeonato != null) { //se campenato for diferente de nulo
			em.remove(campeonato); //remova o campeonato
		}
		em.getTransaction().commit(); //envia a transação
		em.close(); // fecha a transação
	}
}
