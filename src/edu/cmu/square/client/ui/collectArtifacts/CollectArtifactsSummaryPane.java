package edu.cmu.square.client.ui.collectArtifacts;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GwtArtifact;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.CollectArtifactsService;
import edu.cmu.square.client.remoteService.step.interfaces.CollectArtifactsServiceAsync;
import edu.cmu.square.client.ui.ChooseStep.ChooseStepPilot;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;


public class CollectArtifactsSummaryPane extends BasePane
{

	//private static final String URL_REGEX = "^(http(s?)\\:\\/\\/|~/|/)([a-zA-Z]{1}([\\w\\-]+\\.)+([\\w]{2,5}))(:[\\d]{1,5})?/?(\\w+\\.[\\w]{3,4})?((\\?\\w+=\\w+)?(&\\w+=\\w+)*)?";
	
	private CollectArtifactsPaneMessages messages = GWT.create(CollectArtifactsPaneMessages.class);
	private CollectArtifactsServiceAsync service = GWT.create(CollectArtifactsService.class);
	
	private List<GwtArtifact> listOfProjectArtifacts = new ArrayList<GwtArtifact>();
	private CreateArtifactDialog createArtifactDialog;
	
	private final FlexTable artifactTable = new FlexTable();
	private DisclosurePanel notFoundDisclousure = new DisclosurePanel();
	private int lastClickedRow = -1;
	

	public CollectArtifactsSummaryPane(State stateInfo)
	{
		super(stateInfo);
		ServiceDefTarget endpoint = (ServiceDefTarget) service;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "collectArtifacts.rpc");

		initializePage();
	}

	private void initializePage()
	{
		this.getContent().clear();
		this.showLoadingStatusBar();
		
	
		
		service.getAllArtifacts(currentState.getProjectID(), new AsyncCallback<List<GwtArtifact>>()
		{
			public void onFailure(Throwable caught)
			{
				Window.alert(caught.getMessage());
			}

			public void onSuccess(List<GwtArtifact> result)
			{
				hideStatusBar();
				listOfProjectArtifacts = result;
				createArtifactsTable();
				
				HorizontalPanel noneFoundLayout = new HorizontalPanel();
				noneFoundLayout.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				noneFoundLayout.setWidth("80%");
				Label noArtifact = new Label(messages.noElementsFound());
				noArtifact.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
				
				notFoundDisclousure.add(noArtifact);        
				notFoundDisclousure.setAnimationEnabled(true);
				noneFoundLayout.add(notFoundDisclousure);
				getContent().add(noneFoundLayout);
				addDoneButton();  ////////////////where to add the button
				showNotFoundItems();
			}
		});
	}

	protected void createArtifactsTable()
	{
		artifactTable.setWidth("80%");
		artifactTable.setStyleName("square-flex");
		artifactTable.setCellSpacing(0);
		RowFormatter rowFormatter = artifactTable.getRowFormatter();
		FlexCellFormatter cellFormatter = artifactTable.getFlexCellFormatter();

		Button addArtifactButton = new Button(messages.addArtifact());
		final CollectArtifactsSummaryPane callerPane = this;
		addArtifactButton.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				lastClickedRow=artifactTable.getRowCount();
				createArtifactDialog = new CreateArtifactDialog(listOfProjectArtifacts, callerPane);
				createArtifactDialog.center();
				createArtifactDialog.setText("Create Artifact");
				createArtifactDialog.show();
			}
		});

		//Header labels
		Label name = new Label(messages.artifactName());
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		Label description = new Label(messages.description());
		cellFormatter.setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		Label revision = new Label(messages.revision());

		if (getCurrentState().getMode() == GwtModesType.ReadOnly)
		{
			artifactTable.setWidget(0, 0, name);
		}
		else if (getCurrentState().getMode() == GwtModesType.ReadWrite)
		{
			artifactTable.setWidget(0, 0, addArtifactButton);
			// add handler
		}
		artifactTable.setWidget(0, 1, description);
		artifactTable.setWidget(0, 2, revision);
		artifactTable.setWidget(0, 3, new Label(" "));

		rowFormatter.setStyleName(0, "square-TableHeader");

		// Add all the data to the table
		for (int i = 1; i <= listOfProjectArtifacts.size(); i++)
		{
			this.updateTable(listOfProjectArtifacts.get(i-1), i, false);
		}

		//Add the table to the main layout
		this.getContent().add(artifactTable);
		//addDoneButton();  ////////////////where to add the button
	}
	
	private void addDoneButton()
	{
		FlexTable buttonPanel = new FlexTable();
		Button done =new Button(messages.done());
		done.addStyleName("square-button");
		done.addClickHandler(new ClickHandler(){
			
			public void onClick(ClickEvent event)
			{
				History.newItem(ChooseStepPilot.generateNavigationId(ChooseStepPilot.PageId.home));
				
			}});
		buttonPanel.setWidget(0,0,new Label(" "));
		buttonPanel.setWidget(1, 0, done);
		buttonPanel.setWidth("80%");
		buttonPanel.getCellFormatter().setHorizontalAlignment(1,0,HasHorizontalAlignment.ALIGN_RIGHT);
		this.getContent().add(buttonPanel);
	}
	
	public void createArtifact(final GwtArtifact newArtifact)
	{
		this.showStatusBar(messages.adding());

		service.createArtifact(newArtifact, this.getCurrentState().getProjectID(), new AsyncCallback<GwtArtifact>()
		{
			public void onFailure(Throwable caught)
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, "adding artifacts");
			}
			@Override
			public void onSuccess(GwtArtifact result)
			{
				hideStatusBar();
				updateTable(result, lastClickedRow, true);
				yellowFadeHandler.add(artifactTable, lastClickedRow);
				listOfProjectArtifacts.add(result);
				showNotFoundItems();
			}
		});
	}
	
	
	public void updateArtifact(final GwtArtifact artifactToUpdate)
	{
		this.showStatusBar(messages.updating());

		service.updateArtifact(artifactToUpdate, new AsyncCallback<Void>()
		{
			public void onFailure(Throwable caught)
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, "updating artifacts");
			}
			@Override
			public void onSuccess(Void result)
			{
				hideStatusBar();
				
				updateTable(artifactToUpdate, lastClickedRow, true);
				listOfProjectArtifacts.set(lastClickedRow-1, artifactToUpdate);
				yellowFadeHandler.add(artifactTable, lastClickedRow);
			}
		});
		
	}
	

	public void removeArtifact(final int artifactId)
	{
		this.showStatusBar(messages.removing());
		
		service.deleteArtifact(artifactId, new AsyncCallback<Void>()
		{
			public void onFailure(Throwable caught)
			{
				ExceptionHelper.SquareRootRPCExceptionHandler(caught, "deleting artifacts");
			}

			@Override
			public void onSuccess(Void result)
			{
				hideStatusBar();
				artifactTable.removeRow(lastClickedRow);
				listOfProjectArtifacts.remove(getArtifactById(artifactId));
				showNotFoundItems();
			}
		});
	}
	
	
	private GwtArtifact getArtifactById(int artifactId)
	{
		for (GwtArtifact artifact: this.listOfProjectArtifacts)
		{
			if (artifact.getId() == artifactId)
			{
				return artifact;
			}
		}
		
		return null;
	}
	

	private void updateTable(final GwtArtifact artifact,  final int row, boolean showYFT)
	{
		final CollectArtifactsSummaryPane callerPane = this;
		
		//set up the basic labels
		Label descLabel = new Label(artifact.getDescription());
		Label revLabel = new Label(artifact.getRevision());
		final ArtifactLink changeLink = new ArtifactLink(artifact.getId(), messages.change());
		
		//set up the change clickHandler
		changeLink.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				lastClickedRow = artifactTable.getCellForEvent(event).getRowIndex();
				CreateArtifactDialog popup = new CreateArtifactDialog(getArtifactById(changeLink.getArtifactId()), listOfProjectArtifacts, callerPane);
				popup.center();
				popup.setModal(true);
				popup.show();
				
			}
		});

		//set up the remove click handler
		final ArtifactLink removeLink = new ArtifactLink(artifact.getId(), messages.remove());
		removeLink.addClickHandler(new ClickHandler()
		{
			public void onClick(ClickEvent event)
			{
				boolean response = Window.confirm(messages.confirmDelete());
				if (response)
				{
					lastClickedRow = artifactTable.getCellForEvent(event).getRowIndex();
					removeArtifact(removeLink.getArtifactId());
					
				}
			}
		});

		//set up the link label that goes under the title.
		String url=artifact.getLink().trim().toLowerCase();
		
		VerticalPanel linkPanel = new VerticalPanel();
		linkPanel.setStyleName("inner-table");
		Label path = new Label();
		Label nameLabel= new  Label();
		
		path.setText(artifact.getLink());
		
		if(url.startsWith("http")|url.startsWith("https")|url.startsWith("ftp")|url.startsWith("\\"))//if (artifact.getLink().matches(URL_REGEX))
		{
			SquareHyperlink squareLink = new SquareHyperlink(artifact.getName());
			squareLink.addClickHandler(new ClickHandler(){

				public void onClick(ClickEvent event)
				{
					Window.open(artifact.getLink(), "_blank", "");
					
				}});
			
			
			linkPanel.add(squareLink);
			
		}
		else
		{
			nameLabel.setText(artifact.getName());
			linkPanel.add(nameLabel);
		}
		
		linkPanel.add(path);
		
		artifactTable.setWidget(row, 0, linkPanel);

		FlexCellFormatter formatter = this.artifactTable.getFlexCellFormatter();
		formatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_LEFT);
		artifactTable.setWidget(row, 1, descLabel);
		formatter.setHorizontalAlignment(row, 1, HasHorizontalAlignment.ALIGN_LEFT);
		artifactTable.setWidget(row, 2, revLabel);
		
		if (getCurrentState().getMode() == GwtModesType.ReadOnly)
		{
			artifactTable.setWidget(row, 3, new Label(" "));//empty colum
		}
		else if (getCurrentState().getMode() == GwtModesType.ReadWrite)
		{
			HorizontalPanel buttonPanel = new HorizontalPanel();
			buttonPanel.setStyleName("flex-link-bar");
			buttonPanel.add(changeLink);
			buttonPanel.add(removeLink);

			artifactTable.setWidget(row, 3, buttonPanel);
			formatter.setHorizontalAlignment(row, 3, HasHorizontalAlignment.ALIGN_RIGHT);
		}
		
	}
	
	private void showNotFoundItems()
	{
		if (listOfProjectArtifacts.size() == 0)
		{
			notFoundDisclousure.setOpen(true);

		}
		else
		{
			notFoundDisclousure.setOpen(false);
		}
	}


	class ArtifactLink extends SquareHyperlink
	{
		private int artifactId;
		
		public ArtifactLink(int artifactId, String text)
		{
			super(text);
			this.artifactId = artifactId;
		}

		public void setArtifactId(int artifactId)
		{
			this.artifactId = artifactId;
		}

		public int getArtifactId()
		{
			return artifactId;
		}

	}


}
