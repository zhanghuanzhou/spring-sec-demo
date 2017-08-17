package my.demo.dao.impl;


import my.demo.dao.BaseDAO;
import my.demo.utils.GenericsUtil;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BaseDAOImpl<T, ID extends Serializable> implements BaseDAO<T, ID> {
    @Autowired
    protected SessionFactory sessionFactory;
    
    
    private Map<String, String> annotationMap;
    
    protected String entityID;
    
    private Class<T> entityClass;
    
    /**
     * 获取由Spring框架管理的线程安全的Session接口
     *
     * @return 由Spring框架管理的线程安全的Session接口
     */
    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    /**
	 * 获取实例类的类型
	 */
	@SuppressWarnings("unchecked")
	public BaseDAOImpl() {
		this.annotationMap = new HashMap<String, String>();
		this.entityClass = GenericsUtil.getSuperClassGenricType(getClass());
		for (Field field : entityClass.getDeclaredFields()) {
			Annotation id = field.getAnnotation(Id.class);
			Annotation column = field.getAnnotation(Column.class);
			if (null != column) {
				Column col = (Column) column;
				annotationMap.put(field.getName(), col.name());
			}
			if (null != id) {
				entityID = field.getName();
			}
		}
	}


    @Override
    public void save(T t) {
        getSession().save(t);
    }

    @Override
    public void update(T t) {
        getSession().update(t);
    }

	@Override
	public int countAll() {
		Object object = getSession().createCriteria(entityClass)
				.setProjection(Projections.count(getEntityIdName()))
				.uniqueResult();
		return object == null ? 0 : ((Long) object).intValue();
	}
	
	public Class<T> getEntityClass() {
		return this.entityClass;
	}
	
	public String getEntityIdName() {
		String entityIdName = null;
		for (Field field : entityClass.getDeclaredFields()) {
			Annotation id = field.getAnnotation(javax.persistence.Id.class);
			if (null != id) {
				entityIdName = field.getName();
			}
		}
		return entityIdName;
	}

	@Override
	public T getById(ID id) {
		Object object = getSession().get(entityClass, id);
		return object == null ? null : (T) object;
	}

	@Override
	public int countByMap(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> inMap, Map<String, Object> startMap,
			Map<String, Object> endMap, String[] fetchNames) {
		Criteria criteria = getSession().createCriteria(entityClass,
				getEntityClass().getSimpleName().toLowerCase());
		if (fetchNames != null) {
			for (String fetchName : fetchNames) {
				criteria = criteria.setFetchMode(fetchName, FetchMode.JOIN);
			}
		}
		if (equalMap != null) {
			for (String key : equalMap.keySet()) {
				if (equalMap.get(key) != null) {
					criteria = criteria.add(Restrictions.eq(key,
							equalMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNull(key));
				}

			}
		}
		if (notEqualMap != null) {
			for (String key : notEqualMap.keySet()) {
				if (notEqualMap.get(key) != null) {
					criteria = criteria.add(Restrictions.ne(key,
							notEqualMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNotNull(key));
				}
			}
		}
		if (likeMap != null) {
			for (String key : likeMap.keySet()) {
				if (likeMap.get(key) instanceof String) {
					criteria = criteria.add(Restrictions.like(key,
							likeMap.get(key).toString(), MatchMode.ANYWHERE));
				} else if (likeMap.get(key) instanceof Long
						|| likeMap.get(key) instanceof Integer) {
					criteria = criteria.add(Restrictions.sqlRestriction(
							"this_." + annotationMap.get(key) + " like(?)", "%"
									+ likeMap.get(key) + "%",
							StringType.INSTANCE));
				}
			}
		}
		if (inMap != null) {
			for (String key : inMap.keySet()) {
				criteria = criteria.add(Restrictions.in(key, inMap.get(key)));
			}
		}
		if (startMap != null) {
			for (String key : startMap.keySet()) {
				criteria = criteria
						.add(Restrictions.ge(key, startMap.get(key)));
			}
		}
		if (endMap != null) {
			for (String key : endMap.keySet()) {
				criteria = criteria.add(Restrictions.le(key, endMap.get(key)));
			}
		}
		Object object = criteria.setProjection(
				Projections.countDistinct(getEntityIdName())).uniqueResult();
		return object == null ? 0 : ((Long) object).intValue();
	}

	@Override
	public void delete(T t) throws HibernateException {
		getSession().delete(t);
	}

	@Override
	public void deleteById(ID id) {
		StringBuffer sql = new StringBuffer();
		String entityShortClassName = getEntityClass().getSimpleName();
		sql.append("delete from ").append(entityShortClassName).append(" as ")
				.append(entityShortClassName.toLowerCase()).append(" where ")
				.append(entityShortClassName.toLowerCase()).append(".")
				.append(getEntityIdName()).append("=:id");
		getSession().createQuery(sql.toString()).setParameter("id", id)
				.executeUpdate();
	}

	@Override
	public int deleteByIds(ID[] ids) {
		String entityShortClassName = getEntityClass().getSimpleName();
		return getSession()
				.createQuery(
						"delete from " + entityShortClassName + " as "
								+ entityShortClassName.toLowerCase()
								+ " where "
								+ entityShortClassName.toLowerCase() + "."
								+ entityID + " in (:id)")
				.setParameterList("id", ids).executeUpdate();
	}

	@Override
	public List<T> findByMap(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> inMap, Map<String, Object> startMap,
			Map<String, Object> endMap, String[] fetchNames, String orderName,
			boolean isDesc, int start, int limit) {
		Criteria criteria = getSession().createCriteria(entityClass);
		if (fetchNames != null) {
			for (String fetchName : fetchNames) {
				criteria = criteria.setFetchMode(fetchName, FetchMode.JOIN);
			}
		}
		if (equalMap != null) {
			for (String key : equalMap.keySet()) {
				if (equalMap.get(key) != null) {
					criteria = criteria.add(Restrictions.eq(key,
							equalMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNull(key));
				}

			}
		}
		if (notEqualMap != null) {
			for (String key : notEqualMap.keySet()) {
				if (notEqualMap.get(key) != null) {
					criteria = criteria.add(Restrictions.ne(key,
							notEqualMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNotNull(key));
				}
			}
		}
		if (likeMap != null) {
			for (String key : likeMap.keySet()) {
				if (likeMap.get(key) instanceof String) {
					criteria = criteria.add(Restrictions.like(key,
							likeMap.get(key).toString(), MatchMode.ANYWHERE));
				} else if (likeMap.get(key) instanceof Long
						|| likeMap.get(key) instanceof Integer) {
					criteria = criteria.add(Restrictions.sqlRestriction(
							"this_." + annotationMap.get(key) + " like(?)", "%"
									+ likeMap.get(key) + "%",
							StringType.INSTANCE));
				}

			}
		}
		if (inMap != null) {
			for (String key : inMap.keySet()) {
				criteria = criteria.add(Restrictions.in(key, inMap.get(key)));
			}
		}
		if (startMap != null) {
			for (String key : startMap.keySet()) {
				criteria = criteria
						.add(Restrictions.ge(key, startMap.get(key)));
			}
		}
		if (endMap != null) {
			for (String key : endMap.keySet()) {
				criteria = criteria.add(Restrictions.le(key, endMap.get(key)));
			}
		}

		if (orderName != null) {
			if (isDesc) {
				criteria = criteria.addOrder(Order.desc(orderName));
			} else {
				criteria = criteria.addOrder(Order.asc(orderName));
			}
		}
		return (List<T>) criteria.setFirstResult(start).setMaxResults(limit)
				.list();
	}

	@Override
	public int insertOrUpdateEntities(T[] entities) throws HibernateException {
		for (T t : entities) {
			getSession().saveOrUpdate(t);
		}
		return 0;
	}

	@Override
	public List<T> findByMapAlias(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> inMap, Map<String, Object> startMap,
			Map<String, Object> endMap, String[] fetchNames, String orderName,
			boolean isDesc, int start, int limit, Map<String, String> aliasMap) {
		Criteria criteria = getSession().createCriteria(entityClass);
		if (fetchNames != null) {
			for (String fetchName : fetchNames) {
				criteria = criteria.setFetchMode(fetchName, FetchMode.JOIN);
			}
		}
		if (aliasMap != null) {
			for (String key : aliasMap.keySet()) {
				if (aliasMap.get(key) != null) {
					criteria = criteria.createAlias(key, aliasMap.get(key));
				} else {
					criteria = criteria.createAlias(key, key);
				}

			}
		}
		if (equalMap != null) {
			for (String key : equalMap.keySet()) {
				if (equalMap.get(key) != null) {
					criteria = criteria.add(Restrictions.eq(key,
							equalMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNull(key));
				}

			}
		}
		if (notEqualMap != null) {
			for (String key : notEqualMap.keySet()) {
				if (notEqualMap.get(key) != null) {
					criteria = criteria.add(Restrictions.ne(key,
							notEqualMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNotNull(key));
				}
			}
		}
		if (likeMap != null) {
			for (String key : likeMap.keySet()) {
				if ((likeMap.get(key) instanceof String)
						|| annotationMap.get(key) == null) {
					criteria = criteria.add(Restrictions.like(key,
							likeMap.get(key).toString(), MatchMode.ANYWHERE));
				} else if (likeMap.get(key) instanceof Long
						|| likeMap.get(key) instanceof Integer) {
					criteria = criteria.add(Restrictions.sqlRestriction(
							"this_." + annotationMap.get(key) + " like(?)", "%"
									+ likeMap.get(key) + "%",
							StringType.INSTANCE));
				}

			}
		}
		if (inMap != null) {
			for (String key : inMap.keySet()) {
				criteria = criteria.add(Restrictions.in(key, inMap.get(key)));
			}
		}
		if (startMap != null) {
			for (String key : startMap.keySet()) {
				criteria = criteria
						.add(Restrictions.ge(key, startMap.get(key)));
			}
		}
		if (endMap != null) {
			for (String key : endMap.keySet()) {
				criteria = criteria.add(Restrictions.le(key, endMap.get(key)));
			}
		}

		if (orderName != null) {
			if (isDesc) {
				criteria = criteria.addOrder(Order.desc(orderName));
			} else {
				criteria = criteria.addOrder(Order.asc(orderName));
			}
		}
		return (List<T>) criteria.setFirstResult(start).setMaxResults(limit)
				.list();
	}

	@Override
	public int countByMapAlias(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> inMap, Map<String, Object> startMap,
			Map<String, Object> endMap, String[] fetchNames,
			Map<String, String> aliasMap) {
		Criteria criteria = getSession().createCriteria(entityClass,
				getEntityClass().getSimpleName().toLowerCase());
		if (fetchNames != null) {
			for (String fetchName : fetchNames) {
				criteria = criteria.setFetchMode(fetchName, FetchMode.JOIN);
			}
		}
		if (aliasMap != null) {
			for (String key : aliasMap.keySet()) {
				if (aliasMap.get(key) != null) {
					criteria = criteria.createAlias(key, aliasMap.get(key));
				} else {
					criteria = criteria.createAlias(key, key);
				}

			}
		}
		if (equalMap != null) {
			for (String key : equalMap.keySet()) {
				if (equalMap.get(key) != null) {
					criteria = criteria.add(Restrictions.eq(key,
							equalMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNull(key));
				}

			}
		}
		if (notEqualMap != null) {
			for (String key : notEqualMap.keySet()) {
				if (notEqualMap.get(key) != null) {
					criteria = criteria.add(Restrictions.ne(key,
							notEqualMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNotNull(key));
				}
			}
		}
		if (likeMap != null) {
			for (String key : likeMap.keySet()) {
				if ((likeMap.get(key) instanceof String)
						|| annotationMap.get(key) == null) {
					criteria = criteria.add(Restrictions.like(key,
							likeMap.get(key).toString(), MatchMode.ANYWHERE));
				} else if (likeMap.get(key) instanceof Long
						|| likeMap.get(key) instanceof Integer) {
					criteria = criteria.add(Restrictions.sqlRestriction(
							"this_." + annotationMap.get(key) + " like(?)", "%"
									+ likeMap.get(key) + "%",
							StringType.INSTANCE));
				}
			}
		}
		if (inMap != null) {
			for (String key : inMap.keySet()) {
				criteria = criteria.add(Restrictions.in(key, inMap.get(key)));
			}
		}
		if (startMap != null) {
			for (String key : startMap.keySet()) {
				criteria = criteria
						.add(Restrictions.ge(key, startMap.get(key)));
			}
		}
		if (endMap != null) {
			for (String key : endMap.keySet()) {
				criteria = criteria.add(Restrictions.le(key, endMap.get(key)));
			}
		}
		Object object = criteria.setProjection(
				Projections.countDistinct(getEntityIdName())).uniqueResult();
		return object == null ? 0 : ((Long) object).intValue();
	}

	@Override
	public int countByMapAlias(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> orMap, Map<String, Object[]> inMap,
			Map<String, Object> startMap, Map<String, Object> endMap,
			String[] fetchNames, Map<String, String> aliasMap) {
		Criteria criteria = getSession().createCriteria(entityClass,
				getEntityClass().getSimpleName().toLowerCase());
		if (fetchNames != null) {
			for (String fetchName : fetchNames) {
				criteria = criteria.setFetchMode(fetchName, FetchMode.JOIN);
			}
		}
		if (aliasMap != null) {
			for (String key : aliasMap.keySet()) {
				if (aliasMap.get(key) != null) {
					criteria = criteria.createAlias(key, aliasMap.get(key));
				} else {
					criteria = criteria.createAlias(key, key);
				}

			}
		}
		if (equalMap != null) {
			for (String key : equalMap.keySet()) {
				if (equalMap.get(key) != null) {
					criteria = criteria.add(Restrictions.eq(key,
							equalMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNull(key));
				}

			}
		}
		if (notEqualMap != null) {
			for (String key : notEqualMap.keySet()) {
				if (notEqualMap.get(key) != null) {
					criteria = criteria.add(Restrictions.ne(key,
							notEqualMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNotNull(key));
				}
			}
		}
		if (likeMap != null) {
			for (String key : likeMap.keySet()) {
				if ((likeMap.get(key) instanceof String)
						|| annotationMap.get(key) == null) {
					criteria = criteria.add(Restrictions.like(key,
							likeMap.get(key).toString(), MatchMode.ANYWHERE));
				} else if (likeMap.get(key) instanceof Long
						|| likeMap.get(key) instanceof Integer) {
					criteria = criteria.add(Restrictions.sqlRestriction(
							"this_." + annotationMap.get(key) + " like(?)", "%"
									+ likeMap.get(key) + "%",
							StringType.INSTANCE));
				}
			}
		}
		if (orMap != null) {
			for (String key : orMap.keySet()) {
				Object[] expressions = orMap.get(key);
				Criterion[] criterions = new Criterion[expressions.length];
				for (int i = 0; i < expressions.length; i++) {
					Object[] element = (Object[]) expressions[i];
					String s = element[0].toString();
					Criterion criterion;
					if ("=".equals(s)) {
						if (element[1] == null) {
							criterion = Restrictions.isNull(key);
						} else {
							criterion = Restrictions.eq(key, element[1]);
						}
					} else {
						if (element[1] == null) {
							criterion = Restrictions.isNotNull(key);
						} else {
							criterion = Restrictions.ne(key, element[1]);
						}
					}
					criterions[i] = criterion;
				}
				criteria = criteria.add(Restrictions.or(criterions));
			}
		}
		if (inMap != null) {
			for (String key : inMap.keySet()) {
				criteria = criteria.add(Restrictions.in(key, inMap.get(key)));
			}
		}
		if (startMap != null) {
			for (String key : startMap.keySet()) {
				criteria = criteria
						.add(Restrictions.ge(key, startMap.get(key)));
			}
		}
		if (endMap != null) {
			for (String key : endMap.keySet()) {
				criteria = criteria.add(Restrictions.le(key, endMap.get(key)));
			}
		}
		Object object = criteria.setProjection(
				Projections.countDistinct(getEntityIdName())).uniqueResult();
		return object == null ? 0 : ((Long) object).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByMapAlias(Map<String, Object> equalMap,
			Map<String, Object> notEqualMap, Map<String, Object> likeMap,
			Map<String, Object[]> orMap, Map<String, Object[]> inMap,
			Map<String, Object> startMap, Map<String, Object> endMap,
			String[] fetchNames, String orderName, boolean isDesc, int start,
			int limit, Map<String, String> aliasMap) {
		Criteria criteria = getSession().createCriteria(entityClass);
		if (fetchNames != null) {
			for (String fetchName : fetchNames) {
				criteria = criteria.setFetchMode(fetchName, FetchMode.JOIN);
			}
		}
		if (aliasMap != null) {
			for (String key : aliasMap.keySet()) {
				if (aliasMap.get(key) != null) {
					criteria = criteria.createAlias(key, aliasMap.get(key));
				} else {
					criteria = criteria.createAlias(key, key);
				}

			}
		}
		if (equalMap != null) {
			for (String key : equalMap.keySet()) {
				if (equalMap.get(key) != null) {
					criteria = criteria.add(Restrictions.eq(key,
							equalMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNull(key));
				}

			}
		}
		if (notEqualMap != null) {
			for (String key : notEqualMap.keySet()) {
				if (notEqualMap.get(key) != null) {
					criteria = criteria.add(Restrictions.ne(key,
							notEqualMap.get(key)));
				} else {
					criteria = criteria.add(Restrictions.isNotNull(key));
				}
			}
		}
		if (likeMap != null) {
			for (String key : likeMap.keySet()) {
				if ((likeMap.get(key) instanceof String)
						|| annotationMap.get(key) == null) {
					criteria = criteria.add(Restrictions.like(key,
							likeMap.get(key).toString(), MatchMode.ANYWHERE));
				} else if (likeMap.get(key) instanceof Long
						|| likeMap.get(key) instanceof Integer) {
					criteria = criteria.add(Restrictions.sqlRestriction(
							"this_." + annotationMap.get(key) + " like(?)", "%"
									+ likeMap.get(key) + "%",
							StringType.INSTANCE));
				}

			}
		}
		if (orMap != null) {
			for (String key : orMap.keySet()) {
				Object[] expressions = orMap.get(key);
				Criterion[] criterions = new Criterion[expressions.length];
				for (int i = 0; i < expressions.length; i++) {
					Object[] element = (Object[]) expressions[i];
					String s = element[0].toString();
					Criterion criterion;
					if ("=".equals(s)) {
						if (element[1] == null) {
							criterion = Restrictions.isNull(key);
						} else {
							criterion = Restrictions.eq(key, element[1]);
						}
					} else {
						if (element[1] == null) {
							criterion = Restrictions.isNotNull(key);
						} else {
							criterion = Restrictions.ne(key, element[1]);
						}
					}
					criterions[i] = criterion;
				}
				criteria = criteria.add(Restrictions.or(criterions));
			}
		}
		if (inMap != null) {
			for (String key : inMap.keySet()) {
				criteria = criteria.add(Restrictions.in(key, inMap.get(key)));
			}
		}
		if (startMap != null) {
			for (String key : startMap.keySet()) {
				criteria = criteria
						.add(Restrictions.ge(key, startMap.get(key)));
			}
		}
		if (endMap != null) {
			for (String key : endMap.keySet()) {
				criteria = criteria.add(Restrictions.le(key, endMap.get(key)));
			}
		}

		if (orderName != null) {
			if (isDesc) {
				criteria = criteria.addOrder(Order.desc(orderName));
			} else {
				criteria = criteria.addOrder(Order.asc(orderName));
			}
		}
		return (List<T>) criteria.setFirstResult(start).setMaxResults(limit)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByIds(ID[] ids) {
		return getSession().createCriteria(entityClass)
				.add(Restrictions.in(this.entityID, ids)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getById(ID id, String[] fetchNames) {
		Criteria criteria = getSession().createCriteria(entityClass);
		if (fetchNames != null) {
			for (String fetchName : fetchNames) {
				criteria = criteria.setFetchMode(fetchName, FetchMode.JOIN);
			}
		}
		return (T) criteria.add(Restrictions.eq(entityID, id)).uniqueResult();
	}

	@Override
	public List<T> findAll() {
		return getSession().createCriteria(entityClass).list();
	}

	@Override
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}


}
