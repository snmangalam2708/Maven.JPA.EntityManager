package services;

import entities.Artist;
import entities.CD;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Set;

public class CDService {

    EntityManager em = null;
    EntityTransaction etx = null;

    public CDService(EntityManager em) {

        this.em = em;
        this.etx = em.getTransaction();
    }

    public CD create(String title, String description,Integer year, Set<Artist> artists){

        CD cd = new CD(title, description,year, artists);
        cd.setTitle(title);
        cd.setArtists(artists);
        cd.setDescription(description);
        cd.setYear(year);

        etx.begin();
        em.persist(cd);
        etx.commit();

        return cd;
    }

    public CD create(CD cd){

        etx.begin();
        em.persist(cd);
        etx.commit();
        return cd;
    }

    public CD findById(Long id) {

        return em.find(CD.class, id);
    }

    public List<CD> findAll() {

        return em.createQuery("SELECT z FROM CD z", CD.class).getResultList();
    }

    public void update(CD cd){

        List<CD> cdList = findAll();
        if(cdList.contains(findById(cd.getId()))){
            etx.begin();
            em.merge(cd);
            etx.commit();
        }

    }
    public void delete(Long id){

        CD cd = em.find(CD.class, id);
        if(cd != null){
            etx.begin();
            em.remove(cd);
            etx.commit();
        }

    }

    public void delete(CD cd){

        CD cdToBeDeleted = em.merge(cd);
        etx.begin();
        em.remove(cdToBeDeleted);
        etx.commit();

    }

}