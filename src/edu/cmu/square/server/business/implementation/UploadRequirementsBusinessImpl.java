package edu.cmu.square.server.business.implementation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.proofofconcept.RequisiteProTransformations;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.interfaces.UploadRequirementsBusiness;
import edu.cmu.square.server.dao.interfaces.RequirementDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Requirement;
@Service
public class UploadRequirementsBusinessImpl extends BaseBusinessImpl implements UploadRequirementsBusiness
{

	@Resource
	private RequirementDao requirementDao;

	@Override
	public int uploadFromStream(InputStream requirementsFile, int projectId) throws SquareException
	{
		// parse xml
		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		List<Requirement> requirementsList = new ArrayList<Requirement>();
		try
		{

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			// parse the file and also register this class for call backs
			sp.parse(requirementsFile, new RequirementsXmlHandler(requirementsList, projectId));
			if (requirementsList.isEmpty())
			{
				SquareException se = new SquareException("Empty or malformatted file");
				se.setType(ExceptionType.emptyFile);
				throw se;
			}
			validateRequirementsList(requirementsList);
		}
		catch (SquareException se)
		{
			throw se;
		}
		catch (SAXException se)
		{
			SquareException s = new SquareException(se.getMessage());
			s.setType(ExceptionType.parseError);
			throw s;
		}
		catch (ParserConfigurationException pce)
		{
			SquareException s = new SquareException(pce.getMessage());
			s.setType(ExceptionType.parseError);
			throw s;
		}
		catch (IOException ie)
		{
			SquareException s = new SquareException(ie.getMessage());
			s.setType(ExceptionType.parseError);
			throw s;
		}
		// upload to database
		for (Requirement r : requirementsList)
		{
			requirementDao.create(r);
		}
		return requirementsList.size();

	}
	private void validateRequirementsList(List<Requirement> requirementsList) throws SquareException
	{
		for (Requirement r: requirementsList)
		{
			if (r.getTitle() == null || r.getTitle().equals(""))
			{
				SquareException se = new SquareException("Title is required");
				se.setType(ExceptionType.titleMissing);
				throw se;
			}
			if (r.getDescription() == null || r.getDescription().equals(""))
			{
				SquareException se = new SquareException("Description is required");
				se.setType(ExceptionType.descriptionMissing);
				throw se;
			}
		}
		
	}
	private class RequirementsXmlHandler extends DefaultHandler
	{
		private Requirement currentRequirement;
		private List<Requirement> requirementsList;
		private String tempVal;
		private int projectId;
		public RequirementsXmlHandler(List<Requirement> requirementsList, int projectId)
			{
				this.requirementsList = requirementsList;
				this.projectId = projectId;
			}
		// Event Handlers
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
		{
			// reset
			tempVal = "";
			if (qName.equalsIgnoreCase("requirement"))
			{
				// create a new instance of employee
				currentRequirement = new Requirement();
				currentRequirement.setProject(new Project(projectId));
				currentRequirement.setPriority(0);
				currentRequirement.setSecurity(true);
				currentRequirement.setPrivacy(false);
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException
		{
			tempVal = new String(ch, start, length);
		}
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException
		{

			if (qName.equalsIgnoreCase("requirement"))
			{
				// add it to the list
				requirementsList.add(currentRequirement);

			}
			else if (qName.equalsIgnoreCase("title"))
			{
				currentRequirement.setTitle(tempVal.trim());
			}
			else if (qName.equalsIgnoreCase("description"))
			{
				currentRequirement.setDescription(tempVal.trim());
			}
			

		}
		
		

	}
	@Override
	@AllowedRoles(roles = {Roles.Acquisition_Organization_Engineer})
	public int uploadFromRequisitePro(String fileContent, int projectId) throws SquareException
	{
		// get a csv requirements file
		RequisiteProTransformations tranformer = new RequisiteProTransformations();
		
		//convert it to XML
	    String squareXML = tranformer.transform_ReqProCSV_To_SquareXML(fileContent);
	    byte[] byteArray = squareXML.getBytes();
	    if (byteArray.length <= 0)
	    {
	    	SquareException se = new SquareException("Parsing error");
	    	se.setType(ExceptionType.emptyFile);
	    	throw se;
	    }
	    InputStream xmlStream = new ByteArrayInputStream(byteArray);
	    
		return uploadFromStream (xmlStream, projectId);
	}
}
