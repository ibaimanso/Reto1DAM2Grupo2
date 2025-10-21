package xml;

import java.util.List;

import xml.exceptions.XMLException;

public interface ManagerInterfaceXML<T> {
	public List<T> selectAll() throws XMLException;
	public void insert(T t) throws XMLException;
	public void update(T t) throws XMLException;
	public void delete(T t) throws XMLException;
}
