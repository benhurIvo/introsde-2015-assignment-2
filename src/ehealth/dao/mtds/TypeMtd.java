/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehealth.dao.mtds;

import ehealth.dao.Life;
import ehealth.model.Type;
import ehealth.utils.MeasureType;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author benhur
 */
public class TypeMtd {
       public static Type getTypeById(int tid) {
        EntityManager em = Life.instance.createEntityManager();
        Type t = em.find(Type.class, tid);
        Life.instance.closeConnections(em);
        return t;
    }

    public static List<Type> getAll() {
        EntityManager em = Life.instance.createEntityManager();
        List<Type> list = em.createNamedQuery("Type.findAll", Type.class)
            .getResultList();
        Life.instance.closeConnections(em);
        return list;
    }

     public static List<Type> getTypeByString(String st) {
        EntityManager em = Life.instance.createEntityManager();
        List<Type> list = em.createNamedQuery("Type.findByType", Type.class)
		.setParameter("type", st)
		.getResultList();
        Life.instance.closeConnections(em);
        return list;
    }
    
    public static Type saveType(Type t) {
        EntityManager em = Life.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(t);
        tx.commit();
        Life.instance.closeConnections(em);
        return t;
    } 

    public static Type updateType(Type t) {
        EntityManager em = Life.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        t=em.merge(t);
        tx.commit();
        Life.instance.closeConnections(em);
        return t;
    }

    public static void removeType(Type t) {
        EntityManager em = Life.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        t=em.merge(t);
        em.remove(t);
        tx.commit();
        Life.instance.closeConnections(em);
    }
    
         
      public static List<MeasureType> getMeasuretypes() {
         EntityManager em = Life.instance.createEntityManager();
        List<Type> Tlist = em.createNamedQuery("Type.findAll", Type.class)
		.getResultList();
	Life.instance.closeConnections(em);
	List<MeasureType> mt = new ArrayList<MeasureType>();
	for(Type t:Tlist){
	MeasureType mtt = new MeasureType();
	mtt.setValue(t.getType());
	mt.add(mtt);
	}
        return mt;
    }
}
