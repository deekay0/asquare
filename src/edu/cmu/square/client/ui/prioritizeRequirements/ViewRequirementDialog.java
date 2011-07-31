package edu.cmu.square.client.ui.prioritizeRequirements;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.cmu.square.client.model.GwtArtifact;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.model.GwtRisk;
import edu.cmu.square.client.model.GwtSubGoal;

public class ViewRequirementDialog extends DialogBox
{
	final PrioritizePaneMessages paneMessages = (PrioritizePaneMessages) GWT.create(PrioritizePaneMessages.class);
	public ViewRequirementDialog(GwtRequirement requirement, String categoriesString)
		{
			
			this.setTitle(paneMessages.reviewRequirements());
			this.setText(paneMessages.reviewRequirements());
			this.setAnimationEnabled(true);
			this.center();
			
			VerticalPanel mainPanel = new VerticalPanel();
			mainPanel.setWidth("500px");
			mainPanel.setSpacing(5);
			
			VerticalPanel titleAndCategories = new VerticalPanel();
			
			Label titleLabel = new Label(requirement.getTitle());
			titleLabel.addStyleName("square-title");
			Label categoriesLabel = new Label(categoriesString);
			
			titleAndCategories.add(titleLabel);
			titleAndCategories.add(categoriesLabel);
			
			Label descriptionLabel = new Label(requirement.getDescription());
			
			VerticalPanel risksPanel = new VerticalPanel();
			
			Label risksLabel = new Label(paneMessages.risksAddressed());
			risksLabel.addStyleName("page-title-heading");
			HTML risks = new HTML(createHtmlList(requirement.getRisks()));
			risksPanel.add(risksLabel);
			risksPanel.add(risks);
			
			VerticalPanel goalsPanel = new VerticalPanel();
			
			Label goalsLabel = new Label(paneMessages.goalsAddressed());
			goalsLabel.addStyleName("page-title-heading");
			HTML goals = new HTML(createHtmlList(requirement.getSubGoals()));
			goalsPanel.add(goalsLabel);
			goalsPanel.add(goals);
			
			VerticalPanel artifactsPanel = new VerticalPanel();
			
			Label artifactsLabel = new Label(paneMessages.artifactsReferenced());
			artifactsLabel.addStyleName("page-title-heading");
			HTML artifacts = new HTML(createHtmlList(requirement.getArtifacts()));
			artifactsPanel.add(artifactsLabel);
			artifactsPanel.add(artifacts);
			
			VerticalPanel buttonPanel = new VerticalPanel();
			buttonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			Button okButton = new Button(paneMessages.close());
			okButton.addClickHandler(new CloseButtonHandler(this));
			buttonPanel.add(okButton);
			
			mainPanel.add(titleAndCategories);
			mainPanel.add(descriptionLabel);
			mainPanel.add(risksPanel);
			mainPanel.add(goalsPanel);
			mainPanel.add(artifactsPanel);
			mainPanel.add(buttonPanel);
			
			this.add(mainPanel);
			this.show();
		}
	
	@SuppressWarnings("unchecked")
	private String createHtmlList(List list)
	{
		String formattedList = "<ul>";
		for (Object r: list) {
			formattedList += "<li>";
			if (r instanceof GwtRisk) 
			{
				GwtRisk risk = (GwtRisk)r;
				formattedList += risk.getRiskTitle();
			}
			else if (r instanceof GwtSubGoal) 
			{
				GwtSubGoal subGoal = (GwtSubGoal)r;
				formattedList += subGoal.getDescription();
			}
			else if (r instanceof GwtArtifact) 
			{
				GwtArtifact artifact = (GwtArtifact)r;
				formattedList += artifact.getDescription();
			}
			formattedList += "</li>";
		}
		formattedList += "</ul>";
		return formattedList;
	}

	class CloseButtonHandler implements ClickHandler
	{
		private ViewRequirementDialog dialog;
		public CloseButtonHandler(ViewRequirementDialog viewRequirementDialog)
			{
				this.dialog = viewRequirementDialog;
			}

		@Override
		public void onClick(ClickEvent event)
		{
			this.dialog.hide();

		}

	}
}
