package hska.parsys.ex1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Supermarket {
  private static Logger logger = LoggerFactory.getLogger(Supermarket.class);
  private int freeMachines = 3;
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition();

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

  public void customerEnters(Customer customer) {
    lock.lock();
    logger.info(
        "Customer #{} ({} bags) enters the supermarket", customer.getNumber(), customer.getBags());

    try {
      while (freeMachines == 0) {
        try {
          condition.await();
          logger.info("Customer #{} has to wait", customer.getNumber());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      freeMachines--;
      Thread customerThread = new Thread(customer);
      customer.setSupermarket(this);
      customerThread.start();
    } finally {
      lock.unlock();
    }
  }

  public void customerLeaves() {
    lock.lock();
    try {
      freeMachines++;
      condition.signal();
    } finally {
      lock.unlock();
    }
  }
}
