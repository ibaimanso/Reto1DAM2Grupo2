package firebase;

import java.util.List;

import firebase.exceptions.DBException;

public interface ManagerInterface<T> {
	public List<T> selectAll() throws DBException;
	public T selectById(int id) throws DBException;
	public void insert(T t) throws DBException;
	public void update(T t) throws DBException;
	public void delete(T t) throws DBException;
}
