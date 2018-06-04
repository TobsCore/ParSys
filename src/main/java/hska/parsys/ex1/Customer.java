package hska.parsys.ex1;

import java.util.concurrent.ThreadLocalRandom;

class Customer implements Comparable<Customer> {
  static final int MIN_TIME = 3;
  static final int MAX_TIME = 7;
  private static final int MIN_BAGS = 2;
  private static final int MAX_BAGS = 5;
  private final int number;
  private int bags;
  private boolean isPremiumMember;

  Customer(int number) {
    this.number = number;
    this.bags = ThreadLocalRandom.current().nextInt(MIN_BAGS, MAX_BAGS + 1);
    this.isPremiumMember = ThreadLocalRandom.current().nextInt(0, 100) < 30;
  }

  int getNumber() {
    return this.number;
  }

  int getBags() {
    return bags;
  }

  void decrementBags() {
    bags--;
  }

  public String toString() {
    String premium = isPremiumMember ? " and prime Member" : "";
    return "Customer #" + this.number + " (" + this.bags + " bags" + premium + ")";
  }

    @Override
    public int compareTo(Customer o) {
        return isPremiumMember ? -1 : 1;
    }
}
