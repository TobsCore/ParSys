package hska.parsys.ex1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Automat implements Runnable {
  private BlockingQueue<Customer> customerQueue;
  private static Logger logger = LoggerFactory.getLogger(Supermarket.class);

  public Automat(BlockingQueue<Customer> customerQueue) {
    this.customerQueue = customerQueue;
  }

  @Override
  public void run() {
    while (true) {
      Customer customer = null;
      try {
        customer = customerQueue.take();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      logger.info("Customer #{} is using machine.", customer.getNumber());
      while (customer.getBags() != 0) {
        // Work on bag
        int workTimeOnBag =
            ThreadLocalRandom.current().nextInt(Customer.MIN_TIME, Customer.MAX_TIME + 1);
        try {
          logger.info(
              "Processing Bag of Customer #{} ({} seconds)", customer.getNumber(), workTimeOnBag);
          Thread.sleep(workTimeOnBag * App.SECONDS);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        customer.decreadeBags();
      }
      logger.info("Customer #{} finishes using machine", customer.getNumber());
    }
  }
}
