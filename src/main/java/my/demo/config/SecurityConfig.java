package my.demo.config;

import my.demo.authentication.LoginFailureHandler;
import my.demo.authentication.LoginSuccessHandler;
import my.demo.authentication.UserAuthenticationProvider;
import my.demo.service.LoginService;
import my.demo.service.impl.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity
@EnableGlobalAuthentication
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

   /* @Autowired
    private LoginService loginService;*/

    /**
     * 将自定义的AuthenticationProvider作为bean来定义自定义身份验证
     * @return
     */
    /*@Bean
    public AuthenticationProvider authenticationProvider() {
        AuthenticationProvider authenticationProvider = new UserAuthenticationProvider(loginService);
        return authenticationProvider;
    }*/

    /**
     * 只有当AuthenticationManagerBuilder 没有被填充，并且没有定义AuthenticationProvider时，才会使用此方法。
     * @return
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return new LoginServiceImpl();
    }

    /**
     * 定制通过将PasswordEncoder来编码密码
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        AuthenticationSuccessHandler successHandler = new LoginSuccessHandler();
        return successHandler;
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        AuthenticationFailureHandler failureHandler = new LoginFailureHandler();
        return failureHandler;
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/assets/**").permitAll()
                .anyRequest().authenticated();


        // 登录
        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .permitAll();

        // 自定义注销
        http.logout()
                .logoutUrl("/login?logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true);

        // RemeberMe
       /* http.rememberMe()
                .tokenValiditySeconds(1209600)
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(loginService);*/
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.authenticationProvider(authenticationProvider());
        auth.userDetailsService(loginService).passwordEncoder(new BCryptPasswordEncoder());
    }*/


}