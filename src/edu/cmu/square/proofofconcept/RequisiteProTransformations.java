package edu.cmu.square.proofofconcept;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;

public class RequisiteProTransformations
{

	private String reqProCSVHeader = "<Tag>,\"Requirement text\",\"Name\"\n";

	/**
	 * Transform a Requisite Pro CSV format to SquareRoot
	 * 
	 * @param SQUARE_XML
	 * @return ReqProCSV
	 */
	public String transform_SquareXML_To_ReqProCSV(String SQUARE_XML)
	{

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		StringBuilder result = new StringBuilder();
		Document doc = null;
		try
		{

			InputStream defaultValueStream = new ByteArrayInputStream(SQUARE_XML.getBytes("UTF-8"));
			db = dbf.newDocumentBuilder();
			doc = db.parse(defaultValueStream);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("Requirement");
			result.append(reqProCSVHeader);

			for (int s = 0; s < nodeLst.getLength(); s++)
			{
				Node fstNode = nodeLst.item(s);
				if (fstNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element fstElmnt = (Element) fstNode;
					NodeList nodeList = fstNode.getChildNodes();

					String title = getNodeValue("title", nodeList);
					String description = getNodeValue("description", nodeList);
					if (title.trim().length() > 0 || description.trim().length() > 0)
					{
						result.append("\"XX\",");
						result.append("\"" + description + "\"");
						result.append(",");
						result.append("\"" + title  + "\"\n");
					}

				}
			}

			System.out.println(result.toString());

		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (SAXException e)
		{
			e.printStackTrace();
			return null;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		return result.toString();
	}
	public String transform_ReqProCSV_To_SquareXML(InputStream inputFile)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputFile));
		StringBuilder sb = new StringBuilder();
		String line = "";
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				inputFile.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return transform_ReqProCSV_To_SquareXML(sb.toString());

	}
	/**
	 * 
	 * @param ReqProCSV
	 * @return SQUARE_XML
	 */
	public String transform_ReqProCSV_To_SquareXML(String ReqProCSV)
	{

		StringBuilder result = new StringBuilder();
		result.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		result.append("<Square>\n");
		result.append("<RequirementElements>\n");

		List<Requirement> list = getRequirementList(ReqProCSV);

		for (Requirement r : list)
		{
			result.append("<Requirement>\n");
			result.append("<title>");
			result.append(r.title);
			result.append("</title>\n");
			result.append("<description>");
			result.append(r.description);
			result.append("</description>\n");
			result.append("</Requirement>\n");
		}

		result.append("</RequirementElements>\n");
		result.append("</Square>");

		System.out.println(result.toString());
		return result.toString();

	}
	/**
	 * 
	 * @param SQUARE_XML
	 * @return ReqProCSV
	 * @throws SquareException 
	 */
	public String transform_SquareXML_Terms_To_ReqProCSV(String SQUARE_XML) throws SquareException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		StringBuilder result = new StringBuilder();
		Document doc = null;
		try
		{
			InputStream defaultValueStream = new ByteArrayInputStream(SQUARE_XML.getBytes());
			db = dbf.newDocumentBuilder();
			doc = db.parse(defaultValueStream);
			doc.getDocumentElement().normalize();
			NodeList nodeLst = doc.getElementsByTagName("Term");
			result.append(reqProCSVHeader);

			for (int s = 0; s < nodeLst.getLength(); s++)
			{
				Node fstNode = nodeLst.item(s);
				if (fstNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element fstElmnt = (Element) fstNode;
					NodeList nodeList = fstNode.getChildNodes();

					String title = getNodeValue("term", nodeList);
					String description = getNodeValue("definition", nodeList);
					if (title.trim().length() > 0 || description.trim().length() > 0)
					{
						result.append("XX,");
						result.append("\"" + description + "\"");
						result.append(",");
						result.append("\"" + title + "\",," + "\n");
					}

				}
			}
			System.out.println(result.toString());

		}
		catch (ParserConfigurationException e)
		{
			SquareException se = new SquareException("Error in parser");
			se.setType(ExceptionType.parseError);
			throw se;
		}
		catch (SAXException e)
		{
			SquareException se = new SquareException("Error in parser");
			se.setType(ExceptionType.parseError);
			throw se;
		}
		catch (IOException e)
		{
			SquareException se = new SquareException("Error in parser");
			se.setType(ExceptionType.parseError);
			throw se;
		}
		return result.toString();
	}

	private String getNodeValue(String tagName, NodeList list)
	{

		for (int s = 0; s < list.getLength(); s++)
		{
			Node fstNode = list.item(s);
			if (fstNode == null)
			{
				return "";
			}
			if (fstNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element fstElmnt = (Element) fstNode;

				if (fstElmnt.getTagName().toLowerCase().trim() == tagName)
				{
					return fstElmnt.getTextContent();
				}

			}
		}

		return "";
	}

	private List<Requirement> getRequirementList(String ReqProCSV)
	{

		ArrayList<Requirement> requirements = new ArrayList<Requirement>();

		String[] lines = ReqProCSV.split("\n");
		// Read File Line By Line
		int i = 0;
		for (String line : lines)
		{
			
			if (i==0)
			{
				i++;
				continue;
			}
			if (line.startsWith("\""))
			{

				String[] values = line.split("\",\"");
				Requirement requirement = new Requirement();
				requirement.title = values[2];
				if(requirement.title.endsWith("\""))
				{
					requirement.title=requirement.title.substring(0,requirement.title.length()-1);
				}
				requirement.description = values[1];
				requirements.add(requirement);
			}
		}

		return requirements;

	}

	class Requirement
	{
		private String title;
		private String description;
	}

}
