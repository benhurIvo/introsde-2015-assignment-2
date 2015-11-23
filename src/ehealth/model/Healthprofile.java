/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehealth.model;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author benhur
 */
@Entity
@Table(name = "healthprofile")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Healthprofile.findAll", query = "SELECT h FROM Healthprofile h"),
    @NamedQuery(name = "Healthprofile.findByHid", query = "SELECT h FROM Healthprofile h WHERE h.hid = :hid"),
    @NamedQuery(name = "Healthprofile.findByPidTid", query = "SELECT h FROM Healthprofile h WHERE h.pid.pid = :pid and h.tid.tid = :tid"),
    @NamedQuery(name = "Healthprofile.findByPidHid", query = "SELECT h FROM Healthprofile h WHERE h.pid.pid = :pid and h.hid = :hid"),
    @NamedQuery(name = "Healthprofile.findByDateRange", query = "SELECT h FROM Healthprofile h WHERE h.pid.pid = :pid and h.datecreated between :d1 and :d2"),
    @NamedQuery(name = "Healthprofile.findByPid", query = "SELECT h FROM Healthprofile h WHERE h.pid.pid = :pid")})
public class Healthprofile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "hid")
    private Integer hid;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "value")
    private String value;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "datecreated")
    private String datecreated;
    @JoinColumn(name = "tid", referencedColumnName = "tid")
    @ManyToOne(optional = false)
    private Type tid;
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    @ManyToOne(optional = false)
    private Person pid;

    public Healthprofile() {
    }

    public Healthprofile(Integer hid) {
	this.hid = hid;
    }

    public Healthprofile(Integer hid, String value, String datecreated) {
	this.hid = hid;
	this.value = value;
	this.datecreated = datecreated;
    }

    public Integer getHid() {
	return hid;
    }

    public void setHid(Integer hid) {
	this.hid = hid;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public String getDatecreated() {
	return datecreated;
    }

    public void setDatecreated(String datecreated) {
	this.datecreated = datecreated;
    }

    public Type getTid() {
	return tid;
    }

    public void setTid(Type tid) {
	this.tid = tid;
    }

    public Person getPid() {
	return pid;
    }

    public void setPid(Person pid) {
	this.pid = pid;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (hid != null ? hid.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
	if (!(object instanceof Healthprofile)) {
	    return false;
	}
	Healthprofile other = (Healthprofile) object;
	if ((this.hid == null && other.hid != null) || (this.hid != null && !this.hid.equals(other.hid))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "ehealth.model.Healthprofile[ hid=" + hid + " ]";
    }
    
}
