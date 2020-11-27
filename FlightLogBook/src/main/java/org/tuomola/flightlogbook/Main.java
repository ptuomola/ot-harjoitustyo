package org.tuomola.flightlogbook;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author ptuomola
 */


@EnableJpaRepositories
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        Application.launch(FlightLogBookApplication.class, args);
    }
}