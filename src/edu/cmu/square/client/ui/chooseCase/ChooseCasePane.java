package edu.cmu.square.client.ui.chooseCase;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;

import edu.cmu.square.client.exceptions.ExceptionHelper;
import edu.cmu.square.client.model.GwtAsquareCase;
import edu.cmu.square.client.navigation.State;
import edu.cmu.square.client.remoteService.interfaces.ChooseCaseService;
import edu.cmu.square.client.remoteService.interfaces.ChooseCaseServiceAsync;
import edu.cmu.square.client.ui.chooseProject.HomePilot;
import edu.cmu.square.client.ui.core.BasePane;
import edu.cmu.square.client.ui.core.SquareHyperlink;
import edu.cmu.square.client.utils.SquareUtil;

public class ChooseCasePane extends BasePane
{
	private ChooseCasePaneMessages messages = GWT.create(ChooseCasePaneMessages.class);
	private ChooseCaseServiceAsync chooseService = GWT.create(ChooseCaseService.class);

	
	private List<GwtAsquareCase> cases;
	
	
	public ChooseCasePane(final State stateInfo)
		{
			super(stateInfo);
		
			super.cleanProjectContext();
			super.showLoadingStatusBar();
			System.out.println("choose case pane 1.....       .....");
			initializePane();

		}

	public void initializePane()
	{
		System.out.println("choose case pane.....       .....");
		chooseService.getCasesForUser(new AsyncCallback<List<GwtAsquareCase>>()
			{
				@Override
				public void onFailure(Throwable caught)
				{
					ExceptionHelper.SquareRootRPCExceptionHandler(caught, "getting cases fo user");
				}

				@Override
				public void onSuccess(List<GwtAsquareCase> result)
				{
					// TODO Auto-generated method stub
					cases = result;
					setupCasesTable();
				}
			}
		);
	}

	private void setupCasesTable()
	{
		VerticalPanel casePanel = new VerticalPanel();
		casePanel.setSpacing(10);
		casePanel.setWidth("100%");
		casePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		casePanel.add(getCaseListTable());
		
		this.getContent().add(casePanel);
		this.hideStatusBar();
	}

	/**
	 * This returns a Flextable with the list of cases.
	 * @return returns a Flextable with list of cases.
	 */
	private FlexTable getCaseListTable()
	{
		FlexTable casesTable = new FlexTable();
		casesTable.setWidth("450px");
		RowFormatter rowFormatter = casesTable.getRowFormatter();
		rowFormatter.setStyleName(0, "square-choose-case-title");

		FlexCellFormatter cellFormatter = casesTable.getFlexCellFormatter();
		cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		
		Label casesTableHeader = new Label(messages.chooseCases());
		casesTableHeader.setStyleName("page-title-heading");
		casesTableHeader.setText(messages.chooseCases());
		
		casesTable.setWidget(0, 0, casesTableHeader);
		cellFormatter.setColSpan(0, 0, 2);
		cellFormatter.setStyleName(0, 0, "square-choose-step-title");
		
		List<GwtAsquareCase> caseList = cases;
		
		int currentRow = 1;
		for (GwtAsquareCase c : caseList)
		{		
			cellFormatter.setVerticalAlignment(currentRow, 0, HasVerticalAlignment.ALIGN_TOP);
			cellFormatter.setHorizontalAlignment(currentRow, 0, HasHorizontalAlignment.ALIGN_LEFT);
			cellFormatter.setHorizontalAlignment(currentRow, 1, HasHorizontalAlignment.ALIGN_LEFT);
			
			cellFormatter.setStyleName(currentRow, 0, "square-select-case");
			cellFormatter.setStyleName(currentRow, 1, "square-select-case");
				
			cellFormatter.setWidth(currentRow, 0, "180px");
			
			final ChooseCaseLink caseLink = new ChooseCaseLink(c);
		
			
			caseLink.addClickHandler(new ClickHandler()
				{
					@Override
					public void onClick(ClickEvent event)
					{
						System.out.println("onclick event in choose case....."+event.toString()+"...."+
								caseLink.getAsquareCase().getId());
						if(caseLink.getAsquareCase().getId()==2)
							Window.open("https://squaretool.cylab.cmu.edu/Square/", "_blank", "");
						else
						{
							currentState.setCaseID(caseLink.getAsquareCase().getId());  
							History.newItem(HomePilot.generateNavigationId(HomePilot.PageId.home));
						}
					}
				}
			);
			VerticalPanel statusPanel = new VerticalPanel();
				
			List<String> caseInfoList =new ArrayList<String>();
			caseInfoList.add(messages.caseDescription(c.getDescription()));
	
			statusPanel.add(new HTML(SquareUtil.createHTMLList(caseInfoList)));
		
			casesTable.setWidget(currentRow, 0, caseLink);
			casesTable.setWidget(currentRow, 1, statusPanel);
				
			currentRow++;
		}
		
	
		// fill in the flextable rows with project data
		return casesTable;
	}


	class ChooseCaseLink extends SquareHyperlink
	{
		private GwtAsquareCase asquareCase;

		public ChooseCaseLink(GwtAsquareCase asquareCase)
			{
				super(asquareCase.getName());
				this.asquareCase = asquareCase;
			}

		/**
		 * @return the caseId
		 */
		public GwtAsquareCase getAsquareCase()
		{
			return asquareCase;
		}

	}

}
