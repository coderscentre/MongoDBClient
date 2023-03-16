package utility;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

public class Utility 
{
 
	public static List<String> convertJSONStringToStringList(String jsonString, String listparentelement)
	{
		try
		{
		JSONObject jsonObject = new JSONObject(jsonString);
		JSONArray jsonArray = jsonObject.getJSONArray(listparentelement);

		List<String> data = new ArrayList<String>();
		for(int i = 0; i < jsonArray.length(); i++){
		     data.add(jsonArray.getString(i));
		}
		return data;

		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		return null;
	}
	
	public static List<Document> convertJSONStringListtoDocumentList(List<String> list)
	{
	  int cursor =0;
	  List <Document> documentlist = new ArrayList<Document>();
	  while(cursor < list.size())
	  {
		documentlist.add(Document.parse(list.get(cursor)));
		cursor++;
	  }
	  return documentlist;
	}
}
