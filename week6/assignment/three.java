import java.util.*;

class RaceEntry {
    protected String bibNumber;
    protected double entryFee;

    private double[] lateFeeHistory;
    private int historyCount;

    public RaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        lateFeeHistory = new double[10];
        historyCount = 0;
    }

    public void pay(double amount) {
        entryFee -= amount;
    }

    protected void applyLateFee(double amount) {
        entryFee += amount;

        if (historyCount < lateFeeHistory.length) {
            lateFeeHistory[historyCount] = amount;
            historyCount++;
        }
    }

    public double getBalanceDue() {
        return entryFee;
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, historyCount);
    }
}

class RunnerEntry extends RaceEntry {
    private String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class three {
    public static void main(String[] args) {

        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");

        r.pay(30);
        r.applyLateFee(20);

        System.out.println(r.getBalanceDue());

        double[] history = r.getLateFeeHistory();

        System.out.println(Arrays.toString(history));

        history[0] = 999;

        System.out.println(Arrays.toString(r.getLateFeeHistory()));
    }
}