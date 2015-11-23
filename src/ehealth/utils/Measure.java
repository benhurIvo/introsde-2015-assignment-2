/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehealth.utils;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author benhur
 */
@XmlRootElement(name = "Measure")	
// XmlType can optionally define the order in which the fields of person are written
@XmlType(propOrder = { "value", "datechanged"})
@XmlAccessorType(XmlAccessType.FIELD)
public class Measure {
private String value;		
	private String datechanged;		
	@XmlAttribute(name="id")
	private String tid;
	
//	public People(String personId, String fname, String lname, String birthdate, HealthProfilez hp) {
//		this.setPersonId(personId); 	
//		this.setFirstname(fname);
//		this.setLastname(lname);
//		this.setBirthdate(birthdate); 	
//		this.healthProfile=hp;
//	}
//	
//	public People(String personId, String fname, String lname, String birthdate) {
//		this.setPersonId(personId); 	
//		this.setFirstname(fname);
//		this.setLastname(lname);
//		this.setBirthdate(birthdate); 
//		this.healthProfile=new HealthProfilez();
//	}
//	
	public Measure() {
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDatechanged() {
		return datechanged;
	}
	public void setDatechanged(String datechanged) {
		this.datechanged = datechanged;
	}
	
	public String getTypeId() {
		return tid;
	}
	public void setTypeId(String tid) {
		this.tid = tid;
	}

}

