package my.demo.service.impl;


import my.demo.dao.UserDAO;
import my.demo.entity.User;
import my.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Ash on 2015/5/22.
 */
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService {

    @Autowired
    protected UserDAO userDAO;
    
    public User searchByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public User searchByUserName(String userName) {
        return userDAO.findByUserName(userName);
    }
}
