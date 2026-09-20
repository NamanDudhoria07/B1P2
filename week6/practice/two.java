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

    public void printTicket() {
        System.out.println("Standard Event Ticket | Balance Due: " + basePrice);
    }
}

class WorkshopTicket extends EventTicket {
    protected String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public void pay(double amount) {
        basePrice -= amount;
    }

    @Override
    public void getBalanceDue() {
        System.out.println(basePrice);
    }

    @Override
    public void printTicket() {
        System.out.println("Workshop Ticket | Track: " + track
                + " | Balance Due: " + basePrice);
    }
}

class PremiumWorkshopTicket extends WorkshopTicket {
    private double kitFee;

    public PremiumWorkshopTicket(String attendeeId, double basePrice,
                                 String track, double kitFee) {
        super(attendeeId, basePrice, track);
        this.kitFee = kitFee;
    }

    @Override
    public void printTicket() {
        System.out.println("Premium Workshop Ticket | Track: " + track
                + " | Kit Fee: " + kitFee
                + " | Balance Due: " + basePrice);
    }
}

class HackathonTicket extends EventTicket {
    private String teamName;

    public HackathonTicket(String attendeeId, double basePrice,
                           String teamName) {
        super(attendeeId, basePrice);
        this.teamName = teamName;
    }

    @Override
    public void printTicket() {
        System.out.println("Hackathon Ticket | Team: " + teamName
                + " | Balance Due: " + basePrice);
    }
}

public class two {

    static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        }

        if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        }

        return "Base generation";
    }

    static double getTotalBalanceDue(EventTicket[] tickets) {
        double total = 0;

        for (EventTicket ticket : tickets) {
            // Polymorphism: calls the object's own getBalanceDue()
            // without checking its actual type.
            ticket.getBalanceDue();
            total += ticket.basePrice;
        }

        return total;
    }

    public static void main(String[] args) {

        EventTicket standardTicket =
                new EventTicket("STU1", 500);

        WorkshopTicket workshopTicket =
                new WorkshopTicket("STU2", 1200, "AI/ML");

        PremiumWorkshopTicket premiumTicket =
                new PremiumWorkshopTicket("STU3", 2000,
                        "Cloud Native", 300);

        HackathonTicket hackathonTicket =
                new HackathonTicket("STU4", 800, "Byte Force");

        standardTicket.printTicket();
        workshopTicket.printTicket();
        premiumTicket.printTicket();
        hackathonTicket.printTicket();

        System.out.println();

        System.out.println(classifyGeneration(premiumTicket));
        System.out.println(classifyGeneration(hackathonTicket));

        System.out.println();

        EventTicket[] tickets = {
            standardTicket,
            workshopTicket,
            premiumTicket,
            hackathonTicket
        };

        System.out.println(getTotalBalanceDue(tickets));
    }
}