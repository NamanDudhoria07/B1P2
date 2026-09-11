import java.util.Arrays;

class FareSplitter {
    String id;
    double fare;
    int count;

    public FareSplitter(String id, double fare, int count) {
        if (fare < 0 || count <= 0)
            throw new IllegalArgumentException();
        this.id = id;
        this.fare = fare;
        this.count = count;
    }

    public FareSplitter(String id, double fare) {
        this(id, fare, 1);
    }

    public FareSplitter(String id) {
        this(id, 0, 1);
    }

    public double[] fareBreakdown() {
        double[] a = new double[count];
        double x = Math.floor(fare / count * 100) / 100;

        for (int i = 0; i < count; i++)
            a[i] = x;

        a[count - 1] = Math.round((fare - x * (count - 1)) * 100) / 100.0;
        return a;
    }

    boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }
}

public class Remainder_fair_FareSplitter{
    public static void main(String[] args) {

        FareSplitter f1 = new FareSplitter("TRIP001", 100000, 3);
        System.out.println(Arrays.toString(f1.fareBreakdown()));

        FareSplitter f2 = new FareSplitter("TRIP003");
        System.out.println(Arrays.toString(f2.fareBreakdown()));

        System.out.println(f1.isConfirmationOverdue(2, 3));
    }
}