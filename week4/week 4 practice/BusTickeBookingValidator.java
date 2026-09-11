import java.util.*;

class BusTicket {
    String name, dest;
    boolean checked;

    public BusTicket(String n, String d) {
        if (n == null || d == null || !n.matches("[A-Za-z ]+")
                || d.trim().isEmpty())
            throw new IllegalArgumentException();
        name = n;
        dest = d;
    }

    void markCheckedIn() {
        if (checked) throw new IllegalStateException();
        checked = true;
    }

    static void processBatch(String[][] b) {
        Set<String> s = new HashSet<>();
        int v = 0, r = 0, d = 0;

        for (String[] x : b)
            try {
                BusTicket t = new BusTicket(x[0], x[1]);
                if (!s.add(t.name + "|" + t.dest)) d++;
                else v++;
            } catch (Exception e) {
                r++;
            }

        System.out.println("Valid: " + v + " | Rejected: " + r +
                           " | Duplicates skipped: " + d);
    }
}
public class BusTickeBookingValidator {
    public static void main(String[] args) {
        BusTicket.processBatch(new String[][] {
            {"Divya","Chennai"},
            {"","Bangalore"},
            {"Ravi123","Pune"},
            {"Divya","Chennai"},
            {"Divya","   "}
        });
    }
}