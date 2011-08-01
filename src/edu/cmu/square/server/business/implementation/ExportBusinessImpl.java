package edu.cmu.square.server.business.implementation;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.proofofconcept.RequisiteProTransformations;
import edu.cmu.square.server.business.interfaces.ExportBusiness;
import edu.cmu.square.server.dao.interfaces.ArtifactDao;
import edu.cmu.square.server.dao.interfaces.AssetDao;
import edu.cmu.square.server.dao.interfaces.CategoryDao;
import edu.cmu.square.server.dao.interfaces.GoalDao;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.RequirementDao;
import edu.cmu.square.server.dao.interfaces.RiskDao;
import edu.cmu.square.server.dao.interfaces.TermDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Requirement;
@Service
@Scope("prototype")
public class ExportBusinessImpl extends BaseBusinessImpl implements ExportBusiness
{
	private static Logger logger = Logger.getLogger(ExportBusinessImpl.class);
	private static final String CSV_DELIMITTER = ",";
	private static final String XSLT_FILE = "export.xsl";
	
	@Resource
	private RequirementDao requirementDao;
	@Resource
	private CategoryDao categoryDao;

	@Resource
	private ArtifactDao artifactDao;

	@Resource
	private AssetDao assetDao;

	@Resource
	private TermDao termDao;

	@Resource
	private RiskDao riskDao;

	@Resource
	private GoalDao goalDao;

	@Resource
	private ProjectDao projectDao;

	private TransformerFactory xsltTransformerFactory;

	public ExportBusinessImpl()
		{
			xsltTransformerFactory = TransformerFactory.newInstance();
		}

	public String exportToHtml(Integer projectId) throws SquareException
	{
		String exportedXml = exportToXml(projectId);

		if (exportedXml.isEmpty())
		{
			return exportedXml;
		}

		StringWriter sw = new StringWriter();
		String path = Thread.currentThread().getContextClassLoader().getResource(XSLT_FILE).getFile();
		try
		{
			Transformer transformer = xsltTransformerFactory.newTransformer(new StreamSource(path));
			transformer.transform(new StreamSource(new StringReader(exportedXml)), new StreamResult(sw));
		}

		catch (TransformerException e)
		{

			SquareException se = new SquareException("Error creating transformerFactory ", e);
			throw se;
		}
		return sw.toString();
	}

	@Override
	public String exportToXml(Integer projectId) throws SquareException
	{
		Project currentProject = projectDao.fetch(projectId);
		if (currentProject == null)
		{
			return "";
		}
		// create and send document
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Square");

		// add assets
		assetDao.addElements(root, "AssetElements", projectId);
		// add goals
		goalDao.addElements(root, "GoalElements", projectId);

		termDao.addElements(root, "TermElements", projectId);

		// adding requirements
		List<Element> results = requirementDao.exportToXML(projectId);
		Element requirements = root.addElement("RequirementElements");
		for (Element e : results)
		{
			Node n = e.selectSingleNode("userAhpsForRidB");
			if (n != null)
			{
				e.remove(n);
			}

			n = e.selectSingleNode("userAhpsForRidA");

			if (n != null)
			{
				e.remove(n);
			}
			requirements.add(e);
		}

		// add artifacts
		artifactDao.addElements(root, "ArtifactElements", projectId);

		// adding categories
		categoryDao.addElements(root, "CategoryElements", projectId);

		// add risks
		riskDao.addElements(root, "RiskElements", projectId);

		// get other XMLs and append
		return doc.asXML();
	}

	@Override
	public String exportToCsv(Integer projectId) throws SquareException
	{
		Project currentProject = projectDao.fetch(projectId);
		if (currentProject == null)
		{
			return "";
		}
		Set<Requirement> reqs = currentProject.getRequirements();
		List<Requirement> requirements = new ArrayList<Requirement>(reqs);

		Collections.sort(requirements, new Comparator<Requirement>()
			{

				@Override
				public int compare(Requirement o1, Requirement o2)
				{
					if (o1 == null || o2 == null)
						return 0;
					if (o1.getPriority() > o1.getPriority())
					{
						return 1;
					}
					if (o1.getPriority() < o1.getPriority())
					{
						return -1;
					}
					return 0;

				}

			});
		StringBuilder requirementCsvFile = new StringBuilder();

		requirementCsvFile.append("\"Id\"");
		requirementCsvFile.append(CSV_DELIMITTER);
		requirementCsvFile.append("\"Title\"");
		requirementCsvFile.append(CSV_DELIMITTER);
		requirementCsvFile.append("\"Description\"");
		requirementCsvFile.append(CSV_DELIMITTER);

		requirementCsvFile.append("\"Project type\"");
		requirementCsvFile.append(CSV_DELIMITTER);
		requirementCsvFile.append("\"priority\"");
		requirementCsvFile.append("\n");

		for (Requirement r : requirements)
		{
			requirementCsvFile.append("\"" + r.getId() + "\"");
			requirementCsvFile.append(CSV_DELIMITTER);
			requirementCsvFile.append("\"" + r.getTitle() + "\"");
			requirementCsvFile.append(CSV_DELIMITTER);
			requirementCsvFile.append("\"" + r.getDescription() + "\"");
			requirementCsvFile.append(CSV_DELIMITTER);
			String projectType = null;
			if (r.isSecurity() && r.isPrivacy())
			{
				projectType = "Both";
			}
			else if (r.isPrivacy())
			{
				projectType = "Privacy";
			}
			else if (r.isSecurity())
			{
				projectType = "Security";
			}
			requirementCsvFile.append("\"" + projectType + "\"");
			requirementCsvFile.append(CSV_DELIMITTER);

			requirementCsvFile.append("\"" + r.getPriority() + "\"");
			requirementCsvFile.append("\n");
		}
		return requirementCsvFile.toString();
	}

	@Override
	public String exportToRequisiteProCsv(int projectId) throws SquareException
	{
		String xmlString = exportToXml(projectId);
		RequisiteProTransformations tranformer = new RequisiteProTransformations();
		//convert it to XML
	    String requisiteProCsv = tranformer.transform_SquareXML_To_ReqProCSV(xmlString);
		return requisiteProCsv;
	}

}
