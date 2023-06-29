package pro.hexa.backend.main.api.common.config.db;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import javax.sql.DataSource;
import org.h2.tools.Server;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@Profile({"default", "development"})
public class H2DataSourceConfig {

    // hibernate Bean container와 관련된 객체임. hibernate의 sessionFactoryBean과 동일한 역할을 한다.
    // DB가 자동으로 붙어지게끔 만드는 일.
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
        EntityManagerFactoryBuilder builder,
        ConfigurableListableBeanFactory beanFactory
    ) {
        // 빌더와 beanFactory를 인자로 받고, 빈 하나를 생성한다. 빈은 builder에 여러가지 메서드를 사용하여 만든다.
        LocalContainerEntityManagerFactoryBean bean = builder
            .dataSource(dataSource())
            .properties(new HashMap<>())
            .packages("pro.hexa.backend")
            .build();

        bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        bean.setJpaProperties(additionalProperties());
        bean.getJpaPropertyMap().put(AvailableSettings.BEAN_CONTAINER, new SpringBeanContainer(beanFactory));

        return bean;
    }

    @Bean
    public DataSource dataSource() {
        try {
            Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9093").start();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUsername("sa");
        dataSource.setPassword("1234");
        dataSource.setJdbcUrl("jdbc:h2:mem:hexa;INIT=CREATE SCHEMA IF NOT EXISTS hexa;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        return dataSource;
    }

    // 데이터베이스config에 transaction manager 빈 설정하기
    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty("hibernate.default_catalog", "hexa");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl");

        return properties;
    }
}
