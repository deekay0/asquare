package edu.cmu.square.server.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gwt.user.client.rpc.RemoteService;

import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.server.business.interfaces.ExportBusiness;
import edu.cmu.square.server.remoteService.implementations.SquareRemoteServiceServlet;
@Service
public class XmlExportServlet extends SquareRemoteServiceServlet implements RemoteService
{

	private static final long serialVersionUID = -6311961339639458675L;
	private static Logger logger = Logger.getLogger(XmlExportServlet.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// get parameter
		int projectId = Integer.parseInt(req.getParameter("projectId"));
		logger.debug("Project id: " + projectId);

		authorizeServlet(projectId, req, resp);
		
		ExportBusiness exportBusinessLogic = null;
		exportBusinessLogic = getLogic(exportBusinessLogic, ExportBusiness.class);
		
		String format = req.getParameter("format");
		String output = null;
		if (format.equals("XML"))
		{

			resp.setContentType("text/xml");
			resp.setHeader("Content-Disposition", "attachment; filename=requirements.xml");
			try
			{
				output = exportBusinessLogic.exportToXml(projectId);
			}
			catch (SquareException e)
			{
				throw new ServletException(e);
			}
			
		}
		else if (format.equals("CSV"))
		{
			resp.setContentType("text/csv");
			resp.setHeader("Content-Disposition", "attachment; filename=requirements.csv");
			try
			{
				output = exportBusinessLogic.exportToCsv(projectId);
			}
			catch (SquareException e)
			{
				throw new ServletException(e);
			}
		}
		else if (format.equals("HTML"))
		{
			resp.setContentType("text/html");
			resp.setHeader("Content-Disposition", "attachment; filename=requirements.html");
			try
			{
				output = exportBusinessLogic.exportToHtml(projectId);
			}
			catch (SquareException e)
			{
				throw new ServletException(e);
			}
		}
		else if (format.equals("RequisitePro-CSV"))
		{
			resp.setContentType("text/csv");
			resp.setHeader("Content-Disposition", "attachment; filename=requisitePro.csv");
			try
			{
				output = exportBusinessLogic.exportToRequisiteProCsv(projectId);
			}
			catch (SquareException e)
			{
				throw new ServletException(e);
			}
		}
//		write it out
		PrintWriter out = resp.getWriter();
		out.println(output);
	}

	private void authorizeServlet(int projectId, HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		// add authorization stuff
		HttpSession session = req.getSession();
		String serverSessionId = (String) session.getAttribute("sessionID");
		logger.debug("session " + serverSessionId);
		// authentication
		String clientSessionId = (String) req.getParameter("sessionId");
		
		
		if ((clientSessionId == null 
				|| serverSessionId == null)
				||
				!clientSessionId.equals(serverSessionId))
		{
			String contextPath = req.getRequestURL().toString();
			contextPath = contextPath.substring(0, contextPath.lastIndexOf("/SquareGwt")+1);
//			contextPath += "#";
			String path = contextPath; 
//			+ HomePilot.generateNavigationId(HomePilot.PageId.logout) + "?timeout=1";
			logger.info("Path " + path);
			resp.sendRedirect(path);
			return;
		}

	}
	@Override
	public void setValuesForAuthorization()
	{
		// TODO Auto-generated method stub

	}

}
