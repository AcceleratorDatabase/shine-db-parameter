/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.db.ejb;

import heps.db.param_list.db.entity.Version;
import heps.db.param_list.comman.tools.EmProvider;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author Lvhuihui
 */
@Stateless
public class VersionFacade{

    /*@PersistenceUnit
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("param_listPU");
    static EntityManager em = emf.createEntityManager();

    @PersistenceContext*/
    public static EntityManager em=EmProvider.getInstance().getEntityManagerFactory().createEntityManager();
    
    public void getVersion(){
       
    }
    
}
