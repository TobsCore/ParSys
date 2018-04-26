package hska.parsys.ex1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;

class Supermarket {
  private static Logger logger = LoggerFactory.getLogger(Supermarket.class);
  private final Semaphore semaphore = new Semaphore(3);
  private ExecutorService pool;

  /**
   * Initializes the supermarket with the given number of reverse vending machines.
   *
   * @param initialFreeMachines The amount of reverse vending machines must be larger than 1.
   *     Otherwise an @code{IllegalArgumentException} is thrown.
   */
  Supermarket(int initialFreeMachines, ExecutorService pool) {
    if (initialFreeMachines < 1) {
      throw new IllegalArgumentException(
          "The amount of machines must be greater than 0. Is " + initialFreeMachines);
    }
    this.pool = pool;
  }

  void customerEnters(Customer customer) throws InterruptedException {
    semaphore.acquire();
    logger.info(
        "Customer #{} ({} bags) enters the supermarket", customer.getNumber(), customer.getBags());

    try {
      pool.submit(
          () -> {
            logger.info("Customer #{} is using machine.", customer.getNumber());
            while (customer.getBags() != 0) {
              // Work on bag
              int workTimeOnBag =
                  ThreadLocalRandom.current().nextInt(Customer.MIN_TIME, Customer.MAX_TIME + 1);
              try {
                logger.info(
                    "Processing Bag of Customer #{} ({} seconds)",
                    customer.getNumber(),
                    workTimeOnBag);
                Thread.sleep(workTimeOnBag * App.SECONDS);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              customer.decreadeBags();
            }
            logger.info("Customer #{} finishes using machine", customer.getNumber());
          });
    } finally {
      semaphore.release();
    }
  }
}
