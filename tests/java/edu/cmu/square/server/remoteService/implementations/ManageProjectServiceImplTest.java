/**
 * 
 */
package edu.cmu.square.server.remoteService.implementations;

import javax.annotation.Resource;

import org.junit.Test;

import edu.cmu.square.client.remoteService.interfaces.ManageProjectService;
import edu.cmu.square.server.base.AbstractSpringBase;

/**
 * @author kaalpurush
 * 
 */
public class ManageProjectServiceImplTest extends AbstractSpringBase {
	@SuppressWarnings("unused")
	@Resource
	private ManageProjectService manageProjectService;

	/**
	 * Test method for
	 * {@link edu.cmu.square.server.remoteService.implementations.ManageProjectServiceImpl#removeUserFromProject(edu.cmu.square.client.model.GwtProject, edu.cmu.square.client.model.GwtUser)}
	 * .
	 */
	@Test
	public void testRemoveUserFromProject() {
//		GwtProject gwtProject = new GwtProject();
//		gwtProject.setId(38);
//		GwtUser gwtUser = new GwtUser();
//		gwtUser.setUserId(1);
//		try {
//			manageProjectService.removeUserFromProject(gwtProject, gwtUser);
//		} catch (SerializationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			fail("error: " + e.getMessage());
//		}
	}

	@Test
	public void testGetUserList() {
//		Map<String, Object> map = super.createUserProjectforRole();
//		try {
//			GwtUser gwtUser = ((User) map.get("user")).createGwtUser();
//			GwtProject gwtProject = ((Project) map.get("project"))
//					.createGwtProject();
//			GwtRole gwtRole = ((Role) map.get("role")).createGwtRole();
//			manageProjectService.addUserToProject(gwtProject, gwtUser, gwtRole);
//			// Project project = new Project();
//			// project.setId(gwtProject.getId());
//			List<GwtUser> userList = manageProjectService.getUserList(gwtProject);
//			assertTrue(userList.size() == 1);
//		} catch (Exception e) {
//			fail("error " + e.getMessage());
//		}
	}

}
