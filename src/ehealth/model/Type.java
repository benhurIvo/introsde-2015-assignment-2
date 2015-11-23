/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehealth.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author benhur
 */
@Entity
@Table(name = "type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Type.findAll", query = "SELECT t FROM Type t"),
    @NamedQuery(name = "Type.findByTid", query = "SELECT t FROM Type t WHERE t.tid = :tid"),
    @NamedQuery(name = "Type.findByType", query = "SELECT t FROM Type t WHERE t.type = :type")})
public class Type implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tid")
    private Integer tid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "type")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tid")
    private Collection<Healthprofile> healthprofileCollection;

    public Type() {
    }

    public Type(Integer tid) {
	this.tid = tid;
    }

    public Type(Integer tid, String type) {
	this.tid = tid;
	this.type = type;
    }

    public Integer getTid() {
	return tid;
    }

    public void setTid(Integer tid) {
	this.tid = tid;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    @XmlTransient
    public Collection<Healthprofile> getHealthprofileCollection() {
	return healthprofileCollection;
    }

    public void setHealthprofileCollection(Collection<Healthprofile> healthprofileCollection) {
	this.healthprofileCollection = healthprofileCollection;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (tid != null ? tid.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
	if (!(object instanceof Type)) {
	    return false;
	}
	Type other = (Type) object;
	if ((this.tid == null && other.tid != null) || (this.tid != null && !this.tid.equals(other.tid))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "ehealth.model.Type[ tid=" + tid + " ]";
    }
    
}
