/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.db.ejb;

import heps.db.param_list.db.entity.System;
import heps.db.param_list.comman.tools.EmProvider;
import heps.db.param_list.db.entity.Leader;
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
public class LeaderFacade {

    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public Boolean validate(Leader leader) {
        if (leader != null) {
            Leader l = this.getLeader(leader.getName());
            if (l != null) {
                if (l.getPassword().equals(leader.getPassword())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Leader getLeader(String name) {
        Query q;
        q = em.createNamedQuery("Leader.findByName").setParameter("name", name);
        List<Leader> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }
}
