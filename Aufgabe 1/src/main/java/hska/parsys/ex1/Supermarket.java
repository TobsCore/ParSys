package hska.parsys.ex1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.Set;

public class Supermarket {
    Logger logger = LoggerFactory.getLogger(Supermarket.class);
    private Set<ReverseVendingMachine> machines;

    /**
     * Initializes the supermarket with the given number of reverse vending machines.
     *
     * @param numberOfReverseVendingMachines The amount of vending machines must be larger than 1.
     *                                       Otherwise an @code{IllegalArgumentException} is thrown.
     */
    Supermarket(int numberOfReverseVendingMachines) {
        machines = new LinkedHashSet<>(numberOfReverseVendingMachines);
        for (int i = 0; i < numberOfReverseVendingMachines; i++) {
            ReverseVendingMachine machine = new ReverseVendingMachine(i + 1);
            machines.add(machine);
        }
    }

    synchronized public void customerEnters(Customer customer) {
        logger.info("Customer #{} ({} bags) enters the supermarket.", customer.getNumber(), customer.getBags());
        customer.setSupermarketInstance(this);
        Thread customerThread = new Thread(customer);
        customerThread.start();
    }

    public Set<ReverseVendingMachine> getMachines() {
        return machines;
    }


}
