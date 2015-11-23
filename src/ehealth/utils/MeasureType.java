/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehealth.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author benhur
 */
@XmlRootElement(name = "measureType")	
// XmlType can optionally define the order in which the fields of person are written
@XmlType(propOrder = { "value"})
@XmlAccessorType(XmlAccessType.FIELD)
public class MeasureType {
    private String value;
    public MeasureType() {
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
