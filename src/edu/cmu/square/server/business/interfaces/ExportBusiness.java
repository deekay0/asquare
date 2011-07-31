package edu.cmu.square.server.business.interfaces;

import edu.cmu.square.client.exceptions.SquareException;

public interface ExportBusiness extends BaseBusinessInterface
{
	/**
	 * Exports all requirements, risks, assests, goals, priorities, and categories of a given project
	 * @param projectId TODO
	 * @return A string format for the xml
	 */
	String exportToXml(Integer projectId)throws SquareException;
	
	/**
	 * Exports the prioritized requirements of a given project.
	 * @param projectId The id of the given project
	 * @return Requirements in csv format
	 */
	String exportToCsv(Integer projectId) throws SquareException;

	/**
	 * Exports the requirements to XML with a linked XSLT stylesheet
	 * @param projectId
	 * @return XML with XSLT
	 * @throws SquareException 
	 */
	String exportToHtml(Integer projectId) throws SquareException;

	String exportToRequisiteProCsv(int projectId) throws SquareException;
	
}
