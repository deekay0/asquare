package edu.cmu.square.client.ui.inspectRequirements;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.cmu.square.client.model.GwtArtifact;
import edu.cmu.square.client.model.GwtAsset;
import edu.cmu.square.client.model.GwtCategory;
import edu.cmu.square.client.model.GwtRequirement;
import edu.cmu.square.client.model.GwtRisk;
import edu.cmu.square.client.model.GwtSubGoal;
import edu.cmu.square.client.ui.core.SquareHyperlink;

public class Filters
{
	private final InspectRequirementsPaneMessages messages = GWT.create(InspectRequirementsPaneMessages.class);
	private int filterAID = -1; // this is the id for values within each filter
	
	// category of the stack panel
	private String filterAType = null; // stack panel filter
	private String filterBType = null; // drop down filter
	
	private List<GwtRisk> risks;
	private List<GwtAsset> assets;
	private List<GwtSubGoal> subGoals;
	private List<GwtRequirement> requirements;
	private List<GwtArtifact> artifacts;
	private List<GwtCategory> categories;
	private VerticalPanel display;
	
	

	
	public String getFilterBType()
	{
		return filterBType;
	}

	public int getFilterAID()
	{
		return filterAID;
	}
	
	public void setRequirements(List<GwtRequirement> requirements)
	{
		this.requirements = requirements;
	}

	public VerticalPanel setRenderingPanel()
	{
		this.display = new VerticalPanel();
		this.display.setSize("100%", "100%");
		return this.display;

	}

	public void setArtifacts(List<GwtArtifact> artifacts)
	{
		this.artifacts = artifacts;
	}

	public void setCategories(List<GwtCategory> categories)
	{
		this.categories = categories;
	}

	public void setFilterAType(String filterAType, int filterAID)
	{
		this.filterAType = filterAType;
		this.filterAID = filterAID;
		renderAssociationDisplay();
	}

	
	
	
	
	
	public Widget renderHorizontalPanel(List<String> stringTitle, List<String> stringDescription)
	{
		HorizontalPanel tempPanel = new HorizontalPanel();
		tempPanel.setWidth("98%");
		tempPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		tempPanel.setStyleName("square-flex-fullborder");

		final FlexTable inspectionTechniqueTable = new FlexTable();
		inspectionTechniqueTable.setStyleName("square-flex");
		inspectionTechniqueTable.setWidth("100%");
		inspectionTechniqueTable.setCellPadding(10);
		inspectionTechniqueTable.setCellSpacing(0);

		if (stringTitle == null)
		{
			Label noEntry = new Label(messages.selectBothValues());
			noEntry.setStyleName("square-error");
			inspectionTechniqueTable.setWidget(0, 0, noEntry);
			tempPanel.add(noEntry);
			return tempPanel;
		}

		if (stringTitle.size() == 0)
		{
			Label noAssociations = new Label(messages.noAssociations());
			noAssociations.setStyleName("square-error");
			inspectionTechniqueTable.setWidget(0, 0, noAssociations);
			tempPanel.add(noAssociations);
			return tempPanel;
		}

		if (stringDescription == null)
		{
			for (int i = 0; i < stringTitle.size(); i++)
			{
				inspectionTechniqueTable.getCellFormatter().setWidth(i, 0, "100%");
				inspectionTechniqueTable.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_LEFT);
				inspectionTechniqueTable.setWidget(i, 0, new Label(stringTitle.get(i)));
			}
		}
		else
		{
			inspectionTechniqueTable.removeStyleName("square-flex");
			inspectionTechniqueTable.setStyleName("square-flex-no-padding");

			for (int i = 0; i < stringTitle.size(); i++)
			{
				VerticalPanel panelForTitleAndDescription = new VerticalPanel();
				panelForTitleAndDescription.setWidth("100%");
				panelForTitleAndDescription.setStyleName("inner-table");

				final DisclosurePanel descriptionPanel = new DisclosurePanel();
				// panelForTitleAndDescription.setBorderWidth(1);
				descriptionPanel.setContent(new Label(stringDescription.get(i)));
				descriptionPanel.setOpen(false);
				descriptionPanel.setAnimationEnabled(true);

				HorizontalPanel titlePanel = new HorizontalPanel();

				FlexTable titleAndLink = new FlexTable();
				titleAndLink.setWidth("100%");
				titleAndLink.getFlexCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
				titleAndLink.getFlexCellFormatter().setHorizontalAlignment(0, 1, HasHorizontalAlignment.ALIGN_RIGHT);
				titleAndLink.setWidget(0, 0, new Label(stringTitle.get(i)));
				final SquareHyperlink tempLink = new SquareHyperlink(messages.show());
				titleAndLink.setWidget(0, 1, tempLink);

				tempLink.addClickHandler(new ClickHandler()
					{
						public void onClick(ClickEvent event)
						{
							if (tempLink.getText().equalsIgnoreCase(messages.show()))
							{
								tempLink.setText(messages.hide());
								descriptionPanel.setOpen(true);
							}
							else if (tempLink.getText().equalsIgnoreCase(messages.hide()))
							{
								tempLink.setText(messages.show());
								descriptionPanel.setOpen(false);
							}
						}
					});

				titleAndLink.setStyleName("inner-table");

				titlePanel.setWidth("100%");
				titlePanel.setStyleName("inner-table");
				titlePanel.add(titleAndLink);

				panelForTitleAndDescription.setWidth("100%");
				panelForTitleAndDescription.add(titlePanel);
				panelForTitleAndDescription.add(descriptionPanel);

				// panelForTitleAndDescription.add(titlePanel);
				// panelForTitleAndDescription.add(descriptionPanel);

				inspectionTechniqueTable.getCellFormatter().setWidth(i, 0, "100%");
				inspectionTechniqueTable.getCellFormatter().setHorizontalAlignment(i, 0, HasHorizontalAlignment.ALIGN_LEFT);
				inspectionTechniqueTable.setWidget(i, 0, panelForTitleAndDescription);
			}

		}
		tempPanel.add(inspectionTechniqueTable);
		return tempPanel;
	}

	private void renderAssociationDisplay()
	{
		this.display.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		this.display.setSpacing(5);

		this.display.clear();
		if (this.filterAType == null)
		{
			this.display.add(renderHorizontalPanel(null, null));
		}
		else if (this.filterBType == null)
		{
			List<String> stringTitle = new ArrayList<String>();
			stringTitle.add(messages.selectBothValues());
			this.display.add(renderHorizontalPanel(stringTitle, null));
		}
		else if (this.filterBType.equalsIgnoreCase(messages.requirements()))
		{

			List<GwtRequirement> requirements = fetchAssociatedRequirements(this.filterAID, this.filterAType);
			List<String> stringTitle = new ArrayList<String>();
			List<String> stringDescription = new ArrayList<String>();
			if (requirements == null)
			{
				this.display.add(renderHorizontalPanel(null, null));
			}
			else
			{
				for (int j = 0; j < requirements.size(); j++)
				{
					stringTitle.add(requirements.get(j).getTitle());
					stringDescription.add(requirements.get(j).getDescription());
				}
				this.display.add(renderHorizontalPanel(stringTitle, stringDescription));
			}
		}
		else if (this.filterBType.equalsIgnoreCase(messages.assets()))
		{
			List<GwtAsset> assets = fetchAssociatedAssets(this.filterAID, this.filterAType);
			if (assets == null)
			{
				this.display.add(renderHorizontalPanel(null, null));
				return;
			}
			List<String> stringTitle = new ArrayList<String>();
			for (int j = 0; j < assets.size(); j++)
				stringTitle.add(assets.get(j).getDescription());
			this.display.add(renderHorizontalPanel(stringTitle, null));
		}
		else if (this.filterBType.equalsIgnoreCase(messages.risks()))
		{
			List<GwtRisk> risks = fetchAssociatedRisks(this.filterAID, this.filterAType);
			if (risks == null)
			{
				this.display.add(renderHorizontalPanel(null, null));
				return;
			}
			List<String> stringTitle = new ArrayList<String>();
			for (int j = 0; j < risks.size(); j++)
				stringTitle.add(risks.get(j).getRiskTitle());
			this.display.add(renderHorizontalPanel(stringTitle, null));
		}

		else if (this.filterBType.equalsIgnoreCase(messages.goals()))
		{
			List<GwtSubGoal> goals = fetchAssociatedGoals(this.filterAID, this.filterAType);
			if (goals == null)
			{
				this.display.add(renderHorizontalPanel(null, null));
				return;
			}
			List<String> stringTitle = new ArrayList<String>();
			for (int j = 0; j < goals.size(); j++)
				stringTitle.add(goals.get(j).getDescription());
			this.display.add(renderHorizontalPanel(stringTitle, null));
		}
		else if (this.filterBType.equalsIgnoreCase(messages.artifacts()))
		{
			List<GwtArtifact> artifacts = fetchAssociatedArtifacts(this.filterAID, this.filterAType);
			if (artifacts == null)
			{
				this.display.add(renderHorizontalPanel(null, null));
				return;
			}
			List<String> stringTitle = new ArrayList<String>();
			for (int j = 0; j < artifacts.size(); j++)
				stringTitle.add(artifacts.get(j).getDescription());
			this.display.add(renderHorizontalPanel(stringTitle, null));
		}
		else if (this.filterBType.equalsIgnoreCase(messages.categories()))
		{
			List<GwtCategory> categories = fetchAssociatedCategories(this.filterAID, this.filterAType);
			if (categories == null)
			{
				this.display.add(renderHorizontalPanel(null, null));
				return;
			}
			List<String> stringTitle = new ArrayList<String>();
			for (int j = 0; j < categories.size(); j++)
				stringTitle.add(categories.get(j).getCategoryName());
			this.display.add(renderHorizontalPanel(stringTitle, null));
		}
	}

	@SuppressWarnings("unchecked")
	private List<GwtCategory> fetchAssociatedCategories(int filterAID2, String filterAType2)
	{
		if (filterAType2 == null)
		{
			return null;
		}
		else if (filterAType2.equalsIgnoreCase(messages.categories()))
		{
			return null;
		}
		else if (filterAType2.equalsIgnoreCase(messages.risks()))
		{
			return getCategoriesForRisk(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.requirements()))
		{
			return getArtifactsOrCategoriesForRequirements(filterAID2, "Categories");
		}
		else if (filterAType2.equalsIgnoreCase(messages.artifacts()))
		{
			return getCategoriesForArtifact(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.goals()))
		{
			return getCategoriesForGoals(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.assets()))
		{
			return getCategoriesForAssets(filterAID2);
		}
		return null;
	}

	private List<GwtCategory> getCategoriesForAssets(int id)
	{
		List<GwtRequirement> requirementList = getRequirementsForAssets(id);
		return getCategoriesForListOfRequirements(requirementList);
	}

	private List<GwtCategory> getCategoriesForGoals(int id)
	{
		List<GwtRequirement> requirementList = getRequirementsForGoals(id);
		return getCategoriesForListOfRequirements(requirementList);
	}

	private List<GwtCategory> getCategoriesForArtifact(int id)
	{
		List<GwtRequirement> requirementList = getRequirementsForArtifacts(id);
		return getCategoriesForListOfRequirements(requirementList);
	}

	@SuppressWarnings("unchecked")
	private List<GwtArtifact> fetchAssociatedArtifacts(int filterAID2, String filterAType2)
	{
		if (filterAType2 == null)
		{
			return null;
		}
		else if (filterAType2.equalsIgnoreCase(messages.artifacts()))
		{
			return null;
		}
		else if (filterAType2.equalsIgnoreCase(messages.risks()))
		{
			return getArtifactsForRisk(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.requirements()))
		{
			return getArtifactsOrCategoriesForRequirements(filterAID2, "Artifacts");
		}
		else if (filterAType2.equalsIgnoreCase(messages.categories()))
		{
			return getArtifactsForCategories(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.goals()))
		{
			return getArtifactsForGoals(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.assets()))
		{
			return getArtifactsForAssets(filterAID2);
		}
		return null;
	}

	private List<GwtCategory> getCategoriesForRisk(int id)
	{
		List<GwtRequirement> requirementList = getRequirementsForRisk(id);
		return getCategoriesForListOfRequirements(requirementList);
	}

	@SuppressWarnings("unchecked")
	private List<GwtCategory> getCategoriesForListOfRequirements(List<GwtRequirement> requirementList)
	{
		List<GwtCategory> categoryList = new ArrayList<GwtCategory>();

		for (int i = 0; i < requirementList.size(); i++)
		{
			List<GwtCategory> categoryTempList = getArtifactsOrCategoriesForRequirements(requirementList.get(i).getId().intValue(), "Categories");
			categoryList = mergeLists(categoryTempList, categoryList);
		}

		ArrayList<GwtCategory> listCategoryNoDuplicate = new ArrayList<GwtCategory>();
		for (int i = 0; i < categoryList.size(); i++)
		{
			GwtCategory tempCategoryItem = categoryList.get(i);
			boolean duplicate = false;
			for (int j = 0; j < listCategoryNoDuplicate.size(); j++)
			{
				if (tempCategoryItem.getId() == listCategoryNoDuplicate.get(j).getId())
				{
					duplicate = true;
					break;
				}
			}
			if (!duplicate)
			{
				listCategoryNoDuplicate.add(tempCategoryItem);
			}
		}
		categoryList = listCategoryNoDuplicate;
		return categoryList;
	}

	private List<GwtArtifact> getArtifactsForAssets(int id)
	{
		List<GwtRequirement> requirementList = getRequirementsForAssets(id);
		return getArtifactsForListOfRequirements(requirementList);
	}

	private List<GwtArtifact> getArtifactsForGoals(int id)
	{
		List<GwtRequirement> requirementList = getRequirementsForGoals(id);
		return getArtifactsForListOfRequirements(requirementList);
	}

	private List<GwtArtifact> getArtifactsForCategories(int id)
	{
		List<GwtRequirement> requirementList = getRequirementsForCategories(id);
		return getArtifactsForListOfRequirements(requirementList);
	}

	@SuppressWarnings("unchecked")
	private List<GwtArtifact> getArtifactsForListOfRequirements(List<GwtRequirement> requirementList)
	{
		List<GwtArtifact> artifactList = new ArrayList<GwtArtifact>();

		for (int i = 0; i < requirementList.size(); i++)
		{
			List<GwtArtifact> artifactTempList = getArtifactsOrCategoriesForRequirements(requirementList.get(i).getId().intValue(), "Artifacts");
			artifactList = mergeLists(artifactTempList, artifactList);
		}

		ArrayList<GwtArtifact> listArtifactNoDuplicate = new ArrayList<GwtArtifact>();
		for (int i = 0; i < artifactList.size(); i++)
		{
			GwtArtifact tempArtifactItem = artifactList.get(i);
			boolean duplicate = false;
			for (int j = 0; j < listArtifactNoDuplicate.size(); j++)
			{
				if (tempArtifactItem.getId() == listArtifactNoDuplicate.get(j).getId())
				{
					duplicate = true;
					break;
				}
			}
			if (!duplicate)
			{
				listArtifactNoDuplicate.add(tempArtifactItem);
			}
		}
		artifactList = listArtifactNoDuplicate;
		return artifactList;
	}

	private List<GwtSubGoal> fetchAssociatedGoals(int filterAID2, String filterAType2)
	{
		if (filterAType2 == null)
		{
			return null;
		}
		else if (filterAType2.equalsIgnoreCase(messages.goals()))
		{
			return null;
		}
		else if (filterAType2.equalsIgnoreCase(messages.assets()))
		{
			return getGoalsForAssets(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.risks()))
		{
			return getGoalsForRisks(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.requirements()))
		{
			return getGoalsForRequirements(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.artifacts()))
		{
			return getGoalsForArtifactsOrCategories(filterAID2, "Artifacts");
		}
		else if (filterAType2.equalsIgnoreCase(messages.categories()))
		{
			return getGoalsForArtifactsOrCategories(filterAID2, "Categories");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private List getArtifactsOrCategoriesForRequirements(int filterAID2, String type)
	{
		GwtRequirement referenceRequirement = null;
		for (int i = 0; i < requirements.size(); i++)
		{
			if (requirements.get(i).getId().intValue() == filterAID2)
			{
				referenceRequirement = requirements.get(i);
			}
		}
		if (type.equalsIgnoreCase(messages.categories()))
		{
			return referenceRequirement.getCategories();
		}
		else if (type.equalsIgnoreCase(messages.artifacts()))
		{
			return referenceRequirement.getArtifacts();
		}
		return null;
	}

	private List<GwtArtifact> getArtifactsForRisk(int id)
	{
		List<GwtRequirement> requirementList = getRequirementsForRisk(id);
		return getArtifactsForListOfRequirements(requirementList);
	}

	@SuppressWarnings("unchecked")
	private List<GwtSubGoal> getGoalsForArtifactsOrCategories(int id, String type)
	{
		List<GwtRequirement> requirementList = null;
		List<GwtSubGoal> goalList = new ArrayList<GwtSubGoal>();

		if (type.equalsIgnoreCase(messages.categories()))
		{
			requirementList = getRequirementsForCategories(id);
		}
		else if (type.equalsIgnoreCase(messages.artifacts()))
		{
			requirementList = getRequirementsForArtifacts(id);
		}

		for (int i = 0; i < requirementList.size(); i++)
		{
			List<GwtSubGoal> goalListTemp = requirementList.get(i).getSubGoals();
			goalList = mergeLists(goalListTemp, goalList);
		}

		ArrayList<GwtSubGoal> listGoalNoDuplicate = new ArrayList<GwtSubGoal>();
		for (int i = 0; i < goalList.size(); i++)
		{
			GwtSubGoal tempGoalItem = goalList.get(i);
			boolean duplicate = false;
			for (int j = 0; j < listGoalNoDuplicate.size(); j++)
			{
				if (tempGoalItem.getId().intValue() == listGoalNoDuplicate.get(j).getId().intValue())
				{
					duplicate = true;
					break;
				}
			}
			if (!duplicate)
			{
				listGoalNoDuplicate.add(tempGoalItem);
			}
		}
		goalList = listGoalNoDuplicate;
		return goalList;
	}

	private List<GwtRequirement> fetchAssociatedRequirements(int filterAID2, String filterAType2)
	{
		if (filterAType2 == null)
		{
			return null;
		}
		else if (filterAType2.equalsIgnoreCase(messages.requirements()))
		{
			return null;
		}
		else if (filterAType2.equalsIgnoreCase(messages.risks()))
		{
			return getRequirementsForRisk(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.artifacts()))
		{
			return getRequirementsForArtifacts(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.categories()))
		{
			return getRequirementsForCategories(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.goals()))
		{
			return getRequirementsForGoals(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.assets()))
		{
			return getRequirementsForAssets(filterAID2);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private List<GwtSubGoal> getGoalsForRisks(int id)
	{
		List<GwtAsset> assetList = getAssetsForRisk(id);
		List<GwtSubGoal> goalList = new ArrayList<GwtSubGoal>();

		for (int j = 0; j < assetList.size(); j++)
		{
			List<GwtSubGoal> goalListTemp = getGoalsForAssets(assetList.get(j).getId().intValue());
			goalList = mergeLists(goalListTemp, goalList);
		}

		ArrayList<GwtSubGoal> listGoalNoDuplicate = new ArrayList<GwtSubGoal>();
		for (int i = 0; i < goalList.size(); i++)
		{
			GwtSubGoal tempGoalItem = goalList.get(i);
			boolean duplicate = false;
			for (int j = 0; j < listGoalNoDuplicate.size(); j++)
			{
				if (tempGoalItem.getId().intValue() == listGoalNoDuplicate.get(j).getId().intValue())
				{
					duplicate = true;
					break;
				}
			}
			if (!duplicate)
			{
				listGoalNoDuplicate.add(tempGoalItem);
			}
		}
		goalList = listGoalNoDuplicate;
		return goalList;
	}

	private List<GwtRisk> fetchAssociatedRisks(int filterAID2, String filterAType2)
	{
		if (filterAType2 == null)
		{
			return null;
		}
		else if (filterAType2.equalsIgnoreCase(messages.risks()))
		{
			return null;
		}
		else if (filterAType2.equalsIgnoreCase(messages.requirements()))
		{
			return getRisksForRequirements(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.artifacts()))
		{
			return getRisksForArtifacts(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.categories()))
		{
			return getRisksForCategories(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.goals()))
		{
			return getRisksForGoals(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.assets()))
		{
			return getRisksForAssets(filterAID2);
		}
		return null;
	}

	private List<GwtAsset> fetchAssociatedAssets(int filterAID2, String filterAType2)
	{
		if (filterAType2 == null)
		{
			return null;
		}
		else if (filterAType2.equalsIgnoreCase(messages.assets()))
		{
			return null;
		}
		else if (filterAType2.equalsIgnoreCase(messages.goals()))
		{
			return getAssetsForGoals(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.risks()))
		{
			return getAssetsForRisk(filterAID2);
		}
		else if (filterAType2.equalsIgnoreCase(messages.artifacts()))
		{
			return getAssetsForCategoriesOrArtifacts(filterAID2, "Artifacts");
		}
		else if (filterAType2.equalsIgnoreCase(messages.categories()))
		{
			return getAssetsForCategoriesOrArtifacts(filterAID2, "Categories");
		}

		else if (filterAType2.equalsIgnoreCase(messages.requirements()))
		{
			return getAssetsForRequirements(filterAID2);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private List<GwtAsset> getAssetsForCategoriesOrArtifacts(int filterAID2, String type)
	{
		List<GwtRequirement> requirementList = null;
		if (type.equalsIgnoreCase(messages.categories()))
		{
			requirementList = getRequirementsForCategories(filterAID2);
		}
		else if (type.equalsIgnoreCase(messages.artifacts()))
		{
			requirementList = getRequirementsForArtifacts(filterAID2);
		}
		List<GwtAsset> assetList = new ArrayList<GwtAsset>();
		for (int j = 0; j < requirementList.size(); j++)
		{
			List<GwtAsset> assetTempList;
			assetTempList = getAssetsForRequirements(requirementList.get(j).getId().intValue());
			assetList = mergeLists(assetTempList, assetList);
		}

		ArrayList<GwtAsset> listAssetNoDuplicate = new ArrayList<GwtAsset>();
		for (int i = 0; i < assetList.size(); i++)
		{
			GwtAsset tempGoalItem = assetList.get(i);
			boolean duplicate = false;
			for (int j = 0; j < listAssetNoDuplicate.size(); j++)
			{
				if (tempGoalItem.getId().intValue() == listAssetNoDuplicate.get(j).getId().intValue())
				{
					duplicate = true;
					break;
				}
			}
			if (!duplicate)
			{
				listAssetNoDuplicate.add(tempGoalItem);
			}
		}
		assetList = listAssetNoDuplicate;
		return assetList;
	}

	private List<GwtAsset> getAssetsForRequirements(int filterAID2)
	{
		List<GwtAsset> assetList = new ArrayList<GwtAsset>();

		List<GwtSubGoal> tempSubGoalList = getGoalsForRequirements(filterAID2);
		List<GwtRisk> tempRiskList = getRisksForRequirements(filterAID2);

		for (int i = 0; i < tempSubGoalList.size(); i++)
		{
			for (int j = 0; j < tempSubGoalList.get(i).getAssets().size(); j++)
			{
				assetList.add(tempSubGoalList.get(i).getAssets().get(j));
			}
		}

		for (int i = 0; i < tempRiskList.size(); i++)
		{
			for (int j = 0; j < tempRiskList.get(i).getAssets().size(); j++)
			{
				assetList.add(tempRiskList.get(i).getAssets().get(j));
			}
		}

		// remove duplicate values in the list;
		List<GwtAsset> listAssetNoDuplicate = new ArrayList<GwtAsset>();
		for (int i = 0; i < assetList.size(); i++)
		{
			GwtAsset tempAssetItem = assetList.get(i);
			boolean duplicate = false;
			for (int j = 0; j < listAssetNoDuplicate.size(); j++)
			{
				if (tempAssetItem.getId().intValue() == listAssetNoDuplicate.get(j).getId().intValue())
				{
					duplicate = true;
					break;
				}
			}
			if (!duplicate)
			{
				listAssetNoDuplicate.add(tempAssetItem);
			}
		}
		assetList = listAssetNoDuplicate;
		return assetList;
	}

	private List<GwtRisk> getRisksForRequirements(int id)
	{
		for (int i = 0; i < requirements.size(); i++)
		{
			if (requirements.get(i).getId().intValue() == id)
			{
				return requirements.get(i).getRisks();
			}
		}
		
		return new ArrayList<GwtRisk>();
	}

	private List<GwtRequirement> getRequirementsForAssets(int filterAID2)
	{
		List<GwtRequirement> requirementsList = new ArrayList<GwtRequirement>();

		for (int i = 0; i < requirements.size(); i++)
		{
			boolean addedRequirement = false;

			GwtRequirement tempRequirement = requirements.get(i);
			for (int j = 0; j < tempRequirement.getSubGoals().size(); j++)
			{
				GwtSubGoal tempSubGoal = tempRequirement.getSubGoals().get(j);
				for (int k = 0; k < tempSubGoal.getAssets().size(); k++)
				{
					if (tempSubGoal.getAssets().get(k).getId().intValue() == filterAID2)
					{
						requirementsList.add(tempRequirement);
						addedRequirement = true;
						break;
					}
				}
				if (addedRequirement)
				{
					break;
				}
			}
			if (!addedRequirement)
			{
				for (int j = 0; j < tempRequirement.getRisks().size(); j++)
				{
					GwtRisk tempRisks = tempRequirement.getRisks().get(j);
					for (int k = 0; k < tempRisks.getAssets().size(); k++)
					{
						if (tempRisks.getAssets().get(k).getId().intValue() == filterAID2)
						{
							requirementsList.add(tempRequirement);
							addedRequirement = true;
							break;
						}
					}
					if (addedRequirement)
					{
						break;
					}
				}
			}
		}
		return requirementsList;
	}

	private List<GwtRequirement> getRequirementsForGoals(int filterAID2)
	{
		List<GwtRequirement> requirementsList = new ArrayList<GwtRequirement>();
		for (int i = 0; i < requirements.size(); i++)
		{
			for (int j = 0; j < requirements.get(i).getSubGoals().size(); j++)
			{
				if (requirements.get(i).getSubGoals().get(j).getId() == filterAID2)
				{
					requirementsList.add(requirements.get(i));
					break;
				}
			}
		}
		return requirementsList;
	}

	private List<GwtRequirement> getRequirementsForArtifacts(int filterAID2)
	{
		List<GwtRequirement> requirementsList = new ArrayList<GwtRequirement>();
		for (int i = 0; i < requirements.size(); i++)
		{
			for (int j = 0; j < requirements.get(i).getArtifacts().size(); j++)
			{
				if (requirements.get(i).getArtifacts().get(j).getId() == filterAID2)
				{
					requirementsList.add(requirements.get(i));
					break;
				}
			}
		}
		return requirementsList;
	}

	private List<GwtRequirement> getRequirementsForCategories(int filterAID2)
	{
		List<GwtRequirement> requirementsList = new ArrayList<GwtRequirement>();
		for (int i = 0; i < requirements.size(); i++)
		{
			for (int j = 0; j < requirements.get(i).getCategories().size(); j++)
			{
				if (requirements.get(i).getCategories().get(j).getId() == filterAID2)
				{
					requirementsList.add(requirements.get(i));
					break;
				}
			}
		}
		return requirementsList;
	}

	@SuppressWarnings("unchecked")
	private List<GwtRisk> getRisksForCategories(int id)
	{
		List<GwtRequirement> requirementsList = getRequirementsForCategories(id);
		List<GwtRisk> risksList = new ArrayList<GwtRisk>();

		for (int i = 0; i < requirementsList.size(); i++)
		{
			List<GwtRisk> risksTempList = requirementsList.get(i).getRisks();
			risksList = mergeLists(risksTempList, risksList);
		}

		ArrayList<GwtRisk> listRiskNoDuplicate = new ArrayList<GwtRisk>();
		for (int i = 0; i < risksList.size(); i++)
		{
			GwtRisk tempRiskItem = risksList.get(i);
			boolean duplicate = false;
			for (int j = 0; j < listRiskNoDuplicate.size(); j++)
			{
				if (tempRiskItem.getId().intValue() == listRiskNoDuplicate.get(j).getId().intValue())
				{
					duplicate = true;
					break;
				}
			}
			if (!duplicate)
			{
				listRiskNoDuplicate.add(tempRiskItem);
			}
		}
		risksList = listRiskNoDuplicate;
		return risksList;
	}

	private List<GwtRisk> getRisksForArtifacts(int filterAID2)
	{
		List<GwtRisk> risksList = new ArrayList<GwtRisk>();
		for (int i = 0; i < risks.size(); i++)
		{
			for (int j = 0; j < risks.get(i).getArtifact().size(); j++)
			{
				if (risks.get(i).getArtifact().get(j).getId() == filterAID2)
				{
					risksList.add(risks.get(i));
					break;
				}
			}
		}
		return risksList;
	}

	@SuppressWarnings("unchecked")
	private List mergeLists(List subGoalsTempRef1, List subGoalsTempRef2)
	{
		for (int i = 0; i < subGoalsTempRef1.size(); i++)
		{
			subGoalsTempRef2.add(subGoalsTempRef1.get(i));
		}
		return subGoalsTempRef2;
	}

	private List<GwtSubGoal> getGoalsForRequirements(int id)
	{
		List<GwtSubGoal> subGoalList = new ArrayList<GwtSubGoal>();

		GwtRequirement tempRequirement = null;
		for (int i = 0; i < requirements.size(); i++)
		{
			if (requirements.get(i).getId().intValue() == id)
			{
				tempRequirement = requirements.get(i);
				break;
			}
		}
		subGoalList = tempRequirement.getSubGoals();
		return subGoalList;
	}

	private List<GwtRequirement> getRequirementsForRisk(int riskID)
	{
		List<GwtRequirement> requirementsList = new ArrayList<GwtRequirement>();
		for (int i = 0; i < requirements.size(); i++)
		{
			for (int j = 0; j < requirements.get(i).getRisks().size(); j++)
			{
				if (requirements.get(i).getRisks().get(j).getId().intValue() == riskID)
				{
					requirementsList.add(requirements.get(i));
					break;
				}
			}
		}
		return requirementsList;
	}

	private List<GwtSubGoal> getGoalsForAssets(int id)
	{
		List<GwtSubGoal> subGoalList = new ArrayList<GwtSubGoal>();

		for (int i = 0; i < subGoals.size(); i++)
		{
			for (int j = 0; j < subGoals.get(i).getAssets().size(); j++)
			{
				if (subGoals.get(i).getAssets().get(j).getId().intValue() == id)
				{
					subGoalList.add(subGoals.get(i));
					break;
				}
			}
		}

		return subGoalList;
	}

	@SuppressWarnings("unchecked")
	private List<GwtRisk> getRisksForGoals(int id)
	{
		List<GwtRequirement> requirementsTempRef = getRequirementsForGoals(id);
		List<GwtAsset> assetsTempRef = getAssetsForGoals(id);
		List<GwtRisk> risksList = new ArrayList<GwtRisk>();

		for (int i = 0; i < requirementsTempRef.size(); i++)
		{
			List<GwtRisk> risksTemp = requirementsTempRef.get(i).getRisks();
			risksList = mergeLists(risksTemp, risksList);
		}

		for (int i = 0; i < assetsTempRef.size(); i++)
		{
			List<GwtRisk> risksTemp = getRisksForAssets(assetsTempRef.get(i).getId().intValue());
			risksList = mergeLists(risksTemp, risksList);
		}

		ArrayList<GwtRisk> listRiskNoDuplicate = new ArrayList<GwtRisk>();
		for (int i = 0; i < risksList.size(); i++)
		{
			GwtRisk tempRiskItem = risksList.get(i);
			boolean duplicate = false;
			for (int j = 0; j < listRiskNoDuplicate.size(); j++)
			{
				if (tempRiskItem.getId().intValue() == listRiskNoDuplicate.get(j).getId().intValue())
				{
					duplicate = true;
					break;
				}
			}
			if (!duplicate)
			{
				listRiskNoDuplicate.add(tempRiskItem);
			}
		}
		risksList = listRiskNoDuplicate;
		return risksList;
	}

	private List<GwtRisk> getRisksForAssets(int id)
	{
		List<GwtRisk> riskList = new ArrayList<GwtRisk>();

		for (int i = 0; i < risks.size(); i++)
		{
			for (int j = 0; j < risks.get(i).getAssets().size(); j++)
			{
				if (risks.get(i).getAssets().get(j).getId().intValue() == id)
				{
					riskList.add(risks.get(i));
					break;
				}
			}
		}
		return riskList;
	}

	
	private List<GwtAsset> getAssetsForRisk(int id)
	{
		List<GwtAsset> assetList = new ArrayList<GwtAsset>();
		GwtRisk tempRisk = null;
		for (int i = 0; i < risks.size(); i++)
		{
			if (risks.get(i).getId().intValue() == id)
			{
				tempRisk = risks.get(i);
				break;
			}
		}
		assetList = tempRisk.getAssets();
		return assetList;
	}

	private List<GwtAsset> getAssetsForGoals(int id)
	{
		List<GwtAsset> assetList = new ArrayList<GwtAsset>();
		GwtSubGoal tempSubGoal = null;
		for (int i = 0; i < subGoals.size(); i++)
		{
			if (subGoals.get(i).getId().intValue() == id)
			{
				tempSubGoal = subGoals.get(i);
				break;
			}
		}
		assetList = tempSubGoal.getAssets();
		return assetList;
	}

	public void setFilterBType(String filterBType)
	{
		this.filterBType = filterBType;
		renderAssociationDisplay();
	}


	public String getFilterAType()
	{
		return filterAType;
	}

	public void setRisks(List<GwtRisk> risks)
	{
		this.risks = risks;
	}

	public void setAssets(List<GwtAsset> assets)
	{
		this.assets = assets;
	}

	public void setSubGoals(List<GwtSubGoal> subGoals)
	{
		this.subGoals = subGoals;
	}

	public List<GwtSubGoal> getSubGoals()
	{
		return subGoals;
	}

	public VerticalPanel getDisplay()
	{
		return display;
	}

	
	
	
}
