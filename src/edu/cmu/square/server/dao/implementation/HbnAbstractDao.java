package edu.cmu.square.server.dao.implementation;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Element;
import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import edu.cmu.square.server.dao.interfaces.AbstractDao;

@SuppressWarnings("unchecked")
public abstract class HbnAbstractDao<T, ID extends Serializable> implements
		AbstractDao<T, ID> {
	protected Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass()
			.getGenericSuperclass()).getActualTypeArguments()[0];
	@Resource
	protected SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cmu.square.server.dao.implementation.AbstractDao#create(T)
	 */
	public void create(T object) {
		if (!persistentClass.isInstance(object))
			throw new IllegalArgumentException(
					"Object class does not match dao type.");
		handleCreatedModifiedDate(object);
		getSession().save(object);
	}

	private void handleCreatedModifiedDate(T object) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(persistentClass);
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();

			for (PropertyDescriptor p : propertyDescriptors) {
				if (p.getName().equals("dateCreated")||p.getName().equals("dateModified")) {
					if (p.getReadMethod().invoke(object)==null) {
						p.getWriteMethod().invoke(object, new Date());
					}
				}
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cmu.square.server.dao.implementation.AbstractDao#fetch(ID)
	 */
	public T fetch(ID id) {
		return (T) getSession().get(persistentClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cmu.square.server.dao.implementation.AbstractDao#fetchAll()
	 */
	public List<T> fetchAll() {
		return getSession().createCriteria(persistentClass)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cmu.square.server.dao.implementation.AbstractDao#update(T)
	 */
	public void update(T object) {
		if (!persistentClass.isInstance(object))
			throw new IllegalArgumentException(
					"Object class does not match dao type.");
		handleCreatedModifiedDate(object);
		getSession().merge(object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cmu.square.server.dao.implementation.AbstractDao#deleteById(ID)
	 */
	public boolean deleteById(ID id) {
		if (id != null) {
			Object entity = getSession().get(persistentClass, id);
			if (entity != null) {
				getSession().delete(entity);
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cmu.square.server.dao.implementation.AbstractDao#deleteEntity(T)
	 */
	public boolean deleteEntity(T entity) {
		if (!persistentClass.isInstance(entity))
			throw new IllegalArgumentException(
					"Object class does not match dao type.");
		if (entity != null) {
			getSession().delete(entity);
			return true;
		}

		return false;
	}

	
	public List<Element> exportToXML(Integer projectId)
	{
		Session xmlSession = getSession().getSession(EntityMode.DOM4J);
		Criteria q = xmlSession.createCriteria(persistentClass);
		q.add(Restrictions.eq("project.id", projectId));
		return q.list();

	}
	
	public void addElements(Element root, String elementName, Integer projectId)
	{
		List<Element> elementsFromDb = exportToXML(projectId);
		Element elementGrouping = root.addElement(elementName);
		for (Element a: elementsFromDb)
		{
			elementGrouping.add(a);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.cmu.square.server.dao.implementation.AbstractDao#getSession()
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
