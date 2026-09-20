import java.util.*;

class RaceEntry {
    protected String bibNumber;
    protected double entryFee;

    public RaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }

    public void announce() {
        System.out.print("Race Entry | Bib: " + bibNumber
                + " | Balance: " + entryFee);
    }
}

class RunnerEntry extends RaceEntry {
    protected String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public void announce() {
        System.out.print("Runner Entry | Bib: " + bibNumber
                + " | Category: " + category
                + " | Balance: " + entryFee);
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    @Override
    public void announce() {
        System.out.print("Relay Team | Bib: " + bibNumber
                + " | Team Size: " + teamSize
                + " | Balance: " + entryFee);
    }

    public int getTeamSize() {
        return teamSize;
    }
}

public class four {

    static String announceAll(RaceEntry[] entries) {
        StringBuilder sb = new StringBuilder();

        for (RaceEntry entry : entries) {

            if (sb.length() > 0) {
                sb.append(" | ");
            }

            entry.announce();

            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry relay = (RelayTeamEntry) entry;
                sb.append(" | Team Size via downcast: "
                        + relay.getTeamSize());
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        RunnerEntry runnerEntry =
                new RunnerEntry("BIB2001", 90, "Open 10K");

        RelayTeamEntry relayEntry =
                new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] fleet = {
            runnerEntry,
            relayEntry
        };

        System.out.println(announceAll(fleet));

        // Example of an invalid downcast
        RaceEntry plain = new RaceEntry("BIB5001", 50);

        try {
            RelayTeamEntry bad = (RelayTeamEntry) plain;
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}