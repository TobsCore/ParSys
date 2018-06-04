package hska.parsys.ex1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

class Supermarket {
  private static Logger logger = LoggerFactory.getLogger(Supermarket.class);
  private final int machineCount;
  private BlockingQueue<Customer> queue = new PriorityBlockingQueue<>();

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
    this.machineCount = initialFreeMachines;
    for (int i = 0; i < initialFreeMachines; i++) {
      new Thread(new Automat(queue)).start();
    }
  }

  void customerEnters(Customer customer) throws InterruptedException {
    queue.put(customer);
    logger.info(
        "{} enters the supermarket", customer.toString());
  }

  void shutdown() throws InterruptedException {
    for (int i = 0; i < machineCount; i++) {
      queue.put(new Customer(true));
    }
  }
}
