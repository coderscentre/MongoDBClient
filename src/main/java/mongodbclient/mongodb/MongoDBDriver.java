package mongodbclient.mongodb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.util.JSON;

import utility.Converter;
import utility.FileProcessor;
import utility.Utility;
import utility.XMLOperations;
import org.xml.sax.SAXException;  

public class MongoDBDriver 
{
	MongoClient mongoclient;
	MongoDatabase mongodb;
	MongoCollection <Document> collection;
	String collectionName;
	String ip;
	int port;
	
	public boolean initializeMongoDB()
	{
	   try 
	   {
		initConnection();
		init_collections();
	   } 
	      catch (Exception e) 
	   {
	
		  System.out.println(e.toString());
	   }
	   return true;
	}
	
	 public boolean initConnection() throws IOException
	   {
			establishHostConnection();
		    connectToDatabase();  
		    return true;
	   }
	 
	public boolean establishHostConnection() throws IOException
	{ 
		ip=new FileProcessor().loadProperty("mongoserver.properties", "mongodbhost");
		port=Integer.parseInt(new FileProcessor().loadProperty("mongoserver.properties", "mongodbport"));
		mongoclient = new MongoClient(ip,port);	
		return true;
	}
	
	public boolean connectToDatabase() throws IOException
	{
		String dbname = new FileProcessor().loadProperty("mongoserver.properties", "mongodb");
	    mongodb = mongoclient.getDatabase(dbname);	
		return true;
	}
	
	
	public boolean init_collections() throws IOException, ParserConfigurationException, SAXException
	{

		String data= new FileProcessor().loadFilefromResource("collections.xml");
		System.out.println(data);
		Converter converter = new Converter();
		XMLOperations xmlop = new XMLOperations();
	    org.w3c.dom.Document document = converter.convertStringToDocument(data);
		String[] recordlist = xmlop.GetAttributeList(document, "mongodbcollection", "name");
		int i=0;
		while (i<recordlist.length)
		{
			createCollection(recordlist[i]);
			List<String> records= xmlop.getContentFromElementsSubElement(document, "mongodbcollection", "record", i);
			insertManyintoCollection(records);
			i++;
		}
		
		return false;
	}
	
	
	  
	public void List()
	{
		MongoIterable<String> dbs = mongoclient.listDatabaseNames();
		MongoCursor<String> cursor = dbs.iterator();
		while (cursor.hasNext())
		{
			System.out.println(cursor.next());
		}
	}

	public boolean deleteDatabase(String name)
	{ 
	   
		mongoclient.dropDatabase(name);
		return true;
	}
	
	public boolean createCollection(String name)
	{ 
	   
		mongodb.createCollection(name);
		collection =  mongodb.getCollection(name);
		collectionName=name;
		return true;
	}
	public boolean connecttoCollection(String name)
	{ 
	   
		collection =  mongodb.getCollection(name);
		collectionName=name;
		return true;
	}
	public boolean deleteCollection(String name)
	{ 
	  
		mongodb.getCollection(name).drop();
		return true;
	}
	public boolean insertintoCollection(String data)
	{ 
	
	   Document doc = Document.parse(data);
	   collection.insertOne(doc);
	   return true;
	}
	
	public boolean insertManyintoCollection(List<String> records)
	{ 
	
	  List <Document> documentlist = Utility.convertJSONStringListtoDocumentList(records);
	  collection.insertMany(documentlist);
	  return true;
	}
	
	
	
	public String getCollectionData()
	{ 
	
	   FindIterable<Document> resultset =  collection.find();
	   String json = JSON.serialize(resultset);
	   json="{"+collectionName+":"+json+"}";
	   return json;
	}

	
	
	
	 public String getAggregateResult(String queryName, String[] args, String aggregateName) throws IOException
	 {
		Properties prop = new Properties();
		
		prop =new FileProcessor().loadPropertiesFromResource("aggregate.properties");
		
		int i=1;
		String query;
		Document doc;
		int argcounter=0;
		if(args !=null)
		{
		 argcounter = args.length;
		}
		
		int argtracker = 1;
		ArrayList<Document> list = new ArrayList<Document>();
		 while(prop.getProperty(queryName+"."+i)!=null)
		   {   
			 
			 query = prop.getProperty(queryName+"."+i);
			 
			 int localcounter = argtracker;
			 while (localcounter<=argcounter)
			 {
				 if (query.contains("variable"+argtracker))
					 
				 {
					 query=query.replace("variable"+argtracker, args[argtracker-1]);
					 argtracker= argtracker+1;
					 localcounter=localcounter+1;
					 
				 }
				 else
				 {
					 localcounter=argcounter+1;
				 }
			 
				 
			 }
			
			 System.out.println(query);
			 doc = Document.parse(query);
			 list.add(doc);
			 i++;
		   }
		 System.out.println("Exited"+ list.size());
			AggregateIterable<Document> output = collection.aggregate(list);
			 System.out.println("Exited");
			String jsonstring = JSON.serialize(output);
			System.out.println(jsonstring);
			jsonstring = "{"+aggregateName+":"+jsonstring+"}";
			return jsonstring;
	
	 }
	
	
}
