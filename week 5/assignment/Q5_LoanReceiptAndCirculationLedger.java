import java.util.*;

 class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    // Static block
    static {
        // One-time shared initialization
    }

    public LoanReceipt(String memberId, String[] bookIds) {

        if (bookIds == null) {
            throw new IllegalArgumentException(
                "bookIds cannot be null"
            );
        }

        // Validate every book ID
        for (String id : bookIds) {

            if (id == null || !id.matches("BK-\\d{3}")) {

                throw new IllegalArgumentException(
                    "Invalid book ID"
                );
            }
        }

        this.memberId = memberId;

        // Defensive copy
        this.bookIds = bookIds.clone();
    }

    // Getter with defensive copy
    public String[] getBookIds() {
        return bookIds.clone();
    }

    // Create a corrected copy
    public LoanReceipt withCorrectedBookId(
            int index,
            String newId) {

        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException(
                "Invalid index"
            );
        }

        if (newId == null ||
            !newId.matches("BK-\\d{3}")) {

            throw new IllegalArgumentException(
                "Invalid book ID"
            );
        }

        String[] newBookIds = bookIds.clone();

        newBookIds[index] = newId;

        return new LoanReceipt(
            memberId,
            newBookIds
        );
    }
}


// Reference-only subclass
class ReferenceOnlyLoanReceipt extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(
            String memberId,
            String[] bookIds,
            String roomNumber) {

        super(memberId, bookIds);

        this.roomNumber = roomNumber;
    }
}


// Main class
public class Q5_LoanReceiptAndCirculationLedger {

    static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        if (receipts == null) {

            return "0 processed | 0 null skipped | " +
                   "0 reference-only | 0 regular";
        }

        for (LoanReceipt receipt : receipts) {

            // Null entries are skipped
            if (receipt == null) {

                nullSkipped++;
                continue;
            }

            processed++;

            // instanceof check
            if (receipt instanceof ReferenceOnlyLoanReceipt) {

                referenceOnly++;

            } else {

                regular++;
            }
        }

        return processed + " processed | " +
               nullSkipped + " null skipped | " +
               referenceOnly + " reference-only | " +
               regular + " regular";
    }


    // Main method
    public static void main(String[] args) {

        // Sample 1
        try {

            new LoanReceipt(
                "LIB-8841",
                new String[]{
                    "BK-100",
                    "bad"
                }
            );

            System.out.println(
                "construction succeeded"
            );

        } catch (IllegalArgumentException e) {

            System.out.println(
                "construction rejected"
            );
        }


        // Sample 2
        LoanReceipt r =
            new LoanReceipt(
                "LIB-8841",
                new String[]{
                    "BK-100",
                    "BK-101"
                }
            );

        String[] ids = r.getBookIds();

        ids[0] = "HACKED";

        System.out.println(
            r.getBookIds()[0]
        );


        // Sample 3
        LoanReceipt[] receipts = {

            new ReferenceOnlyLoanReceipt(
                "LIB-001",
                new String[]{
                    "BK-200"
                },
                "Reading Room 3"
            ),

            null,

            new LoanReceipt(
                "LIB-002",
                new String[]{
                    "BK-201"
                }
            )
        };

        System.out.println(
            processNightlyCirculation(receipts)
        );
    }
}