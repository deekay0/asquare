package edu.cmu.square.server.business.interfaces;

import java.io.InputStream;

import edu.cmu.square.client.exceptions.SquareException;

public interface UploadRequirementsBusiness extends BaseBusinessInterface
{
	/**
	 * This takes an xml inputstream an parses it for requirements.
	 * The xml must be in SQUARE format.
	 * After parsing XML, it uploads the requirements to the database.
	 * These requirements can be viewed from Step 6.
	 * However, these requirements do not have any traceability.
	 * @param inputStream
	 * @param projectId 
	 * @return the number of requirements saved
	 * @throws SquareException 
	 */
	int uploadFromStream(InputStream inputStream, int projectId) throws SquareException;

	int uploadFromRequisitePro(String fileContent, int projectId) throws SquareException;

}
