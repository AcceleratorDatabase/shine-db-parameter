/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.db.ejb;

import heps.db.param_list.db.entity.Parameter;
import heps.db.param_list.db.entity.Reference;
import heps.db.param_list.db.entity.Unit;
import heps.db.param_list.comman.tools.EmProvider;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Lvhuihui
 */
@Stateless
public class ParameterFacade {

    /*@PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("param_listPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext*/
    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public void setParameter(String name, Unit unit, String definition, Date date_modified, Reference reference) {
        Parameter p = new Parameter();
        p.setName(name);
        p.setUnitid(unit);
        p.setDefinition(definition);
        p.setDatemodified(date_modified);
        p.setReferenceid(reference);
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public Parameter setParameter(Parameter p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        return p;
    }

    public Parameter getParameterById(int id) {
        Query q;
        return em.find(Parameter.class, id);
    }

    public Parameter getParameterByName(String name) {
        Query q;
        q = em.createNamedQuery("Parameter.findByName").setParameter("name", name);
        List<Parameter> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    public Parameter getParameterBy(String name, String unit, Date date_modified, String def, String ref) {
        Query q = null;

        StringBuilder sql = new StringBuilder("select * from parameter p where 1=1 ");
        if (name != null && (!"".equals(name))) {
            sql.append(" and name=" + "'" + name + "'");
        }
        if (unit != null && (!"".equals(unit))) {
            sql.append(" and unit_id=" + new UnitFacade().getUnit(unit).getId());
        }
        if (date_modified != null && (!"".equals(date_modified))) {
            LocalDate localDate = date_modified.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            date_modified = java.sql.Date.valueOf(localDate);
            sql.append(" and date_modified=" + "'" + date_modified + "'");
        }

        if (def != null && (!"".equals(def))) {
            sql.append(" and definition=" + "'" + def + "'");
        }
        if (ref != null && (!"".equals(ref))) {
            sql.append(" and reference_id=" + new ReferenceFacade().getReferenceByTitle(ref).getId());
        }
        q = em.createNativeQuery(sql.toString(), Parameter.class);

        //System.out.println("sql语句：" + sql);
        List<Parameter> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
    }

    public List<Parameter> getAllParameter() {
        Query q;
        q = em.createNamedQuery("Parameter.findAll");
        return q.getResultList();
    }
}
