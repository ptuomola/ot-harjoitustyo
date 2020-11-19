package org.tuomola.flightlogbook;

import java.util.Scanner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.activation.DataSource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.tuomola.flightlogbook.domain.Aircraft;
import org.tuomola.flightlogbook.service.AircraftService;
import org.tuomola.flightlogbook.service.LogBookService;
import org.tuomola.flightlogbook.ui.TextUI;

/**
 *
 * @author ptuomola
 */

@Configuration
@ComponentScan
@EnableJpaRepositories
//public class Main extends Application {
public class Main {
      
/*
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/mainScene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("css/styles.css").toExternalForm());
        
        stage.setTitle("Flight Log Book");
        stage.setScene(scene);
        stage.show();
    }
*/
    
    public static void main(String[] args) {
        // TODO: Graphic UI disabled for now - implementing business logic with text UI
        // launch(args);
        
        // TODO: Remove below code testing creation of database tables
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);

        EntityManager em = Persistence.createEntityManagerFactory("FlightLogBook").createEntityManager();
        AircraftService service = ctx.getBean(AircraftService.class);
        Aircraft ac = new Aircraft();
        ac.setIdentifier("OH-KAT");
        ac.setType("DA20-C1");
        service.saveAircraft(ac);
       
        // Start off UI
        TextUI ui = new TextUI(new Scanner(System.in));
        ui.execute(new LogBookService());
    }

    @Bean
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(adapter);
        factory.setPackagesToScan("org.tuomola.flightlogbook");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }
    
}
