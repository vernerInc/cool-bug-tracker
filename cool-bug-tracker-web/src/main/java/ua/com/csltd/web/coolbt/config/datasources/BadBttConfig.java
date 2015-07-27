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
import ua.com.csltd.server.dao.badbtt.BaseBadDAO;
import ua.com.csltd.server.dao.badbtt.models.badbug.BadBug;
import ua.com.csltd.server.dao.badbtt.models.department.BadDepartment;
import ua.com.csltd.server.dao.badbtt.models.priority.Priority;
import ua.com.csltd.server.dao.badbtt.models.product.BadProduct;
import ua.com.csltd.server.dao.badbtt.models.productversion.ProductVersion;
import ua.com.csltd.server.dao.badbtt.models.status.Status;
import ua.com.csltd.server.dao.badbtt.models.subsystem.SubSystem;
import ua.com.csltd.server.dao.badbtt.models.user.User;

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
        entityManagerFactoryRef = "badEntityManagerFactory",
        transactionManagerRef = "badTransactionManager",
        basePackages = {"ua.com.csltd.server.dao.badbtt"})
public class BadBttConfig {

    @Autowired
    protected Environment environment;

    @Bean
    public BaseBadDAO<Status> statusBadDAO() {
        return new BaseBadDAO<>(Status.class);
    }

    @Bean
    public BaseBadDAO<BadBug> bugBadDAO() {
        return new BaseBadDAO<>(BadBug.class);
    }

    @Bean
    public BaseBadDAO<BadDepartment> departmentBadDAO() {
        return new BaseBadDAO<>(BadDepartment.class);
    }

    @Bean
    public BaseBadDAO<Priority> priorityBadDAO() {
        return new BaseBadDAO<>(Priority.class);
    }

    @Bean
    public BaseBadDAO<BadProduct> productBadDAO() {
        return new BaseBadDAO<>(BadProduct.class);
    }

    @Bean
    public BaseBadDAO<ProductVersion> productVersionBadDAO() {
        return new BaseBadDAO<>(ProductVersion.class);
    }

    @Bean
    public BaseBadDAO<SubSystem> subSystemBadDAO() {
        return new BaseBadDAO<>(SubSystem.class);
    }

    @Bean
    public BaseBadDAO<User> userBaseDAO() {
        return new BaseBadDAO<>(User.class);
    }

    @Bean(name = "badDataSource")
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource(environment.getProperty("bad.btt.db.jndi.name"));
    }

    @Bean(name = "badEntityManager")
    public EntityManager entityManager() {
        return entityManagerFactory().createEntityManager();
    }

    @Bean(name = "badEntityManagerFactory")
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource());

        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setDatabase(Database.valueOf(environment.getProperty("bad.btt.db.jpa.type", "SQL_SERVER")));

        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan("ua.com.csltd.server.dao.badbtt.models");
        lef.setPersistenceUnitName("badPersistenceUnit");
        lef.setJpaDialect(new HibernateJpaDialect());
        lef.afterPropertiesSet();
        return lef.getObject();
    }

    @Bean(name = "badTransactionManager")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }
}
