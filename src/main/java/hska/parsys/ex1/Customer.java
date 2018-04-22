package hska.parsys.ex1;

import java.util.concurrent.ThreadLocalRandom;

class Customer {
  static final int MIN_TIME = 3;
  static final int MAX_TIME = 7;
  private static final int MIN_BAGS = 2;
  private static final int MAX_BAGS = 5;
  private final int number;
  private int bags;

  Customer(int number) {
    this.number = number;
    this.bags = ThreadLocalRandom.current().nextInt(MIN_BAGS, MAX_BAGS + 1);
  }

  int getNumber() {
    return this.number;
  }

  int getBags() {
    return bags;
  }

  void decreadeBags() {
    bags--;
  }
}
