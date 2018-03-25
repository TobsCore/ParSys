package hska.parsys.ex1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Supermarket {
    private static Logger logger = LoggerFactory.getLogger(Supermarket.class);
    private List<ReverseVendingMachine> machines;
    private int customerCounter = 0;

    /**
     * Initializes the supermarket with the given number of reverse vending machines.
     *
     * @param numberOfReverseVendingMachines The amount of vending machines must be larger than 1.
     *                                       Otherwise an @code{IllegalArgumentException} is thrown.
     */
    Supermarket(int numberOfReverseVendingMachines) {
        machines = new ArrayList<>(numberOfReverseVendingMachines);
        for (int i = 0; i < numberOfReverseVendingMachines; i++) {
            ReverseVendingMachine machine = new ReverseVendingMachine(i + 1);
            machines.add(machine);
        }
    }

    synchronized public void customerEnters(Customer customer) {
        logger.info("Customer #{} ({} bags) enters the supermarket", customer.getNumber(), customer.getBags());
        ReverseVendingMachine selectedMachine = machines.get(customerCounter % machines.size());
        logger.info("Customer #{} is appointed machine #{}", customer.getNumber(), selectedMachine.getID());
        customer.setAppointedMachine(selectedMachine);
        Thread customerThread = new Thread(customer);
        customerThread.start();
        customerCounter++;
    }
}
