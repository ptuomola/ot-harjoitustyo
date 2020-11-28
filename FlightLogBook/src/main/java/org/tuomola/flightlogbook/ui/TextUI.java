package org.tuomola.flightlogbook.ui;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tuomola.flightlogbook.domain.Aircraft;
import org.tuomola.flightlogbook.domain.Airport;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.service.AircraftService;
import org.tuomola.flightlogbook.service.AirportService;
import org.tuomola.flightlogbook.service.FlightLogService;
import org.tuomola.flightlogbook.service.FlightService;

/**
 *
 * @author ptuomola
 */

@Service
public class TextUI {

    @Autowired 
    private final FlightLogService fls;
    
    @Autowired
    private final FlightService fs;
    
    @Autowired
    private final AirportService aps;
    
    @Autowired
    private final AircraftService acs;
    
    private final HashMap<String, String> commands;
    
    public TextUI(FlightLogService fls, FlightService fs, AirportService aps, AircraftService acs) {
        this.fls = fls;
        this.fs = fs;
        this.aps = aps;
        this.acs = acs;
        
        this.commands = new HashMap<>();
        this.commands.put("1", "Start new flight");
        this.commands.put("2", "Print log");
        this.commands.put("3", "Print total times");
        this.commands.put("4", "List all airports");
        this.commands.put("5", "List all aircraft");
        this.commands.put("9", "Exit programme");
        this.commands.put("?", "Print help");
    }
    
    public void execute(Scanner reader) {
        System.out.println("Enter pilot name: ");
        
        // TODO: Rework this to support new FlightLog interface
        FlightLog fl = fls.findOrCreateLog(null);
        
        printInstructions();
        
        while (true) {
            System.out.println("Enter command: ");
            String input = reader.nextLine();
            
            if (!this.commands.containsKey(input)) {
                System.out.println("Invalid command - enter ? for help");
                continue;
            }
            
            if (input.equals("9")) {
                System.out.println("Exiting...");
                break;
            } else if (input.equals("?")) {
                printInstructions();
            } else if (input.equals("1")) {
                startNewFlight(reader, fl);
            } else if (input.equals("2")) {
                printLog(fl);
            } else if (input.equals("3")) {
                printTotals(fl);
            } else if (input.equals("4")) {
                printAirports();
            } else if (input.equals("5")) {
                printAircraft();
            }
        }
    }

    private void printInstructions() {
        for (Entry entry : this.commands.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void startNewFlight(Scanner reader, FlightLog fl) {
        Flight flight = fls.addNewFlight(fl);
        
        System.out.println("Enter aircraft ID (or press ENTER to leave empty):");
        String aircraftId = reader.nextLine();
        if (aircraftId != null) {
            fs.setAircraft(flight, aircraftId);
        }

        System.out.println("Enter origin airport code (or press ENTER to leave empty):");
        String originCode = reader.nextLine();
        if (originCode != null) {
            fs.setOrigin(flight, originCode);
        }

        System.out.println("Enter destination airport code (or press ENTER to leave empty):");
        String destionationCode = reader.nextLine();
        if (destionationCode != null) {
            fs.setDestination(flight, destionationCode);
        }

        
        System.out.println("Press ENTER to start taxiing...");
        reader.nextLine();
        fs.startTaxi(flight);

        System.out.println("Press ENTER to take off...");
        reader.nextLine();
        fs.takeOff(flight);

        System.out.println("Press ENTER to land...");
        reader.nextLine();
        fs.land(flight);

        System.out.println("Press ENTER to complete taxiing...");
        reader.nextLine();
        fs.stopFlight(flight);
    }

    private void printTotals(FlightLog fl) {
        System.out.println("Total time: " + fl.getTotalTime());
        System.out.println("Total flight time: " + fl.getTotalFlightTime());
        System.out.println("Total landings: " + fl.getTotalLandings());
        System.out.println("Total takeoffs: " + fl.getTotalTakeOffs());
    }

    private void printLog(FlightLog fl) {
        System.out.println(fl);
    }

    private void printAirports() {
        for (Airport airport : aps.getAllAirports()) {
            System.out.println(airport);
        }
    }
    
    private void printAircraft() {
        for (Aircraft aircraft : acs.getAllAircraft()) {
            System.out.println(aircraft);
        }
    }
    
}
