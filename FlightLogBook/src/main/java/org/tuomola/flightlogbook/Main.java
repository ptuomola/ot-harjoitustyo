package org.tuomola.flightlogbook;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main class of the application.
 * @author ptuomola
 */

@EnableJpaRepositories
@SpringBootApplication
public class Main {

    /**
     * Main method - entry point for the execution.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Application.launch(FlightLogBookApplication.class, args);
    }
}