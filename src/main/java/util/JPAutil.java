package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAutil {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence_unit");
	
	
	public static EntityManager criarEntityManager() {
		
		return emf.createEntityManager();
	}
}
