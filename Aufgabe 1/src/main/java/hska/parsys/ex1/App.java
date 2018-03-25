package hska.parsys.ex1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final int DEFAULT_NUMBER_OF_REVERSE_VENDING_MACHINES = 3;
    public static final int SECONDS = 100;
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        int numberOFMachines = DEFAULT_NUMBER_OF_REVERSE_VENDING_MACHINES;

        if (args.length == 1) {
            try {
                numberOFMachines = Integer.parseInt(args[0]);
            } catch (Exception e) {
                logger.error("Cannot parse number of vending machines.");
                System.exit(1);
            }
        }

        logger.info("Supermarket has {} reverse vending machines.", numberOFMachines);
        logger.info("Starting Supermarket Simulation...");
        Supermarket market = new Supermarket(numberOFMachines);

        for (int customerNumber = 1; customerNumber <= 30; customerNumber++) {
            try {
                market.customerEnters(new Customer(customerNumber));
                Thread.sleep(1 * SECONDS);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
