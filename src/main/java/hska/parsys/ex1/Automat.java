package hska.parsys.ex1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Automat implements Runnable {
  private BlockingQueue<Customer> customerQueue;
  private static Logger logger = LoggerFactory.getLogger(Supermarket.class);

  Automat(BlockingQueue<Customer> customerQueue) {
    this.customerQueue = customerQueue;
  }

  @Override
  public void run() {
    try {
      while (true) {
        Customer customer = customerQueue.take();
        logger.info("Customer #{} is using machine.", customer.getNumber());
        while (customer.getBags() != 0) {
          // Work on bag
          int workTimeOnBag =
              ThreadLocalRandom.current().nextInt(Customer.MIN_TIME, Customer.MAX_TIME + 1);
          try {
            logger.info(
                "Processing Bag of Customer #{} ({} seconds)", customer.getNumber(), workTimeOnBag);
            TimeUnit.SECONDS.sleep(workTimeOnBag);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          customer.decrementBags();
        }
        logger.info("Customer #{} finishes using machine", customer.getNumber());
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
