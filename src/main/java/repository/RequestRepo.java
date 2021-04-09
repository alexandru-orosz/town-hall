package repository;

import entity.Request;
import queries.RequestQueries;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class RequestRepo {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("town_hall");

    public void insertNewRequest(Request request){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(request);
        em.getTransaction().commit();
        em.close();
    }

    public void updateRequest(Request request){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(request);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteRequest(Request request){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(request) ? request : em.merge(request));
        em.getTransaction().commit();
        em.close();
    }

    public Request findRequestById(String id_request){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(RequestQueries.FIND_BY_ID_REQUEST);
        query.setParameter("id_request", id_request);
        Request request = (Request) query.getResultList().get(0);
        em.close();
        return request;
    }

    public List<Request> getAllRequests(){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(RequestQueries.GET_ALL_REQUESTS);
        List requests = query.getResultList();
        em.close();
        return requests;
    }

}
