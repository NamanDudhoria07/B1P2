import java.util.Scanner;

// Wrong version
class WrongLibraryMember {

    static String name;
    static String memberId;
    static int booksIssued;

    WrongLibraryMember(
            String name,
            String memberId,
            int booksIssued
    ) {

        WrongLibraryMember.name = name;
        WrongLibraryMember.memberId = memberId;
        WrongLibraryMember.booksIssued = booksIssued;
    }
}


// Correct version
class LibraryMember {

    String name;
    String memberId;
    int booksIssued;

    static String libraryName = "SRM Library";
    static int memberCount = 0;

    LibraryMember(String name, int booksIssued) {

        this.name = name;
        this.booksIssued = booksIssued;

        memberCount++;

        memberId = "LM-" + (1000 + memberCount);
    }

    void printMemberCard() {

        System.out.println(
                name + " | " +
                        memberId + " | Books: " +
                        booksIssued
        );
    }

    static void printTotalMembers() {

        System.out.println(
                "Total members: " +
                        memberCount
        );
    }
}

public class LibraryMemberMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Demonstrating wrong static version

        System.out.println("----- Broken Version -----");

        System.out.print("Enter first member name: ");
        String name1 = sc.next();

        System.out.print("Enter first member ID: ");
        String id1 = sc.next();

        System.out.print("Books issued: ");
        int books1 = sc.nextInt();

        WrongLibraryMember m1 =
                new WrongLibraryMember(
                        name1, id1, books1
                );


        System.out.print("\nEnter second member name: ");
        String name2 = sc.next();

        System.out.print("Enter second member ID: ");
        String id2 = sc.next();

        System.out.print("Books issued: ");
        int books2 = sc.nextInt();

        WrongLibraryMember m2 =
                new WrongLibraryMember(
                        name2, id2, books2
                );

        System.out.println(
                "\nFirst member name: " +
                        WrongLibraryMember.name
        );

        System.out.println(
                "Second member name: " +
                        WrongLibraryMember.name
        );

        System.out.println(
                "\nBoth show the second name because " +
                        "static variables are shared."
        );


        // Correct version

        System.out.println(
                "\n----- Fixed Version -----"
        );

        LibraryMember member1 =
                new LibraryMember(name1, books1);

        LibraryMember member2 =
                new LibraryMember(name2, books2);

        member1.printMemberCard();
        member2.printMemberCard();

        LibraryMember.printTotalMembers();

        sc.close();
    }
}