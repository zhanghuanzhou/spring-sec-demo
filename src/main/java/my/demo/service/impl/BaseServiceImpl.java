package my.demo.service.impl;



import my.demo.dao.BaseDAO;
import my.demo.service.BaseService;
import my.demo.utils.page.ListInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional
public class BaseServiceImpl<T, ID extends Serializable> implements BaseService<T, ID> {

	@Autowired
	private BaseDAO<T, ID> baseDAO;
	
	public Integer countAll() {
		return baseDAO.countAll();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T searchById(ID id) {
		return baseDAO.getById(id);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public int countByMap(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> inMap, Map<String, Object> startMap,
			Map<String, Object> endMap, String[] fetchNames) {
		return baseDAO.countByMap(equalMap, notEqualMap, likeMap, inMap,
				startMap, endMap, fetchNames);
	}

	public void remove(T entity) {
		baseDAO.delete(entity);
	}

	public void removeById(ID id) {
		baseDAO.deleteById(id);
	}

	public int removeByIds(ID[] ids) {
		return baseDAO.deleteByIds(ids);
	}

	public int createOrEditEntities(T[] entities) {
		return baseDAO.insertOrUpdateEntities(entities);
	}

	public ListInfo<T> searchByMapAlias(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> inMap, Map<String, Object> startMap,
			Map<String, Object> endMap, String[] fetchNames, String orderName,
			boolean isDesc, int currentPageNO, int pageSize,
			Map<String, String> aliasMap) {
		ListInfo<T> listInfo = new ListInfo<T>(currentPageNO, pageSize);
		listInfo.setCurrentList(baseDAO.findByMapAlias(equalMap, notEqualMap,
				likeMap, inMap, startMap, endMap, fetchNames, orderName,
				isDesc, listInfo.getFirst(), pageSize, aliasMap));
		listInfo.setSizeOfTotalList(baseDAO.countByMapAlias(equalMap,
				notEqualMap, likeMap, inMap, startMap, endMap, fetchNames,
				aliasMap));
		return listInfo;
	}

	public ListInfo<T> searchByMapAlias(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> orMap, Map<String, Object[]> inMap,
			Map<String, Object> startMap, Map<String, Object> endMap,
			String[] fetchNames, String orderName, boolean isDesc,
			int currentPageNO, int pageSize, Map<String, String> aliasMap) {
		ListInfo<T> listInfo = new ListInfo<T>(currentPageNO, pageSize);
		listInfo.setCurrentList(baseDAO.findByMapAlias(equalMap, notEqualMap,
				likeMap, orMap, inMap, startMap, endMap, fetchNames, orderName,
				isDesc, listInfo.getFirst(), pageSize, aliasMap));
		listInfo.setSizeOfTotalList(baseDAO.countByMapAlias(equalMap,
				notEqualMap, likeMap, orMap, inMap, startMap, endMap,
				fetchNames, aliasMap));
		return listInfo;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<T> getByIds(ID[] ids) {
		return baseDAO.findByIds(ids);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public T searchById(ID id, String[] fetchNames) {
		return baseDAO.getById(id, fetchNames);
	}

	public List<T> getAll() {
		return baseDAO.findAll();
	}

	public void save(T entity) {
		baseDAO.save(entity);		
	}

	public void saveOrUpdate(T entity) {
		this.baseDAO.saveOrUpdate(entity);
	}

	public ListInfo<T> searchByMap(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> inMap, Map<String, Object> startMap,
			Map<String, Object> endMap, String[] fetchNames, String orderName,
			boolean isDesc, int currentPageNO, int pageSize) {
		ListInfo<T> listInfo = new ListInfo<T>(currentPageNO, pageSize);
		listInfo.setCurrentList(baseDAO.findByMap(equalMap, notEqualMap,
				likeMap, inMap, startMap, endMap, fetchNames, orderName,
				isDesc, listInfo.getFirst(), pageSize));
		listInfo.setSizeOfTotalList(baseDAO.countByMap(equalMap, notEqualMap,
				likeMap, inMap, startMap, endMap, fetchNames));
		return listInfo;
	}
}
