import java.util.*;

class EventTicket {
    protected double basePrice;
    private double[] lateFeeHistory;
    private int historyCount;

    public EventTicket(double basePrice) {
        this.basePrice = basePrice;
        lateFeeHistory = new double[10];
        historyCount = 0;
    }

    public void pay(double amount) {
        basePrice -= amount;
    }

    protected void applyLateFee(double amount) {
        basePrice += amount;

        if (historyCount < lateFeeHistory.length) {
            lateFeeHistory[historyCount] = amount;
            historyCount++;
        }
    }

    public double getBalanceDue() {
        return basePrice;
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, historyCount);
    }
}

class WorkshopTicket extends EventTicket {

    public WorkshopTicket(double basePrice) {
        super(basePrice);
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class three {
    public static void main(String[] args) {

        WorkshopTicket w = new WorkshopTicket(1200);

        w.pay(1200);
        w.applyLateFee(100);
        System.out.println(w.getBalanceDue());

        double[] history = w.getLateFeeHistory();
        System.out.println(Arrays.toString(history));

        history[0] = 999;

        System.out.println(Arrays.toString(w.getLateFeeHistory()));
    }
}