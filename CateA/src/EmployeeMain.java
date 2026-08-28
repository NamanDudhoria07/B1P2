import java.util.Scanner;

class Employee {

    private int empId;
    private String empName;
    private double salary;

    Employee(int empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    double getSalary() {
        return salary;
    }

    String getName() {
        return empName;
    }
}

class ManagerEmployee extends Employee {

    private double teamBonus;

    ManagerEmployee(
            int empId,
            String name,
            double salary,
            double teamBonus
    ) {

        super(empId, name, salary);
        this.teamBonus = teamBonus;
    }

    double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {

    private double stipendCap;

    InternEmployee(
            int empId,
            String name,
            double salary,
            double stipendCap
    ) {

        super(empId, name, salary);
        this.stipendCap = stipendCap;
    }

    double effectiveSalary() {

        if (getSalary() < stipendCap) {
            return getSalary();
        }

        return stipendCap;
    }
}

public class EmployeeMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Plain Employee Details");

        System.out.print("ID: ");
        int id1 = sc.nextInt();

        System.out.print("Name: ");
        String name1 = sc.next();

        System.out.print("Salary: ");
        double salary1 = sc.nextDouble();

        Employee e1 =
                new Employee(id1, name1, salary1);


        System.out.println("\nEnter Manager Details");

        System.out.print("ID: ");
        int id2 = sc.nextInt();

        System.out.print("Name: ");
        String name2 = sc.next();

        System.out.print("Salary: ");
        double salary2 = sc.nextDouble();

        System.out.print("Team Bonus: ");
        double bonus = sc.nextDouble();

        ManagerEmployee e2 =
                new ManagerEmployee(
                        id2, name2, salary2, bonus
                );


        System.out.println("\nEnter Intern Details");

        System.out.print("ID: ");
        int id3 = sc.nextInt();

        System.out.print("Name: ");
        String name3 = sc.next();

        System.out.print("Salary: ");
        double salary3 = sc.nextDouble();

        System.out.print("Stipend Cap: ");
        double cap = sc.nextDouble();

        InternEmployee e3 =
                new InternEmployee(
                        id3, name3, salary3, cap
                );


        System.out.println("\n----- Employee Pay -----");

        System.out.println(
                "Plain employee pay: Rs " +
                        e1.getSalary()
        );

        System.out.println(
                "Manager effective pay: Rs " +
                        e2.effectiveSalary()
        );

        System.out.println(
                "Intern effective pay: Rs " +
                        e3.effectiveSalary()
        );

        sc.close();
    }
}