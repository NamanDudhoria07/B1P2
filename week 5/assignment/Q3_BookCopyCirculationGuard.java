import java.util.*;
public class Q3_BookCopyCirculationGuard {

    private int copiesTotal;
    private int copiesAvailable;

    public Q3_BookCopyCirculationGuard(int copiesTotal) {

        if (copiesTotal <= 0) {
            throw new IllegalArgumentException(
                "copiesTotal must be positive"
            );
        }

        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    public void checkOut() {

        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }

    public void checkIn() {

        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public static void main(String[] args) {

        // Sample 1
        try {

            new Q3_BookCopyCirculationGuard(0);

            System.out.println("construction succeeded");

        } catch (IllegalArgumentException e) {

            System.out.println("construction rejected");
        }

        // Sample 2
        Q3_BookCopyCirculationGuard b = new Q3_BookCopyCirculationGuard(3);

        b.checkOut();
        b.checkOut();
        b.checkOut();
        b.checkOut();

        System.out.println(
            b.getCopiesAvailable()
        );

        // Sample 3
        b.checkIn();
        b.checkIn();
        b.checkIn();
        b.checkIn();

        System.out.println(
            b.getCopiesAvailable()
        );
    }
}