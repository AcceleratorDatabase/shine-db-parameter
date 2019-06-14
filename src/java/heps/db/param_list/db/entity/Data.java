/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.param_list.db.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lvhuihui
 */
@Entity
@Table(name = "data")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Data.findAll", query = "SELECT d FROM Data d")
    , @NamedQuery(name = "Data.findById", query = "SELECT d FROM Data d WHERE d.id = :id")
    , @NamedQuery(name = "Data.findByValue", query = "SELECT d FROM Data d WHERE d.value = :value")
    , @NamedQuery(name = "Data.findByAcceptance", query = "SELECT d FROM Data d WHERE d.acceptance = :acceptance")
    , @NamedQuery(name = "Data.findByDatemodified", query = "SELECT d FROM Data d WHERE d.datemodified = :datemodified")
    , @NamedQuery(name = "Data.findByComment", query = "SELECT d FROM Data d WHERE d.comment = :comment")
    , @NamedQuery(name = "Data.findByStatus", query = "SELECT d FROM Data d WHERE d.status = :status")
    , @NamedQuery(name = "Data.findByAttachment", query = "SELECT d FROM Data d WHERE d.attachment = :attachment")})
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    //@Size(max = 200)
    @Column(name = "Value")
    private String value;
   // @Size(max = 200)
    @Column(name = "acceptance")
    private String acceptance;
    @Lob
    @Column(name = "image")
    private byte[] image;
    @Column(name = "Date_modified")
    @Temporal(TemporalType.DATE)
    private Date datemodified;
   // @Size(max = 80)
    @Column(name = "Comment")
    private String comment;
  //  @Size(max = 45)
    @Column(name = "Status")
    private String status;
   //@Size(max = 100)
    @Column(name = "attachment")
    private String attachment;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataId")
    private List<HistoryData> historyDataList;
    @JoinColumn(name = "Attribute_id", referencedColumnName = "Id")
    @ManyToOne
    private Attribute attributeid;
    @JoinColumn(name = "Parameter_id", referencedColumnName = "Id")
    @ManyToOne
    private Parameter parameterid;
    @JoinColumn(name = "system_id", referencedColumnName = "Id")
    @ManyToOne
    private System systemId;
    @JoinColumn(name = "Version_id", referencedColumnName = "Id")
    @ManyToOne
    private Version versionid;

    public Data() {
    }

    public Data(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(Date datemodified) {
        this.datemodified = datemodified;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @XmlTransient
    public List<HistoryData> getHistoryDataList() {
        return historyDataList;
    }

    public void setHistoryDataList(List<HistoryData> historyDataList) {
        this.historyDataList = historyDataList;
    }

    public Attribute getAttributeid() {
        return attributeid;
    }

    public void setAttributeid(Attribute attributeid) {
        this.attributeid = attributeid;
    }

    public Parameter getParameterid() {
        return parameterid;
    }

    public void setParameterid(Parameter parameterid) {
        this.parameterid = parameterid;
    }

    public System getSystemId() {
        return systemId;
    }

    public void setSystemId(System systemId) {
        this.systemId = systemId;
    }

    public Version getVersionid() {
        return versionid;
    }

    public void setVersionid(Version versionid) {
        this.versionid = versionid;
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
        if (!(object instanceof Data)) {
            return false;
        }
        Data other = (Data) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "heps.db.param_list.db.entity.Data[ id=" + id + " ]";
    }
    
}
