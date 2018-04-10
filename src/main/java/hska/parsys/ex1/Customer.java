package hska.parsys.ex1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

public class Customer implements Runnable {
  private static Logger logger = LoggerFactory.getLogger(Customer.class);

  private static final int MIN_TIME = 3;
  private static final int MAX_TIME = 7;
  private static final int MIN_BAGS = 2;
  private static final int MAX_BAGS = 5;
  private final int number;
  private int bags;
  private Supermarket supermarket;

  Customer(int number) {
    this.number = number;
    this.bags = ThreadLocalRandom.current().nextInt(MIN_BAGS, MAX_BAGS + 1);
  }

  public void run() {
    logger.info("Customer #{} is using machine.", getNumber());
    while (!isFinished()) {
      // Work on bag
      processBag();
    }
    logger.info("Customer #{} finishes using machine", getNumber());

  }

  public int getNumber() {
    return this.number;
  }

  public int getBags() {
    return bags;
  }

  private synchronized boolean isFinished() {
    return bags == 0;
  }

  private void processBag() {
    int workTimeOnBag = ThreadLocalRandom.current().nextInt(MIN_TIME, MAX_TIME + 1);
    try {
      logger.info("Processing Bag of Customer #{} ({} seconds)", getNumber(), workTimeOnBag);
      Thread.sleep(workTimeOnBag * App.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    bags--;
  }

  public void setSupermarket(Supermarket supermarket) {
    this.supermarket = supermarket;
  }

  public Supermarket getSupermarket() {
    return supermarket;
  }
}
