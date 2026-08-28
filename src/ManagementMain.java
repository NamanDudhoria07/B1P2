import java.util.Scanner;

class FeeAccounts {

    double totalFee;
    double amountPaid;

    FeeAccounts(double totalFee, double amountPaid) {
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    void pay(double amount) {
        if (amount > 0) {
            amountPaid = amountPaid + amount;
        } else {
            System.out.println("Payment rejected");
        }
    }

    double getDue() {
        return totalFee - amountPaid;
    }
}

class HostelFeeAccounts extends FeeAccounts {

    HostelFeeAccounts(double totalFee, double amountPaid) {
        super(totalFee, amountPaid);
    }
}

class HostelRooms {

    String roomNo;
    int beds;
    int occupied;

    HostelRooms(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    void allot(String name) {

        if (occupied < beds) {
            occupied++;

            System.out.println(
                    name + " allotted to room " + roomNo
            );
        }
    }
}

class SrmStudents {

    String name;
    String regNo;

    HostelFeeAccounts feeAccount;   // FIXED
    HostelRooms room;               // FIXED

    static int totalStudents = 0;

    SrmStudents(
            String name,
            String regNo,
            HostelFeeAccounts feeAccount
    ) {

        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;

        totalStudents++;
    }

    String fullStatus() {

        if (room == null) {

            return name +
                    " | Due: Rs " +
                    feeAccount.getDue() +
                    " | Room: unallotted";

        } else {

            return name +
                    " | Due: Rs " +
                    feeAccount.getDue() +
                    " | Room: " +
                    room.roomNo;
        }
    }
}

public class ManagementMain {

    static HostelRooms findAvailableRoom(
            HostelRooms[] rooms
    ) {

        for (int i = 0; i < rooms.length; i++) {

            if (rooms[i].occupied < rooms[i].beds) {
                return rooms[i];
            }
        }

        return null;
    }

    static void safeAllot(
            HostelRooms[] rooms,
            SrmStudents student
    ) {

        HostelRooms room = findAvailableRoom(rooms);

        if (room != null) {

            room.allot(student.name);
            student.room = room;

        } else {

            System.out.println(
                    "No rooms available for " + student.name
            );
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Create rooms
        HostelRooms[] rooms = new HostelRooms[2];

        for (int i = 0; i < 2; i++) {

            System.out.println(
                    "\nEnter Room " + (i + 1) + " details:"
            );

            System.out.print("Room Number: ");
            String roomNo = sc.next();

            System.out.print("Beds: ");
            int beds = sc.nextInt();

            System.out.print("Occupied: ");
            int occupied = sc.nextInt();

            rooms[i] = new HostelRooms(
                    roomNo, beds, occupied
            );
        }

        // Create students
        SrmStudents[] students = new SrmStudents[3];

        for (int i = 0; i < 3; i++) {

            System.out.println(
                    "\nEnter Student " + (i + 1) + " details:"
            );

            System.out.print("Name: ");
            String name = sc.next();

            System.out.print("Register Number: ");
            String regNo = sc.next();

            System.out.print("Total Fee: ");
            double totalFee = sc.nextDouble();

            System.out.print("Amount Paid: ");
            double amountPaid = sc.nextDouble();

            HostelFeeAccounts fee =
                    new HostelFeeAccounts(totalFee, amountPaid);

            students[i] =
                    new SrmStudents(name, regNo, fee);
        }

        // Allot rooms
        safeAllot(rooms, students[0]);
        safeAllot(rooms, students[1]);

        // Payment
        System.out.print(
                "\nEnter payment for " +
                        students[0].name + ": "
        );

        double payment = sc.nextDouble();

        students[0].feeAccount.pay(payment);

        // Display status
        System.out.println("\n----- Student Status -----");

        for (int i = 0; i < 3; i++) {
            System.out.println(
                    students[i].fullStatus()
            );
        }

        System.out.println(
                "Total students: " +
                        SrmStudents.totalStudents
        );

        sc.close();
    }
}