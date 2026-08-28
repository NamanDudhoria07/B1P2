import java.util.Scanner;

class SrmStudent {
    String name;
    String regNo;
    int attendance;

    SrmStudent(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    void addAttendanceUpdate(int newAttendance) {
        attendance = newAttendance;
    }

    boolean isEligible() {
        if (attendance >= 75)
            return true;
        else
            return false;
    }

    static double classAverage(SrmStudent[] students) {
        int sum = 0;

        for (int i = 0; i < students.length; i++) {
            sum = sum + students[i].attendance;
        }

        return (double) sum / students.length;
    }
}

public class AttendanceMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        SrmStudent[] students = new SrmStudent[5];

        for (int i = 0; i < 5; i++) {
            System.out.println("Enter student " + (i + 1) + " details:");

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Register Number: ");
            String regNo = sc.nextLine();

            System.out.print("Attendance: ");
            int attendance = sc.nextInt();
            sc.nextLine();

            students[i] = new SrmStudent(name, regNo, attendance);
        }

        System.out.println("\nStudent Details:");

        for (int i = 0; i < 5; i++) {

            if (students[i].isEligible()) {
                System.out.println(
                        students[i].name + " - " +
                                students[i].attendance + "% - Eligible"
                );
            } else {
                System.out.println(
                        students[i].name + " - " +
                                students[i].attendance + "% - Detained"
                );
            }
        }

        System.out.println(
                "Class Average: " +
                        SrmStudent.classAverage(students) + "%"
        );

        sc.close();
    }
}