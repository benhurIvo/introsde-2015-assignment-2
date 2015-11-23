/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehealth.dao.mtds;

import ehealth.dao.Life;
import ehealth.model.Healthprofile;
import ehealth.model.Person;
import ehealth.model.Type;
import ehealth.utils.HealthProfilez;
import ehealth.utils.Measure;
import ehealth.utils.MeasureType;
import ehealth.utils.People;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author benhur
 */
public class PersonMtd {
    public static People returnPersonById(int personId) {
        EntityManager em = Life.instance.createEntityManager();
        Person p = em.find(Person.class, personId);
	   People pzz = new People();
	    pzz.setPersonId(String.valueOf(p.getPid()));
	    pzz.setFirstname(p.getFirstname());
	    pzz.setLastname(p.getLastname());
	    pzz.setBirthdate(p.getBirthdate());
	List<Healthprofile> list = em.createNamedQuery("Healthprofile.findByPid", Healthprofile.class)
		.setParameter("pid", p.getPid())
		.getResultList();
	HealthProfilez hp = new HealthProfilez();
	for(Healthprofile hp1:list){
	if(hp1.getTid().getType().contains("weight"))
	    hp.setWeight(hp1.getValue());
	 if(hp1.getTid().getType().contains("height"))
	    hp.setHeight(hp1.getValue());
	 if(hp1.getTid().getType().contains("bloodpressure"))
	    hp.setBloodpressure(hp1.getValue());
	
	}
	
	pzz.setHProfile(hp);
        Life.instance.closeConnections(em);
        return pzz;
    }

    public static List<People> getAll() {	
	List<People> pz = new ArrayList<People>();
        EntityManager em = Life.instance.createEntityManager();
        List<Person> Plist = em.createNamedQuery("Person.findAll", Person.class)
          .getResultList();
	for(Person p:Plist){
	    People pzz = new People();
	    pzz.setPersonId(String.valueOf(p.getPid()));
	    pzz.setFirstname(p.getFirstname());
	    pzz.setLastname(p.getLastname());
	    pzz.setBirthdate(p.getBirthdate());
	List<Healthprofile> list = em.createNamedQuery("Healthprofile.findByPid", Healthprofile.class)
		.setParameter("pid", p.getPid())
		.getResultList();
	HealthProfilez hp = new HealthProfilez();
	for(Healthprofile hp1:list){
	    if(hp1.getTid().getType().equals("weight"))
	    hp.setWeight(hp1.getValue());
	 if(hp1.getTid().getType().equals("height"))
	    hp.setHeight(hp1.getValue());
	 if(hp1.getTid().getType().equals("bloodpressure"))
	    hp.setBloodpressure(hp1.getValue());	
		}	
	
	pzz.setHProfile(hp);
	pz.add(pzz);
	}	
        Life.instance.closeConnections(em);
        return pz;
    }

     public static List<Person> getPersonById(int personId) {
       EntityManager em = Life.instance.createEntityManager();
       List<Person> plist = em.createNamedQuery("Person.findByPid", Person.class)
	    .setParameter("pid", personId)
		.getResultList();
        Life.instance.closeConnections(em);
        return plist;
    }
    
    public static Person savePerson(Person p) {
        EntityManager em = Life.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        Life.instance.closeConnections(em);
        return p;
    } 

    public static Person updatePerson(Person p) {
        EntityManager em = Life.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        Life.instance.closeConnections(em);
        return p;
    }

    public static void removePerson(Person p) {
        EntityManager em = Life.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        Life.instance.closeConnections(em);
    }
    
      public static List<Measure> getPersonMeasure(int personId,String ms) {
	  List<Measure> mz = new ArrayList<Measure>();
       EntityManager em = Life.instance.createEntityManager();
       List<Healthprofile> list = getHealthprof(personId);
	HealthProfilez hp = new HealthProfilez();
	for(Healthprofile hp1:list){	    
	    Measure m = new Measure();
	if(hp1.getTid().getType().equals(ms)){
	    m.setValue(hp1.getValue());
	    m.setDatechanged(hp1.getDatecreated());
	    m.setTypeId(hp1.getHid().toString());
	    mz.add(m);
	    }
	}
	
        Life.instance.closeConnections(em);
        return mz;
    }     
     
      
        public static List<Healthprofile> getHealthprof(int personId) {
        EntityManager em = Life.instance.createEntityManager(); 
         List<Healthprofile> list = em.createNamedQuery("Healthprofile.findByPid", Healthprofile.class)
		.setParameter("pid", personId)
		.getResultList();
        Life.instance.closeConnections(em);
        return list;
    }
      
      public static Measure getPersonMeasureId(int personId,String ms,int mid) {
	  List<Measure> mz = new ArrayList<Measure>();
       EntityManager em = Life.instance.createEntityManager();
       List<Healthprofile> list = getHealthprof(personId);
	HealthProfilez hp = new HealthProfilez();
	for(Healthprofile hp1:list){	    
	    Measure m = new Measure();
	if(hp1.getTid().getType().equals(ms)&&hp1.getHid().equals(mid)){
	    m.setValue(hp1.getValue());
	    m.setDatechanged(hp1.getDatecreated());
	   // m.setTypeId(.toString());
	    mz.add(m);
	    }
	    
	}
	
        Life.instance.closeConnections(em);
        return mz.get(0);
    }    
      
//      public static Healthprofile addMeasure(Healthprofile h) {
//         EntityManager em = Life.instance.createEntityManager();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//        em.persist(h);
//        tx.commit();
//        Life.instance.closeConnections(em);
//        return h;
//    }

      
}
