package hska.parsys.ex1;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Customer implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(Customer.class);

    private static final int MIN_TIME = 3;
    private static final int MAX_TIME = 7;
    private static final int MIN_BAGS = 2;
    private static final int MAX_BAGS = 5;
    private final int number;
    private int bags;

    private Optional<Supermarket> supermarketInstance;

    Customer(int number) {
        this.number = number;
        this.bags = ThreadLocalRandom.current().nextInt(MIN_BAGS, MAX_BAGS + 1);
    }

    public void run() {
        if (supermarketInstance.isPresent()) {
            Set<ReverseVendingMachine> machines = supermarketInstance.get().getMachines();

            synchronized (this) {
                while (machines.stream().filter(machine -> !machine.isInUse()).count() == 0) {
                    logger.debug("No free machines available. Customer #{} has to wait.", getNumber());
                    try {
                        this.wait();
                        logger.warn("AWOKE FROM WAITING!!!!!!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Optional<ReverseVendingMachine> freeMachine = machines.stream().filter(machine -> !machine.isInUse()).findFirst();
                if (freeMachine.isPresent()) {
                    this.useMachine(freeMachine.get());
                } else {
                    logger.error("No machine was actually free, but one should've been.");
                }
            }
        } else {
            throw new IllegalStateException("Customer needs a supermarket in order to run correctly.");
        }
    }

    public int getNumber() {
        return this.number;
    }

    public int getBags() {
        return bags;
    }

    synchronized public boolean isFinished() {
        return bags == 0;
    }

    synchronized public void useMachine(ReverseVendingMachine machine) {
        logger.info("Customer #{} is using Machine #{}", getNumber(), machine.getMachineID());
        machine.setInUse(true);
        while (!isFinished()) {
            // Work on bag
            processBag();
        }
        logger.info("Customer #{} finishes.", getNumber());
        machine.setInUse(false);
        this.notify();
    }

    synchronized public void processBag() {
        int workTimeOnBag = ThreadLocalRandom.current().nextInt(MIN_TIME, MAX_TIME + 1);
        try {
            logger.debug("Processing Bag of Customer #{} ({} seconds)", getNumber(), workTimeOnBag);
            Thread.sleep(workTimeOnBag * App.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bags--;
    }

    public Optional<Supermarket> getSupermarketInstance() {
        return supermarketInstance;
    }

    public void setSupermarketInstance(Supermarket supermarketInstance) {
        this.supermarketInstance = Optional.of(supermarketInstance);
    }

    public void resetSupermarketInstance() {
        this.supermarketInstance = Optional.empty();
    }
}
