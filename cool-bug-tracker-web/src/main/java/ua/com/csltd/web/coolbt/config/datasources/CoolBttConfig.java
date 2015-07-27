package ua.com.csltd.web.coolbt.config.datasources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ua.com.csltd.server.dao.coolbtt.BaseCoolDAO;
import ua.com.csltd.server.dao.coolbtt.models.bug.Bug;
import ua.com.csltd.server.dao.coolbtt.models.department.Department;
import ua.com.csltd.server.dao.coolbtt.models.products.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * @author : verner
 * @since : 21.07.2015
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "coolEntityManagerFactory",
        transactionManagerRef = "coolTransactionManager",
        basePackages = {"ua.com.csltd.server.dao.coolbtt"})
public class CoolBttConfig {

    @Autowired
    protected Environment environment;

    @Bean
    public BaseCoolDAO<Bug> bugCoolDAO() {
        return new BaseCoolDAO<>(Bug.class);
    }

    @Bean
    public BaseCoolDAO<Department> departmentCoolDAO() {
        return new BaseCoolDAO<>(Department.class);
    }

    @Bean
    public BaseCoolDAO<Product> productCoolDAO() {
        return new BaseCoolDAO<>(Product.class);
    }

    @Bean(name = "coolDataSource")
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource(environment.getProperty("cool.btt.db.jndi.name"));
    }

    @Bean(name = "coolEntityManager")
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean(name = "coolEntityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource());

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.valueOf(environment.getProperty("cool.btt.db.jpa.type", "ORACLE")));

        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan("ua.com.csltd.server.dao.coolbtt.models");
        lef.setPersistenceUnitName("coolPersistenceUnit");
        lef.setJpaDialect(new HibernateJpaDialect());
        lef.afterPropertiesSet();
        return lef.getObject();
    }

    @Bean(name = "coolTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }
}
