package my.demo.config;


import my.demo.service.LoginService;
import my.demo.service.UserService;
import my.demo.service.impl.LoginServiceImpl;
import my.demo.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Service 配置文件
 */
@Configuration
public class ServiceConfig {

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
