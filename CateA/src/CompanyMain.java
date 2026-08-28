import java.util.Scanner;

class CompanyEmployee {

    String name;
    int empId;
    double salary;

    CompanyEmployee(String name, int empId, double salary) {
        this.name = name;
        this.empId = empId;
        this.salary = salary;
    }

    double getCompanySalary() {
        return salary;
    }
}

class CompanyManager extends CompanyEmployee {

    double bonus;

    CompanyManager(
            String name,
            int empId,
            double salary,
            double bonus
    ) {
        super(name, empId, salary);
        this.bonus = bonus;
    }

    double getEffectiveSalary() {
        return salary + bonus;
    }
}

class CompanyParking {

    String slotNo;
    int capacity;
    int occupied;

    CompanyParking(
            String slotNo,
            int capacity,
            int occupied
    ) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupied = occupied;
    }

    boolean checkAvailable() {
        return occupied < capacity;
    }

    void assignParking() {
        occupied++;
    }
}

class CompanyRecord {

    String name;
    int empId;

    CompanyEmployee employee;
    CompanyParking parking;

    static int totalRecords = 0;

    CompanyRecord(
            String name,
            int empId,
            CompanyEmployee employee
    ) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;

        totalRecords++;
    }

    void showProfile() {

        double pay;

        if (employee instanceof CompanyManager) {

            CompanyManager manager =
                    (CompanyManager) employee;

            pay = manager.getEffectiveSalary();

        } else {

            pay = employee.getCompanySalary();
        }

        if (parking == null) {

            System.out.println(
                    name +
                            " | Pay: Rs " + pay +
                            " | Parking: no parking assigned"
            );

        } else {

            System.out.println(
                    name +
                            " | Pay: Rs " + pay +
                            " | Parking: " +
                            parking.slotNo
            );
        }
    }
}

public class CompanyMain {

    static CompanyParking findFreeParking(
            CompanyParking[] parkingSlots
    ) {

        for (int i = 0; i < parkingSlots.length; i++) {

            if (parkingSlots[i].checkAvailable()) {
                return parkingSlots[i];
            }
        }

        return null;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Parking details

        CompanyParking[] parkingSlots =
                new CompanyParking[2];

        System.out.println("Enter Parking Details");

        for (int i = 0; i < 2; i++) {

            System.out.println(
                    "\nParking Slot " + (i + 1)
            );

            System.out.print("Slot Number: ");
            String slotNo = sc.next();

            System.out.print("Capacity: ");
            int capacity = sc.nextInt();

            System.out.print("Occupied: ");
            int occupied = sc.nextInt();

            parkingSlots[i] =
                    new CompanyParking(
                            slotNo,
                            capacity,
                            occupied
                    );
        }

        // Three employees

        CompanyRecord[] records =
                new CompanyRecord[3];


        // Employee 1

        System.out.println("\nEmployee 1");

        System.out.print("Name: ");
        String name1 = sc.next();

        System.out.print("ID: ");
        int id1 = sc.nextInt();

        System.out.print("Salary: ");
        double salary1 = sc.nextDouble();

        CompanyEmployee employee1 =
                new CompanyEmployee(
                        name1, id1, salary1
                );

        records[0] =
                new CompanyRecord(
                        name1, id1, employee1
                );


        // Employee 2 - Manager

        System.out.println("\nEmployee 2 - Manager");

        System.out.print("Name: ");
        String name2 = sc.next();

        System.out.print("ID: ");
        int id2 = sc.nextInt();

        System.out.print("Salary: ");
        double salary2 = sc.nextDouble();

        System.out.print("Bonus: ");
        double bonus = sc.nextDouble();

        CompanyManager manager =
                new CompanyManager(
                        name2,
                        id2,
                        salary2,
                        bonus
                );

        records[1] =
                new CompanyRecord(
                        name2, id2, manager
                );


        // Employee 3

        System.out.println("\nEmployee 3");

        System.out.print("Name: ");
        String name3 = sc.next();

        System.out.print("ID: ");
        int id3 = sc.nextInt();

        System.out.print("Salary: ");
        double salary3 = sc.nextDouble();

        CompanyEmployee employee3 =
                new CompanyEmployee(
                        name3, id3, salary3
                );

        records[2] =
                new CompanyRecord(
                        name3, id3, employee3
                );


        // Give parking to first two employees

        for (int i = 0; i < 2; i++) {

            CompanyParking freeParking =
                    findFreeParking(parkingSlots);

            if (freeParking != null) {

                freeParking.assignParking();

                records[i].parking = freeParking;
            }
        }


        // Display profiles

        System.out.println(
                "\n----- Employee Profiles -----"
        );

        for (int i = 0; i < 3; i++) {

            records[i].showProfile();
        }

        System.out.println(
                "Total records: " +
                        CompanyRecord.totalRecords
        );

        sc.close();
    }
}