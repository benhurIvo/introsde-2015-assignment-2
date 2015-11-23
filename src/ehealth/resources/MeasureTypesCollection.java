/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ehealth.resources;

import ehealth.dao.mtds.PersonMtd;
import ehealth.dao.mtds.TypeMtd;
import ehealth.utils.MeasureType;
import ehealth.utils.People;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author benhur
 */
@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/measureTypes")
public class MeasureTypesCollection {
     @Context
    UriInfo uriInfo;
    @Context
    Request request;
@GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<MeasureType> getPersonsBrowser() {
        System.out.println("Getting list of measuretypes...");
        return TypeMtd.getMeasuretypes();
    }
    
}
