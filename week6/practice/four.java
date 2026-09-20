import java.util.*;

class EventTicket {
    protected double basePrice;

    public EventTicket(double basePrice) {
        this.basePrice = basePrice;
    }

    public String printTicket() {
        return "Standard | Balance: " + basePrice;
    }
}

class WorkshopTicket extends EventTicket {
    protected String track;

    public WorkshopTicket(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }

    @Override
    public String printTicket() {
        return "Workshop | Track: " + track + " | Balance: " + basePrice;
    }

    public String getTrack() {
        return track;
    }
}

public class four{

    static String batchPrint(EventTicket[] tickets) {
        StringBuilder sb = new StringBuilder();

        for (EventTicket ticket : tickets) {
            sb.append(ticket.printTicket());

            if (ticket instanceof WorkshopTicket) {
                WorkshopTicket w = (WorkshopTicket) ticket;
                sb.append(" [Track via downcast: " + w.getTrack() + "]");
            }

            sb.append(" | ");
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        EventTicket[] tickets = {
            new EventTicket(500),
            new WorkshopTicket(1200, "AI/ML")
        };

        System.out.println(batchPrint(tickets));

        EventTicket plain = new EventTicket(500);

        try {
            WorkshopTicket bad = (WorkshopTicket) plain;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}