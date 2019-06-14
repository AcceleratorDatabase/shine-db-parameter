/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.db.ejb;


import heps.db.param_list.comman.tools.EmProvider;
import heps.db.param_list.db.entity.Division;
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
public class DivisionFacade {

   
    public static EntityManager em=EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public Division setDivision(String name) {
        Division s = new Division();
        s.setName(name);
        //System.out.println(em.getProperties());
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        return s;
    }

    public Division getDivision(String name) {
        Query q;
        q = em.createNamedQuery("Division.findByName").setParameter("name", name);      
        List<Division> l = q.getResultList();
        
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }
    
    public List<Division> getAllDivision(){
        Query q;
        q=em.createNamedQuery("Division.findAll");
        return  q.getResultList();
    
    }
}
