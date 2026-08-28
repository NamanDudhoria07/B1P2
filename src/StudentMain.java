import java.util.Scanner;

class Srm {

    String name;
    String regNo;
    int attendance;

    static String university = "SRM";
    static int admissionCount = 0;

    Srm(String name, int attendance) {

        this.name = name;
        this.attendance = attendance;

        admissionCount++;

        this.regNo =
                "RA2311003010" + admissionCount;
    }

    void printIdCard() {
        System.out.println(name + " | " + regNo);
    }

    static void printTotalAdmissions() {
        System.out.println(
                "Students admitted so far: "
                        + admissionCount
        );
    }
}

public class StudentMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Student 1:");

        System.out.print("Name: ");
        String name1 = sc.nextLine();

        System.out.print("Attendance: ");
        int attendance1 = sc.nextInt();
        sc.nextLine();

        Srm s1 =
                new Srm(name1, attendance1);


        System.out.println("\nEnter Student 2:");

        System.out.print("Name: ");
        String name2 = sc.nextLine();

        System.out.print("Attendance: ");
        int attendance2 = sc.nextInt();

        Srm s2 =
                new Srm(name2, attendance2);


        System.out.println("\nStudent Details:");

        s1.printIdCard();
        s2.printIdCard();

        Srm.printTotalAdmissions();

        sc.close();
    }
}