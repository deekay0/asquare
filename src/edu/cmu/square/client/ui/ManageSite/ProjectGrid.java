package edu.cmu.square.client.ui.ManageSite;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectService;
import edu.cmu.square.client.remoteService.interfaces.ManageProjectServiceAsync;
import edu.cmu.square.client.remoteService.interfaces.ManageSiteService;
import edu.cmu.square.client.remoteService.interfaces.ManageSiteServiceAsync;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareConfirmDialog;
import edu.cmu.square.client.ui.core.SquareHyperlink;

public class ProjectGrid extends Composite implements Command
{
	private BasePane caller = null;

	private FlexTable projectTable = new FlexTable();

	final ManageSitePaneMessages messages = (ManageSitePaneMessages) GWT.create(ManageSitePaneMessages.class);
	private ManageProjectServiceAsync manageProjectService = GWT.create(ManageProjectService.class);
	private ManageSiteServiceAsync manageSiteService = GWT.create(ManageSiteService.class);

	private SquareConfirmDialog confirmDialog;
	private int lastRowClicked = -1;
	private int lastProjectIdClicked = -1;

	protected List<GwtProject> listOfProjects = null;

	public ProjectGrid(BasePane caller)
		{
			this.caller = caller;
			this.caller.showLoadingStatusBar();

			this.projectTable.setWidth("100%");
			this.projectTable.setCellSpacing(0);
			this.projectTable.setStyleName("square-flex");

			this.setHeaderInTable();
			this.getAllProjects();

			initWidget(this.projectTable);
		}

	private void setHeaderInTable()
	{
		projectTable.getRowFormatter().setStyleName(0, "square-TableHeader");

		Button createButton = new Button(messages.createProject());

		projectTable.setWidget(0, 0, createButton);
		projectTable.setWidget(0, 1, new Label(messages.projectType()));
		projectTable.setWidget(0, 2, new Label(messages.leadRequirementsEngineer()));
		projectTable.getFlexCellFormatter().setColSpan(0, 2, 2);

		createButton.addClickHandler(new ClickHandler()
			{
				@Override
				public void onClick(ClickEvent event)
				{
					lastRowClicked = -1;
					loadCreateDialog(new GwtProject());
				}
			});
	}

	public void createProject(GwtProject newProject, boolean useDefaultTerms, boolean useDefaultTechniques, boolean useDefaultEvaluations, boolean useDefaultInspections)
	{
		this.caller.showStatusBar(messages.creatingProject());
		ServiceDefTarget endpoint = (ServiceDefTarget) manageProjectService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageProject.rpc");
		manageProjectService.createProject(newProject, useDefaultTerms,useDefaultTechniques,useDefaultEvaluations,useDefaultInspections, new AsyncCallback<GwtProject>()
			{
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.creatingProject());
					caller.hideStatusBar();
				}

				public void onSuccess(GwtProject result)
				{
					listOfProjects.add(result);
					addProjectToTable(result);
					caller.yellowFadeHandler.add(projectTable, projectTable.getRowCount() - 1);
					caller.hideStatusBar();
				}
			});
	}

	public void updateProject(int projectId, int newLeadRequirementEngineerId, String projectName)
	{
		this.caller.showStatusBar(messages.creatingProject());
		ServiceDefTarget endpoint = (ServiceDefTarget) manageProjectService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageProject.rpc");
		manageProjectService.updateProject(projectId, newLeadRequirementEngineerId, projectName, new AsyncCallback<GwtProject>()
			{
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.creatingProject());
					caller.hideStatusBar();
				}

				@Override
				public void onSuccess(GwtProject result)
				{
					listOfProjects.set(lastRowClicked - 1, result);
					updateProjectToTable(lastRowClicked, result);
					caller.yellowFadeHandler.add(projectTable, lastRowClicked);
					caller.hideStatusBar();
				}
			});
	}

	private void deleteProject(int projectId)
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) manageProjectService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageProject.rpc");

		manageProjectService.deleteProject(projectId, new AsyncCallback<Void>()
			{
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.deletingProject());
					caller.hideStatusBar();

				}
				public void onSuccess(Void result)
				{
					projectTable.removeRow(lastRowClicked);
					listOfProjects.remove(lastRowClicked - 1);
					caller.hideStatusBar();

				}
			});
	}

	private void loadCreateDialog(final GwtProject project)
	{
		final ProjectGrid caller = this;

		ServiceDefTarget endpoint = (ServiceDefTarget) manageSiteService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageSite.rpc");

		manageSiteService.getAllUsers(new AsyncCallback<List<GwtUser>>()
			{
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.retrievingUsers());
				}

				public void onSuccess(List<GwtUser> result)
				{
					if (project.isInDatabase())
					{
						CreateProjectDialog dialog = new CreateProjectDialog(project.getId(), project.getName(),project.getLeadRequirementEngineer().getUserId(), listOfProjects, result, caller, project.getCases().getId());
						dialog.center();
						dialog.setModal(true);
						dialog.show();
						
					}
					else
					{
						CreateProjectDialog dialog = new CreateProjectDialog( listOfProjects, result, caller);
						dialog.center();
						dialog.setModal(true);
						dialog.show();
					}

				}
			});
	}

	private void getAllProjects()
	{
		ServiceDefTarget endpoint = (ServiceDefTarget) manageProjectService;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "manageProject.rpc");

		manageProjectService.getAllProjects(new AsyncCallback<List<GwtProject>>()
			{
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, messages.retrievingAllProjects());
				}

				public void onSuccess(List<GwtProject> result)
				{
					listOfProjects = result;
					for (GwtProject currentProject : result)
					{
						addProjectToTable(currentProject);
					}

					caller.hideStatusBar();
				}
			});
	}

	private void addProjectToTable(final GwtProject project)
	{
		int row = this.projectTable.getRowCount();

		updateProjectToTable(row, project);
	}

	private void updateProjectToTable(int row, final GwtProject project)
	{

		this.projectTable.setWidget(row, 0, new Label(project.getName()));

		Label type = new Label();
		if (project.isPrivacy() && !project.isSecurity())
		{
			type.setText(messages.privacyOnly());
		}
		else if (!project.isPrivacy() && project.isSecurity())
		{
			type.setText(messages.securityOnly());
		}
		else
		{
			type.setText(messages.securityAndPrivacy());
		}

		this.projectTable.setWidget(row, 1, type);
		this.projectTable.setWidget(row, 2, new Label(project.getLeadRequirementEngineer().getFullName()));

		final Command caller = this;

		SquareHyperlink deleteProjectLink = new SquareHyperlink(messages.permanentlyDelete());
		deleteProjectLink.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		deleteProjectLink.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					lastRowClicked = projectTable.getCellForEvent(event).getRowIndex();
					lastProjectIdClicked = project.getId();

					confirmDialog = new SquareConfirmDialog(caller, messages.confirmDeleteProject(project.getName()), messages.deleteForever(),
							messages.cancelDeleteProject());
					confirmDialog.setText(messages.confirmDeleteDialogTitle());
					confirmDialog.center();
					confirmDialog.show();

				}
			});

		SquareHyperlink editProjectLink = new SquareHyperlink(messages.edit());
		editProjectLink.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		editProjectLink.addClickHandler(new ClickHandler()
			{
				public void onClick(ClickEvent event)
				{
					lastRowClicked = projectTable.getCellForEvent(event).getRowIndex();
					lastProjectIdClicked = project.getId();
					loadCreateDialog(project);
				}
			});

		HorizontalPanel links = new HorizontalPanel();
		links.setStyleName("flex-link-bar");
		links.add(editProjectLink);
		links.add(deleteProjectLink);

		this.projectTable.setWidget(row, 3, links);

		this.projectTable.getCellFormatter().setHorizontalAlignment(row,3,	HasHorizontalAlignment.ALIGN_RIGHT);

	}
	

	@Override
	public void execute()
	{

		if (confirmDialog.isConfirmed())
		{
			caller.showStatusBar(messages.removing());
			deleteProject(lastProjectIdClicked);
		}

	}

}
