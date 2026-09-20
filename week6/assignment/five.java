import java.util.*;

class RaceEntry {
    protected String bibNumber;
    protected double entryFee;

    private final String entryCode;
    private static int bibCounter = 0;
    private String paymentMode;

    public RaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;

        bibCounter++;

        int number = (bibCounter - 1) % 1000 + 1;
        char letter = (char) ('A' + (bibCounter - 1) / 1000);

        entryCode = "M" + String.format("%03d", number) + letter;
    }

    public void pay(double amount) {
        entryFee -= amount;
    }

    public void pay(double amount, String mode) {
        entryFee -= amount;
        paymentMode = mode;
        System.out.println("Paying via " + mode);
    }

    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }

        if (code.charAt(0) != 'M') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1))
                || !Character.isDigit(code.charAt(2))
                || !Character.isDigit(code.charAt(3))) {
            return false;
        }

        return Character.isUpperCase(code.charAt(4));
    }

    public static int getBibCounter() {
        return bibCounter;
    }

    public String getEntryCode() {
        return entryCode;
    }
}

class RunnerEntry extends RaceEntry {
    protected String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }
}

class EliteRunnerEntry extends RunnerEntry {
    private double sponsorBonus;

    public EliteRunnerEntry(String bibNumber, double entryFee,
                            String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }
}

class RelayTeamEntry extends RaceEntry {
    private int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }
}

public class five {

    static String settleNight(RaceEntry[] entries) {
        int processed = 0;
        int skipped = 0;
        int relay = 0;
        int individual = 0;

        for (RaceEntry entry : entries) {
            if (entry == null) {
                skipped++;
                continue;
            }

            processed++;

            if (entry instanceof RelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
        }

        return processed + " processed | "
                + skipped + " null skipped | "
                + relay + " relay | "
                + individual + " individual";
    }

    public static void main(String[] args) {

        System.out.println(
            RaceEntry.isValidDiscountCode("M123A")
        );

        System.out.println(
            RaceEntry.isValidDiscountCode("M12A")
        );

        System.out.println(
            RaceEntry.isValidDiscountCode("X123A")
        );

        RaceEntry r = new RaceEntry("BIB1001", 100);

        r.pay(10, "UPI");

        EliteRunnerEntry eliteEntry =
                new EliteRunnerEntry("BIB3001", 150,
                        "Elite Full Marathon", 500);

        RelayTeamEntry relayEntry =
                new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] entries = {
            eliteEntry,
            null,
            relayEntry
        };

        System.out.println(settleNight(entries));

        System.out.println(RaceEntry.getBibCounter());
    }
}