import java.util.Scanner;

class BookIssue {

    String title;
    String borrowerName;
    int daysOverdue;

    BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    double fineAmount() {
        if (daysOverdue > 0) {
            return daysOverdue * 5;
        }
        return 0;
    }

    boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    // Static because it calculates fine for many books.
    static double totalFineCollected(BookIssue[] books) {

        double total = 0;

        for (int i = 0; i < books.length; i++) {
            total = total + books[i].fineAmount();
        }

        return total;
    }
}

public class LibraryFineMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookIssue[] books = new BookIssue[5];

        for (int i = 0; i < 5; i++) {

            System.out.println("\nEnter Book " + (i + 1));

            System.out.print("Book Title: ");
            String title = sc.nextLine();

            System.out.print("Borrower Name: ");
            String borrower = sc.nextLine();

            System.out.print("Days Overdue: ");
            int days = sc.nextInt();
            sc.nextLine();

            books[i] = new BookIssue(title, borrower, days);
        }

        System.out.println("\n----- Book Details -----");

        for (int i = 0; i < 5; i++) {

            System.out.print(
                    books[i].title + " - " +
                            books[i].daysOverdue + " days - "
            );

            if (books[i].isSeverelyOverdue()) {
                System.out.println("Severely overdue");
            } else {
                System.out.println("OK");
            }
        }

        double total =
                BookIssue.totalFineCollected(books);

        System.out.println(
                "Total fine collected: Rs " + total
        );

        sc.close();
    }
}