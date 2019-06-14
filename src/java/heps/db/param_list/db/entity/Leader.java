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
@Table(name = "leader")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Leader.findAll", query = "SELECT l FROM Leader l")
    , @NamedQuery(name = "Leader.findById", query = "SELECT l FROM Leader l WHERE l.id = :id")
    , @NamedQuery(name = "Leader.findByName", query = "SELECT l FROM Leader l WHERE l.name = :name")
    , @NamedQuery(name = "Leader.findByPassword", query = "SELECT l FROM Leader l WHERE l.password = :password")})
public class Leader implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
  //  @Size(max = 45)
    @Column(name = "Name")
    private String name;
  //  @Size(max = 45)
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "leaderId")
    private List<System> systemList;

    public Leader() {
    }

    public Leader(Integer id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<System> getSystemList() {
        return systemList;
    }

    public void setSystemList(List<System> systemList) {
        this.systemList = systemList;
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
        if (!(object instanceof Leader)) {
            return false;
        }
        Leader other = (Leader) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "heps.db.param_list.db.entity.Leader[ id=" + id + " ]";
    }
    
}
