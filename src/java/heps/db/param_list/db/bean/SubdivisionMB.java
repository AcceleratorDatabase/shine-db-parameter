/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.db.bean;

import heps.db.param_list.db.ejb.SubdivisionFacade;
import heps.db.param_list.db.entity.Subdivision;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Lvhuihui
 */
@ManagedBean
@ViewScoped
public class SubdivisionMB implements Serializable {

    private List<Subdivision> subdivisionList;
    private SubdivisionFacade ejbFacade;

    public SubdivisionMB() {
        ejbFacade = new SubdivisionFacade();
        subdivisionList = ejbFacade.getAllSubdivision();
    }

    public List<Subdivision> getSubdivisionList() {
        return this.subdivisionList;
    }

    public void setSubdivisionList(List<Subdivision> subdivisionList) {
        this.subdivisionList = subdivisionList;
    }

  
}
