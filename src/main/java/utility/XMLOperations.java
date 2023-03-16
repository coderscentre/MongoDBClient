package utility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLOperations
{
	
	public Document updateXML(Document doc, String element, String attribute, String value, int position)
	{
		try
		{
		Node ChangeNode = doc.getElementsByTagName(element).item(position);
		NamedNodeMap attr = ChangeNode.getAttributes();
		Node nodeAttr = attr.getNamedItem(attribute);
		nodeAttr.setTextContent(value);
		
		return doc;
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return null;
		
	}
	public Document updateXML(Document doc, String element, String attribute, int value, int position)
	{
		try
		{
		Node ChangeNode = doc.getElementsByTagName(element).item(position);
		NamedNodeMap attr = ChangeNode.getAttributes();
		Node nodeAttr = attr.getNamedItem(attribute);
		nodeAttr.setTextContent(String.valueOf(value));
		return doc;
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return null;
		
	}
	
	public String Get_Attribute_Value_Of_Element (Document doc, String Element, String attribute, int position) throws ParserConfigurationException, SAXException, IOException
	{
         try
         {
		 	doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(Element);
			Node nNode = nList.item(position);
			Element eElement = (Element) nNode;
			return eElement.getAttribute(attribute);
         }
         catch (Exception e)
         {
        	 return null;
         }
		
	}
	
	public String Get_Attribute_Value_By_Mapping_Two_Other_Attributes_Of_First_Matching_Element (Document doc,String element, String attribute1, String attributevalue1, String attribute2, String attributevalue2, String attribute3) throws ParserConfigurationException, SAXException, IOException
	{
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(element);
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				 if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					if (eElement.getAttribute(attribute1).equalsIgnoreCase(attributevalue1) &&  eElement.getAttribute(attribute2).equalsIgnoreCase(attributevalue2))
					{
					  return eElement.getAttribute(attribute3);
					}
				}
			}
			return null;
     }
	
	public String[] GetAttributeList (Document doc, String Element, String attribute) throws ParserConfigurationException, SAXException, IOException
	{
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName(Element);
		int i=0;
		String[] getValueList = new String[nList.getLength()];
		
		for(i=0;i<nList.getLength();i++)
		{
		Node nNode = nList.item(i);
		Element eElement = (Element) nNode;
		getValueList[i]= eElement.getAttribute(attribute);
	
		}
		
		return getValueList;
		
	}

	public Document removeEntity(Document doc, String Element)
	{
	
		NodeList nodes = doc.getElementsByTagName(Element);
		int i=0;
			
		for(i=0;i<nodes.getLength();i++)
		{
			 Element chain = (Element)nodes.item(i);
               nodes.item(i).removeChild(chain);
			
		}
		 return doc;
	}	
	
	
	public Document removeElement(Document doc, String element, String attribute, String value, int position)
	{
		try
		{
			Element elem = (Element) doc.getElementsByTagName(element).item(position);
			elem.removeAttribute(attribute);
		
		return doc;
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return null;
		
	}
	
	public Document addAttribute(Document doc, String element, String attribute, String value, int position)
	{
		try
		{
			Element elem = (Element) doc.getElementsByTagName(element).item(position);
			elem.setAttribute(attribute, value);
		
		return doc;
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return null;
		
	}
	
	
	public Document addElement(Document doc, String element, String elementtoadd, int position)
	{
		try
		{
			Element elem = (Element) doc.getElementsByTagName(element).item(position);
			Element newelement  = doc.createElement(elementtoadd);
			elem.appendChild(newelement);
		
		return doc;
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return null;
		
	}
	
	public int getElementCount(Document doc, String element)
	{
				NodeList list = doc.getElementsByTagName(element);
				return list.getLength();
	}
	
	public List<String> getContentFromElementsSubElement(Document doc, String element, String subelement, int position)
	{
		List<String> listData = new ArrayList<String>();
		try
		{
			
			Element elem = (Element) doc.getElementsByTagName(element).item(position);
			NodeList nodelist = elem.getElementsByTagName(subelement);
		
			int i = 0;
			while (i<nodelist.getLength())
			{
				listData.add(nodelist.item(i).getTextContent());
				i++;
			}
			
			return listData;
		}
	
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		
		return listData;
		
	}
}

