package DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidades.Jogo;
import util.JPAutil;

public class JogoDAO {

	public static void salvar(Jogo jogo) {
		EntityManager em = JPAutil.criarEntityManager(); //instancia do jpa
		em.getTransaction().begin(); //inicia a transação
		em.merge(jogo); //prepara o salvamento
		em.getTransaction().commit(); //envia a transação
		em.close(); //fecha a transação
	}

	//crio uma lista do objeto jogo
	public static List<Jogo> listar() {
		EntityManager em = JPAutil.criarEntityManager(); //instancia do jpa
		Query query = em.createQuery("SELECT j FROM Jogo j"); //cria a query
		List<Jogo> resultado = query.getResultList(); //pega o resultado da query 
		return resultado; //retorna o resultado//
	}

	public static void editar(Jogo jogo) {
		EntityManager em = JPAutil.criarEntityManager(); //instancia do jpa
		em.getTransaction().begin(); //inicia a transação
		em.merge(jogo); //prepara o salvamento
		em.flush(); //sicroniza os dados que estão no estado de salvamento
		em.getTransaction().commit(); //envia a transação
		em.close(); //fecha a transação 
	}

	public static void excluir(Jogo jogo) {
	    try {
	    	EntityManager em = JPAutil.criarEntityManager(); //instancia do jpa
	        em.getTransaction().begin(); //inicia a transação
	        jogo.setCampeonato(null); //desvincula o jogo do campeonato
	        Jogo jogoParaExcluir = em.merge(jogo); // Mescla o objeto jogo com o contexto de salvamento
	        em.remove(jogoParaExcluir); // Remove o jogo do banco de dados
	        em.getTransaction().commit(); //envia a transação
	        em.close(); //fecha a transação
	    } catch (Exception e) {
	        System.err.println("Erro ao excluir jogo: " + e);
	        //captura e exibe o erro, caso haja algum
	    }
	}

	 public static List<Jogo> selecionarTodoJogos(String time) {
	        EntityManager em = JPAutil.criarEntityManager();
	        Query q = em.createNamedQuery("Jogo.buscarJogos");//onde criamos o apelido da query
	        q.setParameter("team", time); //parametros para a query
	        List<Jogo> resultado = q.getResultList(); //pegando o resultado da query
	        em.close(); //fecha a transação
	        return resultado; //retorna o resultado
	    }
}
