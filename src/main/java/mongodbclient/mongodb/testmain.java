package mongodbclient.mongodb;
import java.io.IOException;


import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.xml.sax.SAXException;

import utility.Utility;



public class testmain 
{
	
	public static void main (String args[]) throws IOException, JSONException, ParserConfigurationException, SAXException
	{
		MongoDBDriver mdb = new MongoDBDriver();
		mdb.initializeMongoDB();
		
	}

}
