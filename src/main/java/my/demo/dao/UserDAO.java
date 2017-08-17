package my.demo.dao;


import my.demo.entity.User;

public interface UserDAO extends BaseDAO<User, Integer> {

    User findByEmail(String email);

    /**
     * 根据用户 userName 查找用户
     * @param userName
     * @return
     */
    User findByUserName(String userName);
}
