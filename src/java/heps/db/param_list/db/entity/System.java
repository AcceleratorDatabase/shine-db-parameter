/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.db.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lvhuihui
 */
@Entity
@Table(name = "system")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "System.findAll", query = "SELECT s FROM System s")
    , @NamedQuery(name = "System.findById", query = "SELECT s FROM System s WHERE s.id = :id")
    , @NamedQuery(name = "System.findByName", query = "SELECT s FROM System s WHERE s.name = :name")})
public class System implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
 //   @Size(max = 45)
    @Column(name = "Name")
    private String name;
    @JoinColumn(name = "leader_id", referencedColumnName = "Id")
    @ManyToOne
    private Leader leaderId;
    @JoinColumn(name = "subdivision_id", referencedColumnName = "Id")
    @ManyToOne
    private Subdivision subdivisionId;
    @OneToMany(mappedBy = "systemId")
    private List<Data> dataList;

    public System() {
    }

    public System(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Leader getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Leader leaderId) {
        this.leaderId = leaderId;
    }

    public Subdivision getSubdivisionId() {
        return subdivisionId;
    }

    public void setSubdivisionId(Subdivision subdivisionId) {
        this.subdivisionId = subdivisionId;
    }

    @XmlTransient
    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof System)) {
            return false;
        }
        System other = (System) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "heps.db.param_list.db.entity.System[ id=" + id + " ]";
    }
    
}
