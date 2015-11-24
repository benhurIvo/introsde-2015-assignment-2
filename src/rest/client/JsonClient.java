package rest.client;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class JsonClient {
   static ClientConfig clientConfig = new ClientConfig();
	static	Client client = ClientBuilder.newClient(clientConfig);
	static	WebTarget service = client.target(getBaseURI());
	static int first_person_id=0;
	static int last_person_id=0;
	static int new_person_id=0;
	static int mid=0;
	static int count=0;
	static int newcount=0;
	static String measure="";
	public static void main(String[] args) {

	String Jdata = "{\n" +
"    \"firstname\": \"Chuck\",\n" +
"    \"lastname\": \"Norris\",\n" +
"    \"birthdate\": \"1945-01-01\",\n" +
"    \"healthProfile\": {\n" +
"      \"weight\": \"78.9\",\n" +
"      \"height\": \"172\",\n" +
"      \"bloodpressure\": null\n" +
"    }\n" +
"  }";
	
try{
    String media = MediaType.APPLICATION_XML;
	String output = getResults("1","GET","person","",media);
	JSONArray array = null;
	JSONArray array2 = null;
	NodeList list = null;
	int cnt = 0;
//	Document doc =xmlToArray(output);
//
//	  list = doc.getElementsByTagName("person");
//		cnt = list.getLength();
//	if(cnt>3){
//	    first_person_id = Integer.parseInt(list.item(0).getAttributes().getNamedItem("id").getTextContent());
//	    last_person_id = Integer.parseInt(list.item(list.getLength()-1).getAttributes().getNamedItem("id").getTextContent());
//	
////	    System.out.println(Formatter.prettyXMLFormat(output));
////	    System.out.println("\n Proceeding to step 2 printing first person");
////	    System.out.println(Formatter.prettyXMLFormat(getPersonId(media,first_person_id)));
//	    System.out.println("\n Proceeding to step 3, changing first name of first person");
//	    JSONObject json = new JSONObject(Jdata);
//        String xml = XML.toString(json);
//	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//	DocumentBuilder db = dbf.newDocumentBuilder();
//	
//	    System.out.println(service.path("person/").request().accept(media).put(Entity.xml(db.parse(xml))).readEntity(String.class));//Formatter.prettyXMLFormat(putPersonId(db.parse(xml).toString(),media,first_person_id)));
//	    System.out.println("\n Proceeding to step 4, adding new person");
//	    String newPerson = getResults("4","POST","person/",Jdata,media);
//	    doc =xmlToArray(newPerson);
//	  list = doc.getElementsByTagName("person");
//	  if(list.getLength()!=0)
//	      new_person_id = Integer.parseInt(list.item(list.getLength()-1).getAttributes().getNamedItem("id").getTextContent());
//	  if(last_person_id==new_person_id)
//	      System.out.println("\nPerson not saved!!!\n");
//	  else{
//	System.out.println("\nPerson successfull saved!!!\n");
//	  }
//	}
//	else{
//	System.out.println("Result: ERROR Too few people \nHTTP Status: 400");
//	}
	  media = MediaType.APPLICATION_JSON;
	 output = getResults("1","GET","person","",media);
	
	array = new JSONArray(output); 
	cnt = array.length();
	if(cnt>3){	    
	    //System.out.println("Result: OK \n HTTP Status: 200");
	  // if(media.equals(MediaType.APPLICATION_XML)){
	    first_person_id = Integer.parseInt(array.getJSONObject(0).getString("id"));
	    last_person_id = Integer.parseInt(array.getJSONObject(array.length()-1).getString("id"));
	
	    System.out.println(Formatter.prettyJsonFormat(output));
	    System.out.println("\n Proceeding to step 2 printing first person");
	    System.out.println(Formatter.prettyJsonFormat(getPersonId(media,first_person_id)));
	    System.out.println("\n Proceeding to step 3, changing first name of first person");
	       System.out.println(Formatter.prettyJsonFormat(putPersonId(Jdata,media,first_person_id)));
	      System.out.println("\n Proceeding to step 4, adding new person");
	      output = getResults("4","POST","person/",Jdata,media);
	      array = new JSONArray("["+output+"]"); 
		JSONObject jsonObj  = array.getJSONObject(array.length()-1);
		new_person_id=Integer.parseInt(jsonObj.getString("id"));
	      if(last_person_id==new_person_id)
		   System.out.println("Person not saved");
		   
	      else {
	      System.out.println("Person successfully saved");
	       System.out.println(Formatter.prettyJsonFormat(output));	       
		System.out.println("\n Proceeding to step 5, deleting new person and returning list of people");
		System.out.println(Formatter.prettyJsonFormat(delPerson("",media,new_person_id)));
	      }
	      System.out.println("\n Getting measuretypes");
	      output = getMeasure(media);
	
	array = new JSONArray(output); 
	cnt = array.length();
	if(cnt<2)
	    System.out.println("\nFew measuretypes");
	else{
	System.out.println(Formatter.prettyJsonFormat(output));
	System.out.println("\n Measuretypes for first and last person");		
	      array = new JSONArray(output); 
		for(int i=0;i<array.length();i++){
		    String rslt = getResults("6","GET","person/"+String.valueOf(first_person_id)+"/"+array.getJSONObject(i).getString("value"),"",media);
		    String rslt1 = getResults("6","GET","person/"+String.valueOf(first_person_id)+"/"+array.getJSONObject(i).getString("value"),"",media);
		    if(array.getJSONObject(i).getString("value").toString().length()!=0){
		    System.out.println("1st person " + array.getJSONObject(i).getString("value")+"\n" +Formatter.prettyJsonFormat(rslt));
		    System.out.println("2nd person " + array.getJSONObject(i).getString("value")+"\n" +Formatter.prettyJsonFormat(rslt1));
		
		    }if(rslt.length()>5)
		{
		measure = array.getJSONObject(i).getString("value");
		array2 = new JSONArray(rslt);
		mid = Integer.parseInt(array2.getJSONObject(i).getString("id"));
		 		
		}
	}
		  System.out.println("\n Measuretype for for given measure id for ist person");
		  System.out.println(Formatter.prettyJsonFormat(getPersonMeasureId(first_person_id,measure,mid,media)));
		  System.out.println("\n Measure history for 1st person");
		  output = getPersonMeasure(first_person_id,measure,media);
		  System.out.println(Formatter.prettyJsonFormat(output));
		  array = new JSONArray(output);
		  count = array.length();
		  System.out.println("\n Measure has "+count + " elements");
		  System.out.println("\n Adding Measure to 1st person");
		  Jdata = " {\n" +
"  \"value\" : \"200\"\n" +
"} ";
		  output = postPersonMeasureId(first_person_id,measure,Jdata,media);
		  array = new JSONArray(output);
		  newcount = array.length();
		  output = getPersonMeasure(first_person_id,measure,media);
		  System.out.println(Formatter.prettyJsonFormat(output));		  
		  System.out.println("\n Measure had "+count + " elements but now has "+newcount+" elements");
	
		  System.out.println("\n Updating the created Measure for 1st person");
		  //array = new JSONArray(rslt);
		mid = Integer.parseInt(array.getJSONObject(array.length()-1).getString("id"));
		Jdata = " {\n" +
"  \"value\" : \"300\"\n" +
"} ";
		output = putPersonMeasureId(first_person_id,measure,mid,media,Jdata);
		System.out.println(Formatter.prettyJsonFormat(output));
		System.out.println("\n Getting measuretypes in a certain date range");
		System.out.println(Formatter.prettyJsonFormat(getPersonMeasureDate(first_person_id,measure,"12-12-2022","14-11-1888",media)));
		System.out.println("\n Getting measuretypes in a certain size range");
		System.out.println(Formatter.prettyJsonFormat(getPersonMeasureRange(first_person_id,measure,20,300,media)));
	}
	      
	   }
	   else{
	    System.out.println("Result: ERROR Too few people \nHTTP Status: 400");
	}
	
//		System.out.println("Result: ERROR \nHTTP Status: 200");
//		if(media.equals(MediaType.APPLICATION_XML))
//	    System.out.println(Formatter.prettyXMLFormat(output));
//	else
//	    System.out.println(Formatter.prettyJsonFormat(output));
	
	}catch(Exception ex){
	ex.printStackTrace();
	}	
		

	}
	
	   public static Document xmlToArray(String xml) {
	       Document doc = null;
       try {
	   DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	   DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	   doc = docBuilder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
       } catch (Exception ex) {
	   Logger.getLogger(JsonClient.class.getName()).log(Level.SEVERE, null, ex);
       } 
	return doc;
    }
	
public static String getResults(String reqNo,String reqType,String url,String data,String media){
   Response response = service.path(url).request().get();
String entity = response.readEntity(String.class);
WriteToFile.write("client-server-json.log","\n\nRequest #:"+reqNo+" "+reqType+" "+url+" Accept: "+media+" Content-type: "+media);
System.out.println("\nResult: "+ response.getStatusInfo().getReasonPhrase() + "\nHTTP Status:"+response.getStatus()+"\n");


String output ="";
if(reqType.toLowerCase().contains("get"))
output =service.path(url).request().accept(media).get().readEntity(String.class);
if(reqType.toLowerCase().contains("post"))
output =service.path(url).request().accept(media).post(Entity.json(data)).readEntity(String.class);
if(reqType.toLowerCase().contains("put"))
output =service.path(url).request().accept(media).put(Entity.json(data)).readEntity(String.class);
if(reqType.toLowerCase().contains("delete"))
output =service.path(url).request().accept(media).delete().readEntity(String.class);
  
return output;
}

    public static String getPeople(String media) {

	return getResults("1","GET","person","",media);
	
    }
    //GET /person/{id} 
    public  static String getPersonId(String media,int pid){
    return getResults("2","GET","person/"+String.valueOf(pid),"",media);
    }
    
     //PUT /person/{id} 
    public  static String putPersonId(String data,String media,int pid){
    return getResults("3","PUT","person/"+String.valueOf(pid),data,media);
    }
    //POST /person
    public  static String postPerson(String data,String media){
    return getResults("4","POST","person/",data,media);
    }
    
//    Request #5: DELETE /person/{id} should delete the person identified by {id} from the system
public  static String delPerson(String data,String media,int pid){
   return getResults("5","DELETE","person/"+String.valueOf(pid),data,media);
        }
//Request #6: GET /person/{id}/{measureType} should return the list of values (the history) of {measureType} (e.g. weight) for person identified by {id}
public  static String getPersonMeasure(int pid,String measure,String media){
   return getResults("6","GET","person/"+String.valueOf(pid)+"/"+measure,"",media);
        }
//Request #7: GET /person/{id}/{measureType}/{mid} should return the value of {measureType} (e.g. weight) identified by {mid} for person identified by {id}
public  static String getPersonMeasureId(int pid,String measure,int Id,String media){
   return getResults("7","GET","person/"+String.valueOf(pid)+"/"+measure+"/"+String.valueOf(Id),"",media);
        }
//Request #8: POST /person/{id}/{measureType} should save a new value for the {measureType} (e.g. weight) of person identified by {id} and archive the old value in the history
public  static String postPersonMeasureId(int pid,String measure,String data,String media){
 return getResults("8","POST","person/"+String.valueOf(pid)+"/"+measure+"/",data,media);
        }	
//Request #9: GET /measureTypes
public  static String getMeasure(String media){
   return getResults("9","GET","measureTypes","",media);
        }
//Request #10: PUT /person/{id}/{measureType}/{mid} 
public  static String putPersonMeasureId(int pid,String measure,int Id,String media,String data){
   return getResults("10","PUT","person/"+String.valueOf(pid)+"/"+measure+"/"+String.valueOf(Id),data,media);
        }
//Request #11: GET /person/{id}/{measureType}?before={beforeDate}&after={afterDate} 
public  static String getPersonMeasureDate(int pid,String measure,String b4,String afta,String media){
  return getResults("11","GET","person/"+String.valueOf(pid)+"/"+measure+"/before="+b4+"&after="+afta,"",media);
        }
//Request #12: GET /person?measureType={measureType}&max={max}&min={min}
public  static String getPersonMeasureRange(int pid,String measure,int min, int max,String media){
  return getResults("12","GET","person/"+measure+"/max="+max+"&min="+min,"",media);
        }
private static URI getBaseURI() {
		return UriBuilder.fromUri(
				//"http://127.0.1.1:5700/sdelab/").build();
			"https://evening-forest-2924.herokuapp.com/sdelab/").build();
	}

}
