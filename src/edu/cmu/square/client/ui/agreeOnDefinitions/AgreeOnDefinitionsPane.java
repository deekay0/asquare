package edu.cmu.square.client.ui.agreeOnDefinitions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GwtModesType;
import edu.cmu.square.client.model.GwtProject;
import edu.cmu.square.client.model.GwtTerm;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.step.interfaces.AgreeOnDefinitionsService;
import edu.cmu.square.client.remoteService.step.interfaces.AgreeOnDefinitionsServiceAsync;
import edu.cmu.square.client.ui.ChooseStep.ChooseStepPilot;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;
import edu.cmu.square.client.ui.core.SquareWaterMarkTextBox;

public class AgreeOnDefinitionsPane extends BasePane
{
	private int projectID;
	private List<GwtTerm> listOfTerms = new ArrayList<GwtTerm>();
	private List<GwtTerm> listOfFilteredTerms = new ArrayList<GwtTerm>();

	

	private VerticalPanel vPane = new VerticalPanel();
	private VerticalPanel vPaneData = new VerticalPanel();
	private int modifyTermId = -1;

	private CreateTermDialog createTermDialog;
	private GwtTerm newTerm;
	private String lastSearch="";
	
	private int pageSize=10;
	private int currentPage=1;
	private int pageCount=0;

	final AgreeOnDefinitionsMessages messages = (AgreeOnDefinitionsMessages) GWT.create(AgreeOnDefinitionsMessages.class);

	public AgreeOnDefinitionsPane(State stateInfo)
		{
			super(stateInfo);
			

			this.projectID = this.getCurrentState().getProjectID();
			SetQueryString();
			this.showLoadingStatusBar();
			loadTerms();

		}

	public void SetQueryString()
		{
		
			if(this.getQueryString().get("PageId")!=null)
			{
				
					try
					{
					this.currentPage=Integer.parseInt(this.getQueryString().get("PageId"));
					}
					catch(Exception ex)
					{
						this.currentPage=1;
					}
				
			}
			
			if(this.getQueryString().get("search")!=null)
			{
			 this.lastSearch=this.getQueryString().get("search");
			}
			
		}
	
	// ------------------------------------------------------
	// RPC Asynchronous calls section
	// ------------------------------------------------------

	public void loadTerms()
	{

		AgreeOnDefinitionsServiceAsync service1 = GWT.create(AgreeOnDefinitionsService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service1;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "agreeOnDefinitionsService.rpc");

		GwtProject project = new GwtProject();
		project.setId(this.getCurrentState().getProjectID());

		service1.getTerms(project, new AsyncCallback<List<GwtTerm>>()
			{

				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, "Retriving Terms");
				}

				public void onSuccess(List<GwtTerm> result)
				{
					listOfTerms = result;

					initializePane();
				}
			});

	}

	public void addTerm(GwtTerm gwtTerm, GwtProject gwtProject)
	{
		this.showStatusBar("adding...");

		AgreeOnDefinitionsServiceAsync service1 = GWT.create(AgreeOnDefinitionsService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service1;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "agreeOnDefinitionsService.rpc");

		GwtProject project = new GwtProject();
		project.setId(this.getCurrentState().getProjectID());

		service1.addTerm(gwtProject, gwtTerm, new AsyncCallback<GwtTerm>()
			{

				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, "Adding Terms");

				}

				public void onSuccess(GwtTerm result)
				{
					newTerm = result;
					modifyTermId = newTerm.getId();
					loadTerms();

				}
			});

	}

	public void removeTerm(final GwtTerm gwtTerm)
	{

		this.showStatusBar("removing...");
		AgreeOnDefinitionsServiceAsync service1 = GWT.create(AgreeOnDefinitionsService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service1;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "agreeOnDefinitionsService.rpc");

		GwtProject project = new GwtProject();
		project.setId(this.getCurrentState().getProjectID());

		service1.removeTerm(gwtTerm, new AsyncCallback<Void>()
			{

				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, "Retriving Terms");

				}

				@Override
				public void onSuccess(Void result)
				{

					loadTerms();
				}

			});

	}

	public void updateTerm(final GwtTerm gwtTerm, GwtProject gwtProject)
	{

		this.showStatusBar("updating...");

		AgreeOnDefinitionsServiceAsync service1 = GWT.create(AgreeOnDefinitionsService.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) service1;
		endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "agreeOnDefinitionsService.rpc");

		GwtProject project = new GwtProject();
		project.setId(this.getCurrentState().getProjectID());

		service1.updateTerm(gwtProject, gwtTerm, new AsyncCallback<Void>()
			{

				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, "Retriving Terms");
				}

				@Override
				public void onSuccess(Void result)
				{
					modifyTermId = gwtTerm.getId();
					loadTerms();

				}

			});

	}

	public void initializePane()

	{
		this.hideStatusBar();

		this.getContent().add(vPane);
		loadTermsTable();

	}

	public void loadTermsTable()
	{

		filterTerms(lastSearch);

		vPane.clear();
		vPane.setSpacing(0);
		vPane.setWidth("90%");
		vPane.setHeight("5%");

		vPane.add(getHeaderRow());
		vPane.add(vPaneData);
		loadTermTableData();
		addDoneButton();
		
		
		

	}

	public void loadTermTableData()
	{
		vPaneData.clear();
		vPaneData.setWidth("100%");

		vPaneData.setSpacing(5);
		int rowCount = 1;
		for (GwtTerm term : listOfFilteredTerms)
		{
			vPaneData.add(getDataRow(rowCount, term));
			rowCount++;
		}

		
		if (rowCount == 1)
		{

			DisclosurePanel diclosure = new DisclosurePanel();

			Label noRequirement = new Label(messages.noelementsFound());
			noRequirement.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			diclosure.add(noRequirement);
			vPaneData.add(diclosure);
			diclosure.setAnimationEnabled(true);
			diclosure.setOpen(true);

		}
		else
		{
			//loadPaginBar(); TODO if we want to do paging.
		}
		
		

	}

	public void loadPaginBar()
	{
		if(pageCount>1)
		{
			
		HorizontalPanel pagingPanel = new HorizontalPanel();
		
		SquareHyperlink previousPage = new SquareHyperlink("<<");
		SquareHyperlink nextPage = new SquareHyperlink(">>");
		
		if(currentPage>1)
		{
			pagingPanel.add(previousPage);
			pagingPanel.add(new Label("  "));
		}
		for(int i=1;i<=pageCount;i++)
		{
			final SquareHyperlink pageLink = new SquareHyperlink(i+"");
			pageLink.addClickHandler(new ClickHandler(){

			
				public void onClick(ClickEvent event)
				{
					currentPage=Integer.parseInt(pageLink.getText());
					//navigatePaging();
				filterTerms(lastSearch);
				loadTermTableData();
					
				}});
		
			if(currentPage==i)
			{ 
				pagingPanel.add(new Label(" "+i+" "));
			}
			else
			{
				pagingPanel.add(new Label(" "));
				pagingPanel.add(pageLink);
				pagingPanel.add(new Label(" "));
			}
			if(i<pageCount)
			{
				pagingPanel.add(new Label("|"));
			}
			
			
		}
		
		previousPage.addClickHandler(new ClickHandler(){

			
			public void onClick(ClickEvent event)
			{
				
				moveToPreviousPage();
				//navigatePaging();  This will force to use the history manager
			
				filterTerms(lastSearch);
				loadTermTableData();
				
			}});
		
		nextPage.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event)
			{
				moveToNextPage();
				//navigatePaging();
				filterTerms(lastSearch);
				loadTermTableData();
				
			}});
		
		if(currentPage<pageCount)
		{
			pagingPanel.add(new Label("  "));
			pagingPanel.add(nextPage);
		}
		
		pagingPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPaneData.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPaneData.add(pagingPanel);
		}
	}
	
	public void navigatePaging()
	{
		String searchQuery="";
		if(lastSearch.trim().length()!=0)
		{
			searchQuery="&search="+lastSearch;
		}
		
		String queryString ="?PageId="+currentPage+searchQuery;
		History.newItem(AgreeOnDefinitionsPilot.generateNavigationId(AgreeOnDefinitionsPilot.PageId.start)+queryString);
		
	}
	
	public int getIndexById(int termId)
	{
		int count = 0;
		for (GwtTerm t : listOfFilteredTerms)
		{

			if (t.getId().intValue() == termId)
			{
				return count;
			}
			count++;
		}
		return -1;
	}

	public void filterTerms(String searchTerm)
	{
		
		lastSearch=searchTerm;
		listOfFilteredTerms.clear();
		if (searchTerm.trim().length() > 0)
		{
			for (GwtTerm t : listOfTerms)
			{
				if (t.getTerm().trim().toLowerCase().startsWith(searchTerm.toLowerCase().trim()))
				{

					GwtTerm filterTerm = new GwtTerm();
					filterTerm.setTerm(t.getTerm());
					filterTerm.setDefinition(t.getDefinition());
					filterTerm.setId(t.getId());

					listOfFilteredTerms.add(filterTerm);
				}
			}
		}
		else
		{
			for (GwtTerm t : listOfTerms)
			{
				GwtTerm filterTerm = new GwtTerm();

				filterTerm.setTerm(t.getTerm());
				filterTerm.setDefinition(t.getDefinition());
				filterTerm.setId(t.getId());

				listOfFilteredTerms.add(filterTerm);

			}

		}
		Collections.sort(listOfFilteredTerms);
		
		//filterForPagin(currentPage);

	}


	public void filterForPagin(int currentPage)
	{
		
		List<GwtTerm> pageFilterTerms= new ArrayList<GwtTerm>();
		pageCount=  listOfFilteredTerms.size()/pageSize;
		int minTermIndex=((currentPage-1)*pageSize);
		int maxTermIndex=(((currentPage-1)*pageSize)+(pageSize-1));
		
		if((pageCount*pageSize)< listOfFilteredTerms.size())
		{
		
			pageCount=pageCount+1;
			
		}
		
		if(currentPage==pageCount)
		{
			int extraTerms =listOfFilteredTerms.size()-(pageCount*pageSize);
			maxTermIndex=maxTermIndex+extraTerms;
		}
		
		if(currentPage>pageCount)
		{
			currentPage=1;
		}
		
		
		
		
		for(int i=0; i<listOfFilteredTerms.size();i++ )
		{
			if(i>= minTermIndex && i<=maxTermIndex)
			{
				GwtTerm term= new GwtTerm();
				term.setTerm(listOfFilteredTerms.get(i).getTerm());
				term.setDefinition(listOfFilteredTerms.get(i).getDefinition());
				term.setId(listOfFilteredTerms.get(i).getId());
				pageFilterTerms.add(term);
				
			}
			
		}
		
		listOfFilteredTerms.clear();
		listOfFilteredTerms=pageFilterTerms;
	
		
	}
	
	public void moveToNextPage()
	{
		if(currentPage<pageCount)
		{
			currentPage++;
		}
		
	}
	
	public void moveToPreviousPage()
	{
		if(currentPage>1)
		{
			currentPage--;
		}
		
	}
	
	public Widget getHeaderRow()
	{

		FlexTable searchTable = new FlexTable();
		searchTable.setWidth("100%");
		searchTable.setCellSpacing(0);
		HorizontalPanel searchBox = new HorizontalPanel();
		searchBox.setSpacing(0);
		Button clearButton = new Button("X");
		// clearButton.setStyleName("square-clear-label");
		clearButton.setSize("25px", "26px");

	
		final SquareWaterMarkTextBox searchTextBox = new SquareWaterMarkTextBox(messages.search());
		
		if(lastSearch.trim().length()!=0)
		{
			searchTextBox.setText(lastSearch);
			searchTextBox.setStyleName("textInput");
		}
		// searchTextBox.setHeight("25px");

		searchBox.add(searchTextBox);
		searchBox.add(clearButton);
		searchBox.setStyleName("square-agree-on-definition-inner-table");

		searchTextBox.addKeyUpHandler(new KeyUpHandler()
			{

				public void onKeyUp(KeyUpEvent event)
				{   currentPage=1;
					filterTerms(searchTextBox.getText());
					loadTermTableData();
					searchTextBox.setFocus(true);
					searchTextBox.setSelectionRange(searchTextBox.getText().length(), 0);

				}
			});
		
		clearButton.addClickHandler(new ClickHandler()
			{

				@Override
				public void onClick(ClickEvent event)
				{
					currentPage=1;
					searchTextBox.setText("");
					filterTerms(searchTextBox.getText());
					loadTermTableData();
					searchTextBox.setFocus(true);
					searchTextBox.setSelectionRange(searchTextBox.getText().length(), 0);

				}
			});

		Button addTermButton = new Button("Add Term");
		final AgreeOnDefinitionsPane agreeOnDefinitionsObject = this;
		addTermButton.addClickHandler(new ClickHandler()
			{

				@Override
				public void onClick(ClickEvent event)
				{
					createTermDialog = new CreateTermDialog(listOfTerms, agreeOnDefinitionsObject);
					createTermDialog.center();
					createTermDialog.setModal(true);
					createTermDialog.show();

				}
			});

		if (this.getCurrentState().getMode().equals(GwtModesType.ReadWrite))
		{
			searchTable.setWidget(0, 0, addTermButton);
		}
		else
		{
			searchTable.setWidget(0, 0, new Label(messages.termsAndDefinition()));
		}
		searchTable.setWidget(0, 1, new Label(" "));
		searchTable.setWidget(0, 2, searchBox);

		searchTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		searchTable.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_RIGHT);

		searchTable.getCellFormatter().setStyleName(0, 0, "square-Categorize-TableHeader");
		searchTable.getCellFormatter().setStyleName(0, 1, "square-Categorize-TableHeader");
		searchTable.getCellFormatter().setStyleName(0, 2, "square-Categorize-TableHeader");

		return searchTable;
	}

	public Widget getDataRow(int rowCount, GwtTerm term)
	{
		FlexTable rowTable = new FlexTable();
		rowTable.setStyleName("square-agree-on-definition-flex");
		rowTable.setCellSpacing(3);
		rowTable.setSize("100%", "50px");

		Label termLabel = new Label(term.getTerm());
		Label definitionLabel = new Label(term.getDefinition());
		final SummaryElementHyperLinkElement hyper1 = new SummaryElementHyperLinkElement(term.getId(), "Change");
		final SummaryElementHyperLinkElement hyper2 = new SummaryElementHyperLinkElement(term.getId(), "Remove");

		hyper2.addClickHandler(new ClickHandler()
			{

				@Override
				public void onClick(ClickEvent event)
				{
					boolean response = Window.confirm(messages.confirmDelete() + "?");
					if (response)
					{
						GwtTerm term = new GwtTerm();
						term.setId(hyper2.getTermId());
						removeTerm(term);
					}
				}
			});

		if (this.getCurrentState().getMode().equals(GwtModesType.ReadWrite))
		{
			HorizontalPanel links = new HorizontalPanel();
			links.setStyleName("flex-link-bar");
			links.add(hyper1);
			// links.add(new Label(" "));
			links.add(hyper2);
			rowTable.setWidget(0, 2, links);
		}
		else
		{
			rowTable.setWidget(0, 2, new Label(" "));
		}

		final AgreeOnDefinitionsPane agreeOnDefinitionsObject = this;
		hyper1.addClickHandler(new ClickHandler()
			{

				@Override
				public void onClick(ClickEvent event)
				{

					EditTermDialog editTermDialog = new EditTermDialog(getTermById(hyper1.requirementID), listOfTerms, agreeOnDefinitionsObject);
					editTermDialog.center();
					editTermDialog.setModal(true);
					editTermDialog.show();

				}
			});

		int addedItemIndex = getIndexById(modifyTermId);

		if (addedItemIndex != -1 && (addedItemIndex + 1) == rowCount)
		{
			yellowFadeHandler.add(rowTable);
			modifyTermId = -1;

		}

		rowTable.setWidget(0, 0, termLabel);
		rowTable.setWidget(0, 1, definitionLabel);

		rowTable.getCellFormatter().setWidth(0, 0, "20%");
		rowTable.getCellFormatter().setWidth(0, 1, "70%");
		rowTable.getCellFormatter().setWidth(0, 2, "15%");
		
		rowTable.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER);
		rowTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);

		rowTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		rowTable.getCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_LEFT);
		rowTable.getCellFormatter().setHorizontalAlignment(0, 2, HasHorizontalAlignment.ALIGN_CENTER);

		rowTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		rowTable.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
		rowTable.getCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_TOP);

		return rowTable;

	}

	public GwtTerm getTermById(int id)
	{
		for (GwtTerm t : listOfTerms)
		{
			if (t.getId().intValue() == id)
			{
				return t;
			}
		}
		return null;
	}


	// This commands are triggered from the dialog boxes
	public void createCommand(GwtTerm term)
	{
		GwtProject gwtProject = new GwtProject();
		gwtProject.setId(projectID);
		addTerm(term, gwtProject);

	}

	public void updateCommand(GwtTerm term)
	{

		GwtProject gwtProject = new GwtProject();
		gwtProject.setId(projectID);
		updateTerm(term, gwtProject);;
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
		buttonPanel.setWidth("100%");
		buttonPanel.getCellFormatter().setHorizontalAlignment(1,0,HasHorizontalAlignment.ALIGN_RIGHT);
		this.vPane.add(buttonPanel);
	}

	class SummaryElementHyperLinkElement extends SquareHyperlink
	{

		private int requirementID;
		public SummaryElementHyperLinkElement(int requirementID, String text)
			{
				super(text);
				this.requirementID = requirementID;
			}

		public void setRequirementID(int requirementID)
		{
			this.requirementID = requirementID;
		}

		public int getTermId()
		{
			return requirementID;
		}

	}

}
