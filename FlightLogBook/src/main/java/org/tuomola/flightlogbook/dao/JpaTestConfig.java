package org.tuomola.flightlogbook.dao;

import javax.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Class for overriding the data source configuration when in test mode.
 * @author ptuomola
 */

@Configuration
public class JpaTestConfig {

    /** 
     * Provide the data source to eb used in test mode.
     * @return Configured data source object for test use. 
     */
    @Bean
    @Profile("test")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
        ds.setUsername("sa");
        ds.setPassword("");
        
        return ds;
    }
}
