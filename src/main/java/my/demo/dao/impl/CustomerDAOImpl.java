package my.demo.dao.impl;


import my.demo.dao.CustomerDAO;
import my.demo.dao.UserDAO;
import my.demo.entity.Customer;
import my.demo.entity.User;
import org.hibernate.criterion.Restrictions;

public class CustomerDAOImpl extends BaseDAOImpl<Customer, Long> implements CustomerDAO {

}
