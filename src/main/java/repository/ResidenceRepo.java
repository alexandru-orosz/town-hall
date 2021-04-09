package repository;

import entity.Residence;
import entity.User;
import queries.ResidenceQueries;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class ResidenceRepo {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("town_hall");

    public void insertNewResidence(Residence residence){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(residence);
        em.getTransaction().commit();
        em.close();
    }

    public void updateResidence(Residence residence){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(residence);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteResidence(Residence residence){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(residence) ? residence : em.merge(residence));
        em.getTransaction().commit();
        em.close();
    }

    public Residence findResidenceById(String id_residence){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(ResidenceQueries.FIND_BY_ID_RESIDENCE);
        query.setParameter("id_residence", id_residence);
        Residence residence = (Residence) query.getResultList().get(0);
        em.close();
        return residence;
    }

    public Residence findResidenceByNameAndUser(String name, User user){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(ResidenceQueries.GET_USER_RESIDENCE_BY_NAME);
        query.setParameter("name", name);
        query.setParameter("user", user);
        Residence residence = (Residence) query.getResultList().get(0);
        em.close();
        return residence;
    }

    public List<Residence> getAllResidences(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(ResidenceQueries.GET_ALL_USER_RESIDENCES);
        query.setParameter("user", user);
        List residences = query.getResultList();
        em.close();
        return residences;
    }
}
