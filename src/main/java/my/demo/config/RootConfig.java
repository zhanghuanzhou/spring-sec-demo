package my.demo.config;

import com.jolbox.bonecp.BoneCPDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Spring 基础配置
 */
@Configuration
@EnableTransactionManagement
@Import({DAOConfig.class, ServiceConfig.class,TaskConfig.class})
public class RootConfig {

    /**
     * 数据源
     * @return
     */
    @Bean
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return  dataSource;
    }

    /**
     * 配置sessionFactory
     *
     * @param dataSource
     * @return
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setPackagesToScan("my.demo.entity");
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        hibernateProperties.setProperty("bonecp.idleMaxAge", "240");
        hibernateProperties.setProperty("bonecp.idleConnectionTestPeriod", "60");
        hibernateProperties.setProperty("bonecp.partitionCount", "3");
        hibernateProperties.setProperty("bonecp.acquireIncrement", "10");
        hibernateProperties.setProperty("bonecp.maxConnectionsPerPartition", "60");
        hibernateProperties.setProperty("bonecp.minConnectionsPerPartition", "20");
        hibernateProperties.setProperty("bonecp.statementsCacheSize", "50");
        hibernateProperties.setProperty("bonecp.releaseHelperThreads", "3");

        hibernateProperties.setProperty("hibernate.show_sql", "true");
        hibernateProperties.setProperty("hibernate.generate_statistics", "true");
        hibernateProperties.setProperty("hibernate.connection.release_mode", "auto");
        hibernateProperties.setProperty("hibernate.autoReconnect", "true");
        hibernateProperties.setProperty("cache.use_query_cache", "true");
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
        sessionFactoryBean.setHibernateProperties(hibernateProperties);
        return sessionFactoryBean;
    }

    /**
     * 事务管理器
     *
     * @param sessionFactory
     * @return
     */
    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    
}
