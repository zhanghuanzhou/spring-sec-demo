package my.demo.service;


import my.demo.entity.User;

/**
 * Created by Ash on 2015/5/22.
 */
public interface UserService extends BaseService<User, Integer> {
    User searchByEmail(String email);

    User searchByUserName(String userName);
}
