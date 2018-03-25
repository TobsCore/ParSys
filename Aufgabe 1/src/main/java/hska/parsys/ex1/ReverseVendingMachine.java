package hska.parsys.ex1;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReverseVendingMachine {
    private static Logger logger = LoggerFactory.getLogger(ReverseVendingMachine.class);
    private int machineID;
    private boolean inUse = false;

    ReverseVendingMachine(int machineID) {
        this.machineID = machineID;
    }

    public int getMachineID() {
        return machineID;
    }

    synchronized public boolean isInUse() {
        return inUse;
    }

    synchronized public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
}
