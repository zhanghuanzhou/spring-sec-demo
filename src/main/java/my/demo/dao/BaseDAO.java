package my.demo.dao;

import org.hibernate.HibernateException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础查询接口
 */
public interface BaseDAO<T, ID extends Serializable> {

    void save(T t);

    void update(T t);
    
    /**
	 * 查询数据库对应的记录数
	 * 
	 * @param criteria
	 *            一个DetachedCriteria对象
	 * @return 记录数
	 */
	int countAll();
	
	/**
	 * 按属性查找对象记录数
	 * 
	 * @param equalMap
	 * @param notEqualMap
	 * @param likeMap
	 * @param inMap
	 * @param startMap
	 * @param endMap
	 * @param fetchNames
	 * @return 记录数
	 */
	int countByMap(Map<String, Object> equalMap,
                   Map<String, Object> notEqualMap, Map<String, Object> likeMap,
                   Map<String, Object[]> inMap, Map<String, Object> startMap,
                   Map<String, Object> endMap, String[] fetchNames);
	
	/**
	 * 根据主键查找对象
	 * 
	 * @param id
	 *            主键
	 * @return 一个实体对象
	 */
	T getById(ID id);
	
	
	/**
	 * 删除相应的实体记录
	 * 
	 * @param entity
	 *            删除一个实体
	 * @throws HibernateException
	 *             抛出异常
	 */
	void delete(T t) throws HibernateException;

	/**
	 * 按主键 删除一个实体
	 * 
	 * @param id
	 */
	void deleteById(ID id);

	/**
	 * 
	 * @param ids
	 * @return 删除记录数目
	 */
	int deleteByIds(ID[] ids);
	
	/**
	 * 按属性查找对象列表, 匹配方式为相等.
	 * 
	 * @param equalMap
	 * @param notEqualMap
	 * @param likeMap
	 * @param inMap
	 * @param startMap
	 * @param endMap
	 * @param fetchNames
	 * @param orderName
	 * @param isDesc
	 * @param start
	 * @param limit
	 * @return
	 */
	List<T> findByMap(Map<String, Object> equalMap,
                      Map<String, Object> notEqualMap, Map<String, Object> likeMap,
                      Map<String, Object[]> inMap, Map<String, Object> startMap,
                      Map<String, Object> endMap, String[] fetchNames, String orderName,
                      boolean isDesc, int start, int limit);
	
	/**
	 * 实例集合持久化操作，如果传入的对象在数据库中有就做update操作，如果没有就做save操作
	 * 
	 * @param entities
	 * @return
	 */
	int insertOrUpdateEntities(T[] entities) throws HibernateException;
	
	List<T> findByMapAlias(Map<String, Object> equalMap,
                           Map<String, Object> notEqualMap, Map<String, Object> likeMap,
                           Map<String, Object[]> inMap, Map<String, Object> startMap,
                           Map<String, Object> endMap, String[] fetchNames, String orderName,
                           boolean isDesc, int start, int limit, Map<String, String> aliasMap);

	int countByMapAlias(Map<String, Object> equalMap,
                        Map<String, Object> notEqualMap, Map<String, Object> likeMap,
                        Map<String, Object[]> inMap, Map<String, Object> startMap,
                        Map<String, Object> endMap, String[] fetchNames,
                        Map<String, String> aliasMap);

	int countByMapAlias(Map<String, Object> equalMap,
                        Map<String, Object> notEqualMap, Map<String, Object> likeMap,
                        Map<String, Object[]> orMap, Map<String, Object[]> inMap,
                        Map<String, Object> startMap, Map<String, Object> endMap,
                        String[] fetchNames, Map<String, String> aliasMap);

	List<T> findByMapAlias(Map<String, Object> equalMap,
                           Map<String, Object> notEqualMap, Map<String, Object> likeMap,
                           Map<String, Object[]> orMap, Map<String, Object[]> inMap,
                           Map<String, Object> startMap, Map<String, Object> endMap,
                           String[] fetchNames, String orderName, boolean isDesc, int start,
                           int limit, Map<String, String> aliasMap);

	List<T> findByIds(ID[] ids);

	T getById(ID id, String[] fetchNames);
	
	/**
	 * 查询数据库所有实体
	 * 
	 * @return 实体对象集合
	 */
	List<T> findAll();
	
	/**
	 * 实例持外化操作，如果传入的对象在数据库中有就做update操作，如果没有就做save操作
	 * 
	 * @param entity
	 */
	void saveOrUpdate(T entity);
	

}
