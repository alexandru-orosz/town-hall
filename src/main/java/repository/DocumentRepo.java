package repository;

import entity.Document;
import queries.DocumentQueries;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class DocumentRepo {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("town_hall");

    public void insertNewDocument(Document document){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.persist(document);
        em.getTransaction().commit();
        em.close();
    }

    public void updateDocument(Document document){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(document);
        em.getTransaction().commit();
        em.close();
    }

    public void deleteDocument(Document document){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(document) ? document : em.merge(document));
        em.getTransaction().commit();
        em.close();
    }

    public Document findDocumentById(String id_document){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(DocumentQueries.FIND_BY_ID_DOCUMENT);
        query.setParameter("id_document", id_document);
        Document document = (Document) query.getResultList().get(0);
        em.close();
        return document;
    }

    public Document findDocumentByName(String name){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(DocumentQueries.FIND_BY_NAME);
        query.setParameter("name", name);
        Document document = (Document) query.getResultList().get(0);
        em.close();
        return document;
    }

    public List<Document> getAllDocuments(){
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(DocumentQueries.GET_ALL_DOCUMENTS);
        List documents = query.getResultList();
        em.close();
        return documents;
    }
}
