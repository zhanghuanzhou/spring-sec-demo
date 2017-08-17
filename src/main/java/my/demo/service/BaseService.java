package my.demo.service;


import my.demo.utils.page.ListInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface BaseService<T, ID extends Serializable> {

	/**
	 * 查询数据库对应的记录数
	 * 
	 * @return 记录数
	 */
	Integer countAll();
	
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
	 * 根据主键板回相在的实体对像
	 * 
	 * @param id
	 * @return 一个对像实体
	 */
	T searchById(ID id);
	
	/**
	 * 从数据库移除实体
	 * 
	 * @param entity
	 */
	void remove(T entity);

	/**
	 * 根据主键 移除相应的实体
	 * 
	 * @param id
	 */
	void removeById(ID id);

	/**
	 * 批量删除实体
	 * 
	 * @param ids
	 *            实体主键数组
	 * @return
	 */
	int removeByIds(ID[] ids);
	
	/**
	 * 批量保存实体，如果传入的对象在数据库中有就做update操作，如果没有就做save操作
	 * 
	 * @param entities
	 * @return
	 */
	int createOrEditEntities(T[] entities);
	
	ListInfo<T> searchByMapAlias(Map<String, Object> equalMap,
								 Map<String, Object> notEqualMap, Map<String, Object> likeMap,
								 Map<String, Object[]> inMap, Map<String, Object> startMap,
								 Map<String, Object> endMap, String[] fetchNames, String orderName,
								 boolean isDesc, int currentPageNO, int pageSize,
								 Map<String, String> aliasMap);

	ListInfo<T> searchByMapAlias(Map<String, Object> equalMap,
                                 Map<String, Object> notEqualMap, Map<String, Object> likeMap,
                                 Map<String, Object[]> orMap, Map<String, Object[]> inMap,
                                 Map<String, Object> startMap, Map<String, Object> endMap,
                                 String[] fetchNames, String orderName, boolean isDesc,
                                 int currentPageNO, int pageSize, Map<String, String> aliasMap);

	List<T> getByIds(ID[] ids);

	T searchById(ID id, String[] fetchNames);
	
	/**
	 * @return 返回所有对像集合
	 */
	List<T> getAll();
	
	/**
	 * 实例持久化操作，对象在数据库做save操作
	 * @param entity
	 */
	void save(T entity);
	
	/**
	 * 实例持外化操作，如果传入的对象在数据库中有就做update操作，如果没有就做save操作
	 * @param entity
	 */
	void saveOrUpdate(T entity);
	
	/**
	 * 根据条件查询实体 （分页参数）
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
	 * @return 对像集合
	 */
	ListInfo<T> searchByMap(Map<String, Object> equalMap,
                            Map<String, Object> notEqualMap, Map<String, Object> likeMap,
                            Map<String, Object[]> inMap, Map<String, Object> startMap,
                            Map<String, Object> endMap, String[] fetchNames, String orderName,
                            boolean isDesc, int start, int limit);

}
