package firebase;

import java.util.List;

public interface ManagerInterface<T> {
	public List<T> selectAll();
	public T selectById();
	public void insert(T t);
	public void update(T t);
	public void delete(T t);
}
