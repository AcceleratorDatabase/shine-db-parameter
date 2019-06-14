/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.db.ejb;

import heps.db.param_list.db.entity.System;
import heps.db.param_list.comman.tools.EmProvider;
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
public class SystemFacade {

    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public System setSystem(Subdivision subdiv, String name) {
        System s = new System();
        s.setSubdivisionId(subdiv);
        s.setName(name);
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        return s;
    }

    public System getSystem(Subdivision subdiv, String name) {
        Query q;
        q = em.createQuery("select s from System s where s.subdivisionId=:subdivision and s.name=:name").setParameter("subdivision", subdiv).setParameter("name", name);
        List<System> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }
    
    public System getSystem( String name) {
        Query q;
        q = em.createNamedQuery("System.findByName").setParameter("name", name);
        List<System> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    public List<System> getAllSystem() {
        Query q;
        q = em.createNamedQuery("System.findAll");
        return q.getResultList();

    }
}
