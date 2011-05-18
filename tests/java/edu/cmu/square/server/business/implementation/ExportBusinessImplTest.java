package edu.cmu.square.server.business.implementation;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.server.base.AbstractSpringBase;
import edu.cmu.square.server.business.interfaces.ExportBusiness;

public class ExportBusinessImplTest extends AbstractSpringBase
{
	private static Logger logger = Logger.getLogger(ExportBusinessImplTest.class); 
	@Resource
	private ExportBusiness exportBusiness;
	@Test
	public void testExportToXml()
	{
		String exportedRequirements;
		try
		{
			exportedRequirements = exportBusiness.exportToXml(38);
			logger.debug("exported requirements " + exportedRequirements);
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Test
	public void testExportToCsv()
	{
		String exportedRequirements;
		try
		{
			exportedRequirements = exportBusiness.exportToCsv(38);
			logger.info("exported requirements as csv: " + exportedRequirements);
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testExportToHtml()
	{
		String exportedRequirements;
		try
		{
			exportedRequirements = exportBusiness.exportToHtml(38);
			logger.info("exported requirements as csv: " + exportedRequirements);
		}
		catch (SquareException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
