/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.db.ejb;

import heps.db.param_list.comman.tools.EmProvider;
import heps.db.param_list.db.entity.Division;
import heps.db.param_list.db.entity.Subdivision;
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
public class SubdivisionFacade {

  
    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public Subdivision setSubdivision(Division div, String name) {
        Subdivision s = new Subdivision();
        s.setDivisionId(div);
        s.setName(name);
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        return s;
    }

    public Subdivision getSubdivision(Division div, String name) {
        Query q;
        q = em.createQuery("select sd from Subdivision sd  where sd.divisionId=:division and sd.name=:name").setParameter("division", div).setParameter("name", name);
        List<Subdivision> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    
    public Subdivision getSubdivision(String name) {
        Query q;
        q = em.createQuery("select sd from Subdivision sd  where sd.divisionId.name=:division and sd.name=:name").setParameter("division", "加速器总体").setParameter("name", name);
        List<Subdivision> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }
    public List<Subdivision> getAllSubdivision() {
        Query q;
        q = em.createNamedQuery("Subdivision.findAll");
        return q.getResultList();

    }
}
