package org.tuomola.flightlogbook.ui;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import org.tuomola.flightlogbook.domain.Flight;
import org.tuomola.flightlogbook.domain.FlightLog;
import org.tuomola.flightlogbook.service.LogBookService;

/**
 *
 * @author ptuomola
 */
public class TextUI {
    
    private Scanner reader;
    private HashMap<String, String> commands;
    
    public TextUI(Scanner reader)
    {
        this.reader = reader;
        
        this.commands = new HashMap<>();
        this.commands.put("1", "Start new flight");
        this.commands.put("2", "Print log");
        this.commands.put("3", "Print total times");
        this.commands.put("9", "Exit programme");
    }
    
    public void execute(LogBookService lbs)
    {
        System.out.println("Enter pilot name: ");
        FlightLog fl = lbs.createLog(reader.nextLine());
        
        printInstructions();
        
        while(true)
        {
            System.out.println("Enter command: ");
            String input = reader.nextLine();
            
            if(!this.commands.containsKey(input)) {
                System.out.println("Invalid command - enter ? for help");
                continue;
            }
            
            if(input.equals("9")) {
                System.out.println("Exiting...");
                break;
            } else if(input.equals("?")) {
                printInstructions();
            } else if(input.equals("1")) {
                startNewFlight(fl);
            } else if(input.equals("2")) {
                printLog(fl);
            } else if(input.equals("3")) {
                printTotals(fl);
            }
        }
    }

    private void printInstructions() {
        for(Entry entry : this.commands.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private void startNewFlight(FlightLog fl) {
        Flight flight = fl.startFlight();

        System.out.println("Press ENTER to start taxiing...");
        reader.nextLine();
        flight.startTaxi();

        System.out.println("Press ENTER to take off...");
        reader.nextLine();
        flight.takeOff();

        System.out.println("Press ENTER to land...");
        reader.nextLine();
        flight.land();

        System.out.println("Press ENTER to complete taxiing...");
        reader.nextLine();
        flight.stopFlight();
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
    
}
