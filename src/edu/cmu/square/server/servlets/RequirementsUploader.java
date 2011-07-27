/**
 * 
 */
package edu.cmu.square.server.servlets;

import java.beans.Introspector;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.server.business.interfaces.UploadRequirementsBusiness;

/**
 * @author kaalpurush
 * 
 */
public class RequirementsUploader extends HttpServlet
{

	private static final long serialVersionUID = -8958275085300202147L;
	private static Logger logger = Logger.getLogger(RequirementsUploader.class);
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		int projectId = Integer.parseInt(req.getParameter("projectId"));
		String fileType = req.getParameter("fileType");
//		if (authorizeServlet(projectId, req, resp))
//
//		{
			if (ServletFileUpload.isMultipartContent(req))
			{
				// Create a factory for disk-based file items
				FileItemFactory factory = new DiskFileItemFactory();

				// Create a new file upload handler
				ServletFileUpload upload = new ServletFileUpload(factory);

				// Parse the request
				try
				{
					List<FileItem> items = upload.parseRequest(req);
					for (FileItem item : items)
					{
						// process only file upload - discard other form item
						// types
						if (item.isFormField())
							continue;

						String fileName = item.getName();
						// get only the file name not whole path
						if (fileName != null)
						{
							fileName = FilenameUtils.getName(fileName);
						}
						logger.info("uploaded file:" + fileName);
						// get the file contents as input stream
						// provide it to a business logic class, which will
						// handle
						// parsing and uploading requirements
						HttpSession session = req.getSession();
						boolean sessionExpired = session.getAttribute("sessionID")==null;
						
						if (sessionExpired)
						{
							logger.info("session expired while importing");
						}
						
						UploadRequirementsBusiness uploadRequirements = getLogic();
						uploadRequirements.setProjectName(session.getAttribute("projectName").toString());
						uploadRequirements.setUserName(session.getAttribute("userName").toString());
						uploadRequirements.setSessionExpired(sessionExpired);
						
						int numberOfRequirements = 0;
						if (fileType.equals("XML"))
						{
							numberOfRequirements = uploadRequirements.uploadFromStream(item.getInputStream(), projectId);
						}
						else if (fileType.equals("RequisitePro-CSV"))
						{
							numberOfRequirements = uploadRequirements.uploadFromRequisitePro(item.getString(), projectId);
						}
						resp.getWriter().print(numberOfRequirements + " requirements from " + fileName + " were created successfully.");
						resp.flushBuffer();
					}

				}
				catch (SquareException se)
				{
					switch (se.getType())
					{
						case titleMissing:
							resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error in input file : " + se.getMessage());
							break;
						case descriptionMissing:
							resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Error in input file : " + se.getMessage());
							break;
						case parseError:
							resp.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, "Error in input file : " + se.getMessage());
							break;
						case emptyFile:
							resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Error in input file : " + se.getMessage());
							break;
					}
					
				}
				catch (Exception e)
				{
					resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while creating the file : " + e.getMessage());
				}

			}
			else
			{
				resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE, "Request contents type is not supported by the servlet.");
			}
		}
//	}
	private UploadRequirementsBusiness getLogic()
	{
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		UploadRequirementsBusiness urb = (UploadRequirementsBusiness) ctx.getBean("uploadRequirementsBusinessImpl");
		return urb;
	}

	
}
