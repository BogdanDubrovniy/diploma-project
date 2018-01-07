package com.dubrovnyi.bohdan.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import static com.dubrovnyi.bohdan.constants.ConstantClass.*;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {
                ENTITIES_PACKAGE_All,
                REPOSITORIES_PACKAGE,
                JSF_PACKAGE
        }
)
@PropertySource(value = {APPLICATION_PROPERTIES_CLASSPATH})
public class HibernateConfig {
    private static final String DATASOURCE_DRIVER_VALUE =
            "datasource.driver";
    private static final String DATASOURCE_URL_VALUE =
            "datasource.url";
    private static final String DATASOURCE_USERNAME_VALUE =
            "datasource.username";
    private static final String DATASOURCE_PASSWORD_VALUE =
            "datasource.password";
    private static final boolean SET_AUTO_COMMIT = false;

    private static final String HIBERNATE_DIALECT_VALUE =
            "hibernate.dialect";
    private static final String HIBERNATE_SHOW_SQL_VALUE =
            "hibernate.show_sql";
    private static final String HIBERNATE_BATCH_SIZE_VALUE =
            "hibernate.batch.size";
    private static final String HIBERNATE_FETCH_SIZE_VALUE =
            "hibernate.fetch.size";
    private static final String HIBERNATE_FETCH_DEPTH_VALUE =
            "hibernate.fetch.depth";

    @Autowired
    private Environment env;

    @Autowired
    private DataSource dataSource;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env
                .getRequiredProperty(DATASOURCE_DRIVER_VALUE));
        dataSource.setUrl(env.getRequiredProperty(DATASOURCE_URL_VALUE));
        dataSource.setUsername(env
                .getRequiredProperty(DATASOURCE_USERNAME_VALUE));
        dataSource.setPassword(env
                .getRequiredProperty(DATASOURCE_PASSWORD_VALUE));
        dataSource.setDefaultAutoCommit(SET_AUTO_COMMIT);

        return dataSource;
    }


    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(ENTITIES_PACKAGE);
        sessionFactory.setHibernateProperties(getHibernateProperties());
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager
    transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public TransactionAttributeSource annotationTransactionAttributeSource() {
        return new AnnotationTransactionAttributeSource();
    }

    @Bean
    public TransactionInterceptor transactionInterceptor(
            PlatformTransactionManager transactionManager) {
        return new TransactionInterceptor(transactionManager,
                annotationTransactionAttributeSource());
    }

    @Bean
    public AbstractEntityManagerFactoryBean
    entityManagerFactory(DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean em =
                new LocalContainerEntityManagerFactoryBean();

        em.setJpaProperties(getHibernateProperties());

        em.setPersistenceXmlLocation(PERSISTENCE_XML_PATH);
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setDataSource(dataSource);

        return em;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(AvailableSettings.DIALECT,
                env.getRequiredProperty(HIBERNATE_DIALECT_VALUE));
        properties.put(AvailableSettings.SHOW_SQL,
                env.getRequiredProperty(HIBERNATE_SHOW_SQL_VALUE));
        properties.put(AvailableSettings.STATEMENT_BATCH_SIZE,
                env.getRequiredProperty(HIBERNATE_BATCH_SIZE_VALUE));
        properties.put(AvailableSettings.STATEMENT_FETCH_SIZE,
                env.getRequiredProperty(HIBERNATE_FETCH_SIZE_VALUE));
        properties.put(AvailableSettings.MAX_FETCH_DEPTH,
                env.getRequiredProperty(HIBERNATE_FETCH_DEPTH_VALUE));

        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.bytecode.use_reflection_optimizer", "false");

        return properties;
    }

}