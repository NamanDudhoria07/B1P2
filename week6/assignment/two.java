import java.util.*;

class RaceEntry {
    protected String bibNumber;
    protected double entryFee;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty()
                || bibNumber.length() < 4) {
            throw new IllegalArgumentException();
        }

        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
    }

    public double getBalanceDue() {
        return entryFee;
    }

    public void announce() {
        System.out.println("Race Entry | Bib: " + bibNumber
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
        System.out.println("Runner Entry | Bib: " + bibNumber
                + " | Category: " + category
                + " | Balance: " + entryFee);
    }
}

class EliteRunnerEntry extends RunnerEntry {
    private double sponsorBonus;

    public EliteRunnerEntry(String bibNumber, double entryFee,
                            String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }

    @Override
    public void announce() {
        System.out.println("Elite Runner | Bib: " + bibNumber
                + " | Category: " + category
                + " | Sponsor Bonus: " + sponsorBonus
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
        System.out.println("Relay Team | Bib: " + bibNumber
                + " | Team Size: " + teamSize
                + " | Balance: " + entryFee);
    }
}

public class two {

    static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        }

        if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        }

        return "Base generation";
    }

    static double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0;

        for (RaceEntry entry : entries) {
            total += entry.getBalanceDue();
        }

        return total;
    }

    public static void main(String[] args) {

        RunnerEntry runnerEntry =
                new RunnerEntry("BIB2001", 80, "Open 10K");

        EliteRunnerEntry eliteEntry =
                new EliteRunnerEntry("BIB3001", 150,
                        "Elite Full Marathon", 500);

        RelayTeamEntry relayEntry =
                new RelayTeamEntry("BIB4001", 300, 4);

        runnerEntry.announce();
        eliteEntry.announce();
        relayEntry.announce();

        System.out.println();

        System.out.println(classifyGeneration(eliteEntry));
        System.out.println(classifyGeneration(relayEntry));

        System.out.println();

        RaceEntry[] entries = {
            runnerEntry,
            eliteEntry,
            relayEntry
        };

        System.out.println(getTotalBalanceDue(entries));
    }
}