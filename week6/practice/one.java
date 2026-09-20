import java.util.*;

class EventTicket {
    protected String attendeeId;
    protected double basePrice;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().isEmpty()
                || attendeeId.length() < 4) {
            throw new IllegalArgumentException();
        }

        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
    }

    public void getBalanceDue() {
        System.out.println(basePrice);
    }
}

class WorkshopTicket extends EventTicket {
    private String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public void pay(double amount) {
        basePrice -= amount;
    }

    public void getBalanceDue() {
        System.out.println(basePrice);
    }

    public static void registerBatch(String[] attendeeIds, double basePrice) {
        int registered = 0;
        int rejected = 0;

        for (String id : attendeeIds) {
            try {
                new EventTicket(id, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Registered: " + registered + " | Rejected: " + rejected);
    }
}

public class one {
    public static void main(String[] args) {

        // Example 1
        try {
            EventTicket e = new EventTicket("ST1", 500);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        // Example 2
        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        w.getBalanceDue();

        // Example 3
        String[] ids = {"STU1", "STU1", "STU2", " ", "STU3"};
        WorkshopTicket.registerBatch(ids, 500);
    }
}