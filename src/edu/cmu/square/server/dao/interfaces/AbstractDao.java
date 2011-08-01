package edu.cmu.square.server.dao.interfaces;

import java.io.Serializable;
import java.util.List;

import org.dom4j.Element;

public interface AbstractDao<T, ID extends Serializable> {

	/**
	 * Creates the object of type T in the database
	 * @param object The object of type T 
	 */
	public abstract void create(T object);

	public abstract T fetch(ID id);

	public abstract List<T> fetchAll();

	public abstract void update(T object);

	public abstract boolean deleteById(ID id);

	public abstract boolean deleteEntity(T entity);
	
	List<Element> exportToXML(Integer projectId);
	
	void addElements(Element root, String elementName, Integer projectId);

}