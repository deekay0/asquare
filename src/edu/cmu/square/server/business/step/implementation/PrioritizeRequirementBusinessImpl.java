package edu.cmu.square.server.business.step.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.math.util.MathUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import edu.cmu.square.client.exceptions.ExceptionType;
import edu.cmu.square.client.exceptions.SquareException;
import edu.cmu.square.client.model.GwtPrioritization;
import edu.cmu.square.client.model.GwtPrioritizationComparator;
import edu.cmu.square.client.model.GwtPrioritizationStatus;
import edu.cmu.square.client.model.GwtPrioritizedRequirement;
import edu.cmu.square.client.model.GwtStepVerficationResult;
import edu.cmu.square.client.model.GwtUser;
import edu.cmu.square.client.model.ProjectRole;
import edu.cmu.square.client.navigation.StepEnum;
import edu.cmu.square.server.authorization.AllowedRoles;
import edu.cmu.square.server.authorization.Roles;
import edu.cmu.square.server.business.implementation.BaseBusinessImpl;
import edu.cmu.square.server.business.implementation.CombinationGenerator;
import edu.cmu.square.server.business.step.interfaces.PrioritizeRequirementBusiness;
import edu.cmu.square.server.dao.interfaces.ProjectDao;
import edu.cmu.square.server.dao.interfaces.RequirementDao;
import edu.cmu.square.server.dao.interfaces.UserAhpDao;
import edu.cmu.square.server.dao.interfaces.UserDao;
import edu.cmu.square.server.dao.model.Project;
import edu.cmu.square.server.dao.model.Requirement;
import edu.cmu.square.server.dao.model.User;
import edu.cmu.square.server.dao.model.UserAhp;
/**
 * Implementation of PrioritizeRequirementsBusiness.
 */
@Service
public class PrioritizeRequirementBusinessImpl extends BaseBusinessImpl implements PrioritizeRequirementBusiness
{
	
	private static Logger logger = Logger.getLogger(PrioritizeRequirementBusinessImpl.class);
	@Resource
	private UserAhpDao userAhpDao;
	@Resource
	private RequirementDao requirementDao;
	@Resource
	private ProjectDao projectDao;
	@Resource
	private UserDao userDao;

	private void createAdditionalComparisons(GwtUser user, Integer projectId) throws SquareException
	{
		// create combinations
		// get all the requirements of the project
		Project project = projectDao.fetch(projectId);
		Set<Requirement> requirements = project.getRequirements();
		List<Requirement> requirementList = new ArrayList<Requirement>(requirements);

		
		List<UserAhp> userAhpList = userAhpDao.getAllComparisons(user.getUserId(), projectId);
		
		combinator(requirementList, userAhpList, user);
		
	}
	
	private void normalizeByColumnTotals(int numberOfColumns, int numberOfRows, double[][]ahpMatrix)
	{
		// get the column totals
		
		List<Double> columnTotals = new ArrayList<Double>(numberOfColumns);
		logger.debug("ahp " + numberOfColumns + " " + numberOfColumns);
		for (int j = 0; j < numberOfColumns; j++)
		{
			double sum = 0;
			for (int k = 0; k < numberOfRows; k++)
			{
				sum += ahpMatrix[k][j];
			}
			logger.debug("adding " + (j) + "th index: " + sum);
			if (sum == 0)
			{
				sum = 1;
			}
			columnTotals.add(sum);
		}

		logger.debug("column totals :" + columnTotals);
		for (int j = 0; j < numberOfColumns; j++)
		{

			for (int k = 0; k < numberOfRows; k++)
			{
				ahpMatrix[k][j] /= columnTotals.get(j);
			}
			logger.debug(j + " column " + Arrays.toString(ahpMatrix[j]));
		}
	}
	
	private void combinator(List<Requirement> requirementList, List<UserAhp> userAhpList, GwtUser user) 
	{
		int[] indices;
		CombinationGenerator combinator = new CombinationGenerator(requirementList.size(), 2);
		while (combinator.hasMore())
		{
			UserAhp userAhp = new UserAhp();
			userAhp.setUser(new User(user, ""));
			userAhp.getId().setUid(user.getUserId());

			indices = combinator.getNext();
			for (int i = 0; i < indices.length; i++)
			{

				Requirement r = requirementList.get(indices[i]);
				r.setPriority(0);
				if (i % 2 == 0)
				{

					userAhp.setRequirementByRidA(r);
					userAhp.getId().setRidA(r.getId());
				}
				if (i % 2 == 1)
				{
					userAhp.setRequirementByRidB(r);
					userAhp.getId().setRidB(r.getId());

				}
			}
			if (userAhpList == null || !userAhpList.contains(userAhp))
			{
				userAhpDao.create(userAhp);
			}
		}
	}
	/**
	 * This method creates a matrix with all the combinations of the requirements.
	 */
	@AllowedRoles(roles = {Roles.All})
	public void createComparisonMatrix(GwtUser user, Integer projectId) throws SquareException
	{
		// create combinations
		// get all the requirements of the project
		Project project = projectDao.fetch(projectId);
		Set<Requirement> requirements = project.getRequirements();
		if (requirements.size() <= 1)
		{
			SquareException se = new SquareException("Too few requirements to compare");
			se.setType(ExceptionType.tooFewToPrioritize);
			throw se;
		}
		List<Requirement> requirementList = new ArrayList<Requirement>(requirements);

		combinator(requirementList, null, user);
	}

	/**
	 * This returns a list of prioritized requirements for a project.
	 * @param projectId
	 * @return
	 * @throws SquareException
	 */
	@AllowedRoles(roles = {Roles.All})
	private List<GwtPrioritizedRequirement> getPrioritizedRequirementsList(Integer projectId) throws SquareException
	{
		List<UserAhp> userAhpList = userAhpDao.getAllComparisons(projectId);
		
		Project project = projectDao.fetch(projectId);
		// FIXME: get the list of requirements that are in the userAhp table
		Set<Requirement> requirements = project.getRequirements();
		
		int totalRequirements = project.getRequirements().size();
		if (totalRequirements <= 1)
		{
			SquareException se = new SquareException();
			se.setType(ExceptionType.tooFewToPrioritize);
			throw se;
		}
		
		if (userAhpList.isEmpty())
		{
			SquareException se = new SquareException();
			se.setType(ExceptionType.noUsersPrioritized);
			throw se;
		}
		return getPrioritizedList(requirements, userAhpList, projectId);
	}

	/**
	 * This returns the prioritized requirements for a particular user.
	 */
	@AllowedRoles(roles = {Roles.All})
	public List<GwtPrioritizedRequirement> getPrioritizedRequirementsForUser(GwtUser user, Integer projectId) throws SquareException
	{
		List<UserAhp> userAhpList = userAhpDao.getAllComparisons(user.getUserId(), projectId);
		Project project = projectDao.fetch(projectId);
		Set<Requirement> requirements = project.getRequirements();
		List<GwtPrioritizedRequirement> individualPriorities = getPrioritizedList(requirements, userAhpList, projectId);
		List<GwtPrioritizedRequirement> groupPriorities = getPrioritizedRequirementsList(projectId);

		for (GwtPrioritizedRequirement indvPriority : individualPriorities)
		{
			for (GwtPrioritizedRequirement groupPriority : groupPriorities)
			{
				if (indvPriority.getRequirement().getId() == groupPriority.getRequirement().getId())
				{
					indvPriority.setGroupRank(groupPriority.getGroupRank());
				}
			}
		}

		return individualPriorities;
	}

	private List<Requirement> getDuplicateRequirements(Set<Requirement> requirements,Integer projectId)
	{
		// remove requirements that cannot be priorities
		List<Requirement> requirementsInAhp = userAhpDao.getRequirementsPrioritized(projectId);
		List<Requirement> removeList = new ArrayList<Requirement>();
		for (Requirement r : requirements)
		{
			if (!requirementsInAhp.contains(r))
			{
				removeList.add(r);
			}
		}
		
		return removeList;
	}
	
	private List<GwtPrioritizedRequirement> sumUpRows
	(int numberOfColumns, int numberOfRows, double[][] ahpMatrix, Map<Integer, GwtPrioritizedRequirement> indexToRequirement )
	{
		List<GwtPrioritizedRequirement> prioritizedRequirements = new ArrayList<GwtPrioritizedRequirement>();
		for (int j = 0; j < numberOfColumns; j++)
		{
			double sum = 0;
			for (int k = 0; k < numberOfRows; k++)
			{
				sum += ahpMatrix[j][k];
			}
			GwtPrioritizedRequirement g = indexToRequirement.get(j);
			g.setAhpPriority(sum);
			prioritizedRequirements.add(g);
		}
		return prioritizedRequirements;
	}
	private double[][] populateAhpMatrix(List<UserAhp> userAhpList, Set<Requirement> requirements, Map<Integer, Integer> requirementToIndex)
	{
		// create a matrix
		double[][] ahpMatrix = new double[requirements.size()][requirements.size()];
		
		// populate the matrix
		for (UserAhp userAhp : userAhpList)
		{
			int indexA = requirementToIndex.get(userAhp.getRequirementByRidA().getId());
			int indexB = requirementToIndex.get(userAhp.getRequirementByRidB().getId());
			// TODO: Assumption the cost and value are non 0

			if (userAhp.getCostA() != 0)
			{
				ahpMatrix[indexA][indexB] += userAhp.getValueA() / userAhp.getCostA();
			}

			if (userAhp.getCostB() != 0)
			{
				ahpMatrix[indexB][indexA] += userAhp.getValueB() / userAhp.getCostB();
			}

		}
		return ahpMatrix;
	}
	/**
	 * This returns a list of prioritized requirements given a set of requirements, comparisons, and project
	 * @param requirements
	 * @param userAhpList
	 * @param projectId
	 * @return
	 */
	// FIXME: Break this up into methods.
	private List<GwtPrioritizedRequirement> getPrioritizedList(Set<Requirement> requirements, List<UserAhp> userAhpList, Integer projectId)
	{
		//remove duplicates
		requirements.removeAll(getDuplicateRequirements(requirements, projectId));

		
		// create a map of requirement to index
		Map<Integer, Integer> requirementToIndex = new Hashtable<Integer, Integer>();
		Map<Integer, GwtPrioritizedRequirement> indexToRequirement = new Hashtable<Integer, GwtPrioritizedRequirement>();
		int i = 0;
		for (Requirement r : requirements)
		{
			requirementToIndex.put(r.getId(), i);
			GwtPrioritizedRequirement gpr = new GwtPrioritizedRequirement();
			gpr.setRequirement(r.createGwtRequirement());
			indexToRequirement.put(i, gpr);
			i++;
		}
		logger.debug("requirementToIndex " + requirementToIndex);
		// create a matrix
		double[][] ahpMatrix = populateAhpMatrix(userAhpList, requirements, requirementToIndex);
		int numberOfColumns = ahpMatrix[0].length;
		int numberOfRows = ahpMatrix.length;
		
		// divide by the column total
		if (requirements.size() > 2)
		{
			normalizeByColumnTotals(numberOfColumns, numberOfRows, ahpMatrix);
		}
		List<GwtPrioritizedRequirement> prioritizedRequirements 
		= sumUpRows(numberOfColumns, numberOfRows, ahpMatrix, indexToRequirement);
		logger.debug("indextToRequirement " + indexToRequirement);
		Collections.sort(prioritizedRequirements, new Comparator<GwtPrioritizedRequirement>()
			{
				@Override
				public int compare(GwtPrioritizedRequirement o1, GwtPrioritizedRequirement o2)
				{
					return o1.getRequirement().getTitle().compareTo(o2.getRequirement().getTitle());
				}
			});
		Collections.sort(prioritizedRequirements);

		int rank = 1;
		for (GwtPrioritizedRequirement gpr : prioritizedRequirements)
		{
			gpr.setGroupRank(rank);
			gpr.setIndividualRank(rank);
			rank++;
		}
		return prioritizedRequirements;

	}
	/**
	 * This returns the prioritization counts and how many are remaining for that particular user.
	 */
	@AllowedRoles(roles = {Roles.All})
	public GwtPrioritizationStatus getPriotizationStatus(GwtUser user, Integer projectId) throws SquareException
	{
		// get the total number of UserAhp
		Integer totalPriorityCounts = userAhpDao.getCountForUser(user.getUserId(), projectId);

		if (totalPriorityCounts == 0)
		{
			createComparisonMatrix(user, projectId);
			totalPriorityCounts = userAhpDao.getCountForUser(user.getUserId(), projectId);
		}
		if (totalPriorityCounts != 0)
		{
			Project project = projectDao.fetch(projectId);
			int totalRequirements = project.getRequirements().size();
			long totalPriorities = MathUtils.binomialCoefficient(totalRequirements, 2);
			if (totalPriorityCounts < totalPriorities)
			{
				createAdditionalComparisons(user, projectId);
				totalPriorityCounts = userAhpDao.getCountForUser(user.getUserId(), projectId);
			}

		}
		// get the currently done UserAhp counts
		Integer comparisonsNotCompleted = userAhpDao.getCompletedCountForUser(user.getUserId(), projectId);
		GwtPrioritizationStatus prioritizationStatus = new GwtPrioritizationStatus();
		prioritizationStatus.setTotalComparisons(totalPriorityCounts);
		prioritizationStatus.setPrioritizationsDone(totalPriorityCounts - comparisonsNotCompleted);
		return prioritizationStatus;

	}

	/**
	 * Returns the list of requirements to prioritize.
	 */
	@AllowedRoles(roles = {Roles.All})
	public List<GwtPrioritization> getRequirementsToCompare(GwtUser user, Integer projectId) throws SquareException
	{
		List<UserAhp> userAhpList = userAhpDao.getAllEmptyComparisons(user.getUserId(), projectId);
		List<GwtPrioritization> priorityList = new ArrayList<GwtPrioritization>();
		for (UserAhp u : userAhpList)
		{
			priorityList.add(u.createGwtPrioritization());
		}
		return priorityList;
	}

	/**
	 * Resets all priorities to 0
	 */
	@AllowedRoles(roles = {Roles.All})
	public void resetPrioritizationStatus(GwtUser user, Integer projectId) throws SquareException
	{
		List<UserAhp> comparisons = userAhpDao.getAllComparisons(user.getUserId(), projectId);

		for (UserAhp userAhp : comparisons)
		{
			userAhpDao.deleteEntity(userAhp);
		}
		createComparisonMatrix(user, projectId);
	}

	@AllowedRoles(roles = {Roles.All})
	public void updateRequirementComparison(GwtPrioritization currentPrioritization, GwtUser user, Integer projectId) throws SquareException
	{
		UserAhp prioritizationToBeUpdated = new UserAhp(currentPrioritization);
		prioritizationToBeUpdated.setUser(new User(user));
		userAhpDao.update(prioritizationToBeUpdated);

	}

	/**
	 * Returns the group prioritizations.
	 */
	@Override
	@AllowedRoles(roles = {Roles.All})
	public List<GwtPrioritizedRequirement> getPrioritizedRequirements(Integer projectId) throws SquareException
	{
		List<GwtPrioritizedRequirement> groupPrioritizations = getPrioritizedRequirementsList(projectId);

		for (GwtPrioritizedRequirement gp : groupPrioritizations)
		{
			if (gp.getRequirement().getPriority() == 0)
			{
				gp.getRequirement().setPriority(gp.getGroupRank());
			}
		}
		Collections.sort(groupPrioritizations, new GwtPrioritizationComparator());
		return groupPrioritizations;
	}

	/**
	 * Returns prioritization for an individual user.
	 * @param user
	 * @param projectId
	 * @return
	 * @throws SquareException
	 */
	@AllowedRoles(roles = {Roles.All})
	public List<GwtPrioritizedRequirement> getPrioritizedRequirementsIndividual(GwtUser user, Integer projectId) throws SquareException
	{
		List<GwtPrioritizedRequirement> groupPrioritizations = getPrioritizedRequirementsList(projectId);
		List<GwtPrioritizedRequirement> individualPrioritizations = getPrioritizedRequirementsForUser(user, projectId);

		for (GwtPrioritizedRequirement gpr : groupPrioritizations)
		{
			for (GwtPrioritizedRequirement indvReq : individualPrioritizations)
			{
				if (indvReq.getRequirement().getId() == gpr.getRequirement().getId())
				{
					gpr.setIndividualRank(indvReq.getGroupRank());
					break;
				}
			}

		}
		return groupPrioritizations;
	}

	
	@Override
	@AllowedRoles(roles = {Roles.All})
	public GwtPrioritizationStatus getUserPrioritizations(Integer projectId) throws SquareException
	{
		Project project = projectDao.fetch(projectId);
		String roleName = ProjectRole.Stakeholder.getLabel();
		int totalUsers = userDao.getCountOfUsersByRole(roleName, project.getId());
		int totalRequirements = project.getRequirements().size();

		if (totalRequirements <= 1)
		{
			SquareException se = new SquareException();
			se.setType(ExceptionType.tooFewToPrioritize);
			throw se;
		}
		long totalPriorities = MathUtils.binomialCoefficient(totalRequirements, 2);
		List<User> completedUsers = userAhpDao.getUsersCompleted(projectId, totalPriorities);
		if (completedUsers.size() <= 0)
		{
			SquareException se = new SquareException();
			se.setType(ExceptionType.noUsersPrioritized);
			throw se;
		}
		GwtPrioritizationStatus prioritizationStatus = new GwtPrioritizationStatus();
		prioritizationStatus.setTotalUsers(totalUsers);
		prioritizationStatus.setCompletedUsers(completedUsers.size());
		return prioritizationStatus;
	}

	@Override
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer, Roles.Requirements_Engineer})
	public void batchUpdatePriorities(List<GwtPrioritizedRequirement> priorities) throws SquareException
	{
		for (GwtPrioritizedRequirement gpr : priorities)
		{
			Requirement r = new Requirement(gpr.getRequirement());
			requirementDao.updatePriority(r);
		}

	}
	@Override
	@AllowedRoles(roles = {Roles.Lead_Requirements_Engineer})
	public void setFinalPriorities(int projectId) throws SquareException
	{
		List<GwtPrioritizedRequirement> priorities = getPrioritizedRequirements(projectId);
		if (priorities.size()<2)
		{
			return;
		}
		for (GwtPrioritizedRequirement gpr : priorities)
		{
			if (gpr.getRequirement().getPriority()!=0)
			{
				return;
			}
		}
		batchUpdatePriorities(priorities);
	}
	
	@Override
	public GwtStepVerficationResult verifyStep(Project project) throws SquareException
	{
		GwtStepVerficationResult result = new GwtStepVerficationResult();

		try
		{
			GwtPrioritizationStatus status = getUserPrioritizations(project.getId());
			int pendingUsers = status.getTotalUsers() - status.getCompletedUsers();
			if (status.getCompletedUsers() == 0)
			{
				result.getMessages().add("No users have prioritized or new requirement(s) got added after prioritization.");
				result.setHasWarning(true);
			}

			else if (pendingUsers != 0)
			{
				result.getMessages().add("There are " + pendingUsers + " pending user(s) to finish their prioritization.");
				result.setHasWarning(true);
			}
		}

		catch (SquareException ex)
		{
		
			if(ex.getType()==ExceptionType.tooFewToPrioritize)
			{
				result.getMessages().add("This project has less than 2 requirements, and therefore no prioritization is necessary.");
				result.setHasWarning(true);
			}
			else if(ex.getType()==ExceptionType.noUsersPrioritized)
			{
				result.getMessages().add("No users have prioritized.");
				result.setHasWarning(true);
			}
			else
			{
				throw ex;
			}
			
		}
		
		return result;
	}
	@Override
	public StepEnum getStepDescription() throws SquareException
	{
		return StepEnum.Prioritize_Requirements;
	}

}
