package hska.parsys.ex1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Supermarket {
  private static Logger logger = LoggerFactory.getLogger(Supermarket.class);
  private int freeMachines = 3;

  /**
   * Initializes the supermarket with the given number of reverse vending machines.
   *
   * @param initialFreeMachines The amount of reverse vending machines must be larger than 1.
   *     Otherwise an @code{IllegalArgumentException} is thrown.
   */
  Supermarket(int initialFreeMachines) {
    if (initialFreeMachines < 1) {
      throw new IllegalArgumentException(
          "The amount of machines must be greater than 0. Is " + initialFreeMachines);
    }
    freeMachines = initialFreeMachines;
  }

  public synchronized void customerEnters(Customer customer) {
    logger.info(
        "Customer #{} ({} bags) enters the supermarket", customer.getNumber(), customer.getBags());

    while(freeMachines == 0) {
      try {
        logger.info("Customer #{} has to wait", customer.getNumber());
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    freeMachines--;
    Thread customerThread = new Thread(customer);
    customer.setSupermarket(this);
    customerThread.start();
  }

  public synchronized void customerLeaves() {
    freeMachines++;
    notify();
  }
}
