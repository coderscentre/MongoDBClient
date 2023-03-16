package api;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

	
	@SpringBootApplication
	public class Inventory_Details extends SpringBootServletInitializer  
     {
			
			  @Override
			   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) 
			   {
			      return application.sources(Inventory_Details.class);
			     
			   }

	 }

	@RestController
	@CrossOrigin(origins = "*")
	@RequestMapping("/Inventory")
	class Inventory
	{
			   @GetMapping("/Item_Details")
			   public String GetItemDetails(HttpSession session) throws IOException 
			   {
				   mongodbclient.mongodb.MongoDBDriver mdb = new mongodbclient.mongodb.MongoDBDriver();
				   mdb.initConnection();
				   mdb.connecttoCollection("Item_Details");
				   String itemDetails = mdb.getAggregateResult("ItemDetails", null, "ItemDetails");
				   return itemDetails;
		       }
			   
			   @GetMapping("/Item_Supply_Details")
			   public String GetItemSupplyDetails(HttpSession session) throws IOException 
			   {
				   mongodbclient.mongodb.MongoDBDriver mdb = new mongodbclient.mongodb.MongoDBDriver();
				   mdb.initConnection();
				   mdb.connecttoCollection("Item_Details");
				   String itemDetails = mdb.getAggregateResult("ItemSupply", null, "Item_Supply");
				   return itemDetails;
		       }
			   
			   @GetMapping("/Item_Demand_Details")
			   public String GetItemDemandDetails(HttpSession session) throws IOException 
			   {
				   mongodbclient.mongodb.MongoDBDriver mdb = new mongodbclient.mongodb.MongoDBDriver();
				   mdb.initConnection();
				   mdb.connecttoCollection("Item_Details");
				   String itemDetails = mdb.getAggregateResult("ItemDemand", null, "Item_Demand");
				   return itemDetails;
		       }
			   
			   @RequestMapping(value= {"/Item_Availability/{id}"}, method=RequestMethod.GET)
			   public String GetItemAvailability(HttpSession session,  @PathVariable("id") String Item_Id) throws IOException 
			   {
				   System.out.println("Entry");
				   mongodbclient.mongodb.MongoDBDriver mdb = new mongodbclient.mongodb.MongoDBDriver();
				   mdb.initConnection();
				   System.out.println(Item_Id);
				   mdb.connecttoCollection("Item_Details");
				   String[] parameter = new String[1];
				   parameter[0]=Item_Id;
				   System.out.println("Retrieve results");
				   String itemDetails = mdb.getAggregateResult("ItemAvailability", parameter, "Item_Availability");
				   return itemDetails;
		       }
			   
			   @GetMapping("/AllItem_Availability")
			   public String GetAllItemAvailability(HttpSession session) throws IOException 
			   {  
				   mongodbclient.mongodb.MongoDBDriver mdb = new mongodbclient.mongodb.MongoDBDriver();
				   mdb.initConnection();
				   mdb.connecttoCollection("Item_Details");
				   String itemDetails = mdb.getAggregateResult("AllItemAvailability", null, "All_ItemAvailability");
				   return itemDetails;
		       }
			   
			   @GetMapping("/AllItem_Total")
			   public String GetAllItemTotal(HttpSession session) throws IOException 
			   {  
				   mongodbclient.mongodb.MongoDBDriver mdb = new mongodbclient.mongodb.MongoDBDriver();
				   mdb.initConnection();
				   mdb.connecttoCollection("Item_Details");
				   String itemDetails = mdb.getAggregateResult("AllItemTotal", null, "AllItem_Total");
				   return itemDetails;
		       }
			  
		}
	
	