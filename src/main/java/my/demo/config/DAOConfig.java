package my.demo.config;

import my.demo.dao.CustomerDAO;
import my.demo.dao.OrderDAO;
import my.demo.dao.UserDAO;
import my.demo.dao.impl.CustomerDAOImpl;
import my.demo.dao.impl.OrderDAOImpl;
import my.demo.dao.impl.UserDAOImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DAO配置
 */
@Configuration
public class DAOConfig {

    @Bean
    public UserDAO userDAO() {
        return new UserDAOImpl();
    }

    @Bean
    public CustomerDAO customerDAO() {
        return new CustomerDAOImpl();
    }

    @Bean
    public OrderDAO orderDAO() {
        return new OrderDAOImpl();
    }

}
