package ehealth.resources;
import ehealth.dao.Life;
import ehealth.dao.mtds.HealthMtd;
import ehealth.dao.mtds.PersonMtd;
import ehealth.dao.mtds.TypeMtd;
import ehealth.model.Healthprofile;
import ehealth.model.Person;
import ehealth.model.Type;
import ehealth.utils.HealthProfilez;
import ehealth.utils.Measure;
//import ehealth.utils.People;
import ehealth.utils.People;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/person")
public class PersonCollectionResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    // will work only inside a Java EE application
    @PersistenceUnit(unitName="sqlitePU")
    EntityManager entityManager;

    // will work only inside a Java EE application
    @PersistenceContext(unitName = "sqlitePU",type=PersistenceContextType.TRANSACTION)
    
    // Return the list of people to the user in the browser
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML })
    public List<People> getPersonsBrowser() {
        System.out.println("Getting list of people...");
        List<People> people = PersonMtd.getAll();
	System.out.println("eh eh "+people.size());
        return people;
    }

    // retuns the number of people
    // to get the total number of records
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        System.out.println("Getting count...");
        List<People> people = PersonMtd.getAll();
        int count = people.size();
        return String.valueOf(count);
    }
    
@POST
@Consumes({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
public People create(People p){	
     Person pp = new Person();
		try {
		   
pp.setFirstname(p.getFirstname());
pp.setLastname(p.getLastname());
pp.setBirthdate(p.getBirthdate());
PersonMtd.savePerson(pp);

Healthprofile hp = new Healthprofile();
Date det = new Date();
hp.setPid(pp);
hp.setDatecreated(new SimpleDateFormat("yyyy-MM-dd").format(det));
Type t=null;
if(p.getHProfile().getWeight()!=null){
    System.out.println("wei "+ p.getHProfile().getWeight());
t = TypeMtd.getTypeByString("weight").get(0);
hp.setTid(t);
hp.setValue(String.valueOf(p.getHProfile().getWeight()));
    HealthMtd.saveHealthprofile(hp);
		}
    if(p.getHProfile().getHeight()!=null){
	System.out.println("hei "+ p.getHProfile().getHeight());
t = TypeMtd.getTypeByString("height").get(0);
hp.setTid(t);
hp.setValue(String.valueOf(p.getHProfile().getHeight()));
hp.setHid(null);
		
    HealthMtd.saveHealthprofile(hp);}
if(p.getHProfile().getBloodpressure()!=null) {   
    System.out.println("blo "+ p.getHProfile().getBloodpressure());
t = TypeMtd.getTypeByString("bloodpressure").get(0);
hp.setTid(t);
hp.setValue(String.valueOf(p.getHProfile().getBloodpressure()));
hp.setHid(null);
    HealthMtd.saveHealthprofile(hp);
}
	    } catch (Exception ex) {
	   ex.printStackTrace();
	    } 
 
	    return PersonMtd.returnPersonById(pp.getPid());
}
 
@PUT
@Path("/{personId}")
   @Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
   @Consumes({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
    public People newPerson(People person,@PathParam("personId") int id) throws IOException {
	System.out.println("Creating new person...123 " + id + person.getHProfile().getHeight());            
      List<Person> plist = PersonMtd.getPersonById(id);
      if(plist!=null){
      Person p = plist.get(0);
      p.setPid(id);
      p.setFirstname(person.getFirstname());
      p.setLastname(person.getLastname());
      p.setBirthdate(person.getBirthdate());
      PersonMtd.updatePerson(p);
      }

      return PersonMtd.returnPersonById(id);
    }	    

    @DELETE
    @Path("/{personId}")
   // @Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
   // @Consumes({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
    public List<People> delPerson(@PathParam("personId") int id) throws IOException {
	System.out.println("deleting person with id " + id );            
      List<Person> plist = PersonMtd.getPersonById(id);
      if(plist!=null){
      Person p = plist.get(0);
	PersonMtd.removePerson(p);
      }
      return PersonMtd.getAll();
    }
    
    @GET
    @Path("/{personId}")
    @Produces({MediaType.APPLICATION_JSON ,  MediaType.APPLICATION_XML})
    public People getPerson(@PathParam("personId") int id) {
	
	//Personz p = PersonMtd.returnPersonById(int personId);
        return PersonMtd.returnPersonById(id);
    }
    
@GET
@Path("/{personId}/{measure}")
public List<Measure> getPersonMeasureType(@PathParam("personId") int id,
        @PathParam("measure") String measure){
 
 return PersonMtd.getPersonMeasure(id, measure);
}
  
@GET
@Path("/{personId}/{measure}/{mid}")
public Measure getPersonMeasureid(@PathParam("personId") int id,
        @PathParam("measure") String measure,@PathParam("mid") int mid){
 
 return PersonMtd.getPersonMeasureId(id, measure, mid);
}

@POST
@Path("/{personId}/{measure}")
public List<Measure> addMeasure(Measure m,@PathParam("personId") int id,
        @PathParam("measure") String measure){
    System.out.println("hhh "+m.getValue()+id +" "+measure);
    List<Healthprofile> list = PersonMtd.getHealthprof(id);
    Healthprofile hp = new Healthprofile();
    Date det = new Date();
    if(list!=null){
	for(Healthprofile hp1:list){
	if(hp1.getTid().getType().equals(measure)){
	    hp.setValue(m.getValue());
	    hp.setPid(hp1.getPid());
	    hp.setTid(hp1.getTid());
	    hp.setDatecreated(new SimpleDateFormat("yyyy-MM-dd").format(det));
	    }
    }
	HealthMtd.saveHealthprofile(hp);
    }
 return PersonMtd.getPersonMeasure(id, measure);
}

@PUT
@Path("/{personId}/{measure}/{mid}")
public Measure gupdateMeasureid(Measure m,@PathParam("personId") int id,
        @PathParam("measure") String measure,@PathParam("mid") int mid){
    if(!HealthMtd.getByPidHid(mid, id).isEmpty()){
	List<Healthprofile> hp = HealthMtd.getByPidHid(mid, id);
	Healthprofile h = hp.get(0);
	h.setDatecreated(h.getDatecreated());
	h.setHid(h.getHid());
	h.setPid(h.getPid());
	h.setTid(h.getTid());
	h.setValue(m.getValue());
	
	HealthMtd.updateHealth(h);
    }
 return PersonMtd.getPersonMeasureId(id, measure, mid);
}

@GET
@Path("/{personId}/{measure}/before={param1}&after={param2}")
public List<Measure> getbyDateRange(@PathParam("personId") int id,@PathParam("measure") String measure,
	@PathParam("param1") String param1,@PathParam("param2") String param2) {

    System.out.println("ah "+param1 + " "+param2);
	    List<Measure> mmm = new ArrayList<Measure>();	
    try {
	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
	    Date d1 = formatter.parse(param1.substring(1, param1.length()));
	    Date d2 = formatter.parse(param2.substring(1,param2.length()));
	    List<Measure> m = PersonMtd.getPersonMeasure(id, measure);
	    for(Measure mm:m){
		Date d3 = formatter.parse(mm.getDatechanged());
		if(d3.after(d2) && d3.before(d1))
		    mmm.add(mm);
	    }
	    System.out.println("params " + param1 + " "+param2);
	} catch (ParseException ex) {
	    Logger.getLogger(PersonCollectionResource.class.getName()).log(Level.SEVERE, null, ex);
	}
   return mmm;
}

@GET
@Path("/{measure}/max={param1}&min={param2}")
public List<People> getbyValueRange(@PathParam("measure") String measure,
	@PathParam("param1") int max,@PathParam("param2") int min) {
    
    System.out.println("params " + max + " "+min);
    List<People> pip = PersonMtd.getAll();
     List<People> pipo = new ArrayList<People>();
     System.out.println("pips "+pip.size());
    for(People p:pip){
	System.out.println("p "+p.getPersonId() + " "+p.getHProfile().getHeight());
	if(measure.contains("weight")&&p.getHProfile().getWeight()!=null){
    if(Double.parseDouble(p.getHProfile().getWeight())>=min&&Double.parseDouble(p.getHProfile().getWeight())<=max)
	pipo.add(p);
	}
	    if(measure.contains("height")&&p.getHProfile().getHeight()!=null){
    if(Double.parseDouble(p.getHProfile().getHeight())>=min&&Double.parseDouble(p.getHProfile().getHeight())<=max)
	pipo.add(p);
	}
	if(measure.contains("bloodpressure")&&p.getHProfile().getBloodpressure()!=null){
    if(Double.parseDouble(p.getHProfile().getBloodpressure())>=min&&Double.parseDouble(p.getHProfile().getBloodpressure())<=max)
	pipo.add(p);
	}
		
    }
    System.out.println("pipos "+pipo.size());
   return pipo;
}

}