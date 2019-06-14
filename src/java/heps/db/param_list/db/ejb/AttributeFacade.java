/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.db.ejb;

import heps.db.param_list.db.entity.Attribute;
import heps.db.param_list.comman.tools.EmProvider;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Lvhuihui
 */
@Stateless
public class AttributeFacade {

    /* @PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("param_listPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext*/
    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public Attribute setAttribute(String name) {
        Attribute a = new Attribute();
        a.setName(name);
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        return a;
    }

    public Attribute getAttribute(String name) {
        Query q;
        q = em.createNamedQuery("Attribute.findByName").setParameter("name", name);
        List<Attribute> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    public List<Attribute> getAllAttribute() {
        Query q;
        q = em.createNamedQuery("Attribute.findAll");
        return q.getResultList();
    }

}
