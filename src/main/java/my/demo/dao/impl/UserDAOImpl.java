package my.demo.dao.impl;


import my.demo.dao.UserDAO;
import my.demo.entity.User;
import org.hibernate.criterion.Restrictions;

public class UserDAOImpl extends BaseDAOImpl<User, Integer> implements UserDAO {

    public User findByEmail(String email) {
        Object object = getSession().createCriteria(User.class).add(Restrictions.eq("email", email)).setMaxResults(1).uniqueResult();
        return object == null ? null : (User) object;
    }

    public User findByUserName(String userName) {
        return (User) getSession().createCriteria(User.class)
                .add(Restrictions.eq("userName", userName))
                .setMaxResults(1)
                .uniqueResult();
    }
}
