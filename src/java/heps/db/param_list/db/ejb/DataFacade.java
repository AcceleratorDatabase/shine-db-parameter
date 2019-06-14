/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.db.ejb;

import heps.db.param_list.db.entity.Attribute;
import heps.db.param_list.db.entity.Data;
import heps.db.param_list.db.entity.HistoryData;
import heps.db.param_list.db.entity.Parameter;
import heps.db.param_list.db.entity.Version;
import heps.db.param_list.comman.tools.EmProvider;
import heps.db.param_list.db.entity.Reference;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Lvhuihui
 */
@Stateless
public class DataFacade{

    /*@PersistenceUnit
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("param_listPU");
    public static EntityManager em = emf.createEntityManager();

    @PersistenceContext*/
    public static EntityManager em = EmProvider.getInstance().getEntityManagerFactory().createEntityManager();

    public List<Data> getAllData() {
        Query q;
        q = em.createNamedQuery("Data.findAll");
        System.out.println(q.getResultList().size());
        return q.getResultList();
    }

    public void setData(Data data) {
        em.getTransaction().begin();
        Parameter parameter = data.getParameterid();
        ParameterFacade parameterFacade = new ParameterFacade();

        String unit_name = data.getParameterid().getUnitid().getName();
        String title = data.getParameterid().getReferenceid().getTitle();
        String author = data.getParameterid().getReferenceid().getAuthor();
        String publication = data.getParameterid().getReferenceid().getPublication();
        String url = data.getParameterid().getReferenceid().getUrl();

        if (unit_name != null && (!"".equals(unit_name))) {
            UnitFacade unitFacade = new UnitFacade();
            if (unitFacade.getUnit(unit_name) == null) {
                parameter.setUnitid(unitFacade.setUnit(unit_name));
            } else {
                parameter.setUnitid(unitFacade.getUnit(unit_name));
            }
        } else {
            parameter.setUnitid(null);
        }
        if (!((title == null || "".equals(title)) && (author == null || "".equals(author))
                && (publication == null || "".equals(publication)) && (url == null || "".equals(url)))) {
            ReferenceFacade referenceFacade = new ReferenceFacade();
            Reference r = referenceFacade.getReferenceByTitle(title);
            if (r == null) {
                parameter.setReferenceid(referenceFacade.setReference(title, author, publication, url, null));
            } else {
                parameter.setReferenceid(r);
            }
        } else {
            parameter.setReferenceid(null);
        }
        Parameter p = parameterFacade.getParameterBy(data.getParameterid().getName(), unit_name, null, data.getParameterid().getDefinition(), title);

        if (p == null) {
            System.out.println("**********");

            data.setParameterid(parameterFacade.setParameter(parameter));
        } else {
            data.setParameterid(p);
        }

        String attribute_name = data.getAttributeid().getName();
        AttributeFacade attributeFacade = new AttributeFacade();
        Attribute a = attributeFacade.getAttribute(attribute_name);
        if (a == null) {
            data.setAttributeid(attributeFacade.setAttribute(attribute_name));
        } else {
            data.setAttributeid(a);
        }

        em.persist(data);
        em.getTransaction().commit();
    }

    public void updateData(Data newData) {
        em.getTransaction().begin();
        em.merge(newData);
        em.getTransaction().commit();
    }

    public void deleteData(Data data) {
        List<HistoryData> hList = data.getHistoryDataList();
        em.getTransaction().begin();
        if (hList.isEmpty() || hList == null) {
        } else {
            Iterator it = hList.iterator();
            while (it.hasNext()) {
                HistoryData hData = (HistoryData) it.next();
                em.remove(em.merge(hData));
            }
        }
        data = em.find(Data.class, data.getId());
        data.setHistoryDataList(null);
        em.remove(em.merge(data));
        em.getTransaction().commit();
    }

    public Data find(int id) {
        Query q = em.createNamedQuery("Data.findById").setParameter("id", id);
        List<Data> dList = q.getResultList();
        if (dList.isEmpty() || dList == null) {
            return null;
        } else {
            Iterator it = dList.iterator();
            while (it.hasNext()) {
                Data data = (Data) it.next();
                return data;
            }
        }
        return null;
    }

    public Data find(Data data) {
        /*  if (data != null && !"".equals(data)) {
            Query q = em.createNamedQuery("Data.findByValue").setParameter("value", data.getValue());
            List<Data> dList = q.getResultList();
            if (dList.isEmpty() || dList == null) {
                return null;
            } else {
                Iterator it = dList.iterator();
                while (it.hasNext()) {
                    Data d = (Data) it.next();
                    if ((d.getSystemid().equals(data.getSystemid())) && (d.getSubsystemid().equals(data.getSubsystemid()))
                            && (d.getDevicetypeId().equals(data.getDevicetypeId())) && (d.getParameterid().equals(data.getParameterid()))
                            && (d.getAttributeid().equals(data.getAttributeid()))) {
                        return d;
                    }
                }
            }
        }*/
        return null;
    }

    public void setImage(Data data, File file) {
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte[] bytes = null;
            bytes = new byte[fin.available()];
            System.out.println("-------" + bytes.length);
            fin.read(bytes);
           // System.out.println(bytes);
            data.setImage(bytes);
            em.getTransaction().begin();
            em.merge(data);
            em.getTransaction().commit();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataFacade.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fin.close();
            } catch (IOException ex) {
                Logger.getLogger(DataFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
