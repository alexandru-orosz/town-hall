package repository;

import entity.User;
import queries.UserQueries;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class UserRepo {

	private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("town_hall");

	public void insertNewUser(User user) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
	}

	public void updateUser(User user) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.merge(user);
		em.getTransaction().commit();
		em.close();
	}

	public void deleteUser(User user) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.remove(em.contains(user) ? user : em.merge(user));
		em.getTransaction().commit();
		em.close();
	}

	public User findUserById(String id_user) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery(UserQueries.FIND_BY_ID_USER);
		query.setParameter("id_user", id_user);
		User user = (User) query.getResultList().get(0);
		em.close();
		return user;
	}

	public User findUserByName(String username) {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery(UserQueries.FIND_BY_USERNAME);
		query.setParameter("username", username);
		List results = query.getResultList();
		em.close();

		if (results.isEmpty()){
			return null;
		}
		return (User)results.get(0);
	}

	public List getAllEmails() {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery(UserQueries.GET_ALL_EMAILS);
		List emails = query.getResultList();
		em.close();
		return emails;
	}

	public List<User> getAllUsers(){
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Query query = em.createQuery(UserQueries.GET_ALL_USERS);
		List users = query.getResultList();
		em.close();
		return  users;
	}
}
