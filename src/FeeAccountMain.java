import java.util.Scanner;

class FeeAccount {
    String regNo;
    double totalFee;
    double amountPaid;

    FeeAccount(String regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    void pay(double amount) {
        if (amount > 0) {
            amountPaid = amountPaid + amount;
        } else {
            System.out.println("Invalid payment");
        }
    }

    double getDue() {
        return totalFee - amountPaid;
    }
}

class HostelFeeAccount extends FeeAccount {

    HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }

    void payInTwoInstallments(double amount) {
        pay(amount);
        pay(amount);
    }
}

class ScholarshipFeeAccount extends FeeAccount {
    double scholarshipPercent;

    ScholarshipFeeAccount(String regNo, double totalFee,
                          double amountPaid, double scholarshipPercent) {

        super(regNo, totalFee, amountPaid);
        this.scholarshipPercent = scholarshipPercent;
    }

    double effectiveDue() {
        double due = getDue();

        return due - (due * scholarshipPercent / 100);
    }
}

public class FeeAccountMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Normal account
        System.out.println("Enter Plain Account details:");

        System.out.print("Register Number: ");
        String reg1 = sc.nextLine();

        System.out.print("Total Fee: ");
        double fee1 = sc.nextDouble();

        System.out.print("Amount Paid: ");
        double paid1 = sc.nextDouble();

        FeeAccount plain =
                new FeeAccount(reg1, fee1, paid1);


        // Hostel account
        System.out.println("\nEnter Hostel Account details:");

        System.out.print("Register Number: ");
        String reg2 = sc.next();

        System.out.print("Total Fee: ");
        double fee2 = sc.nextDouble();

        System.out.print("Amount Paid: ");
        double paid2 = sc.nextDouble();

        HostelFeeAccount hostel =
                new HostelFeeAccount(reg2, fee2, paid2);

        System.out.print("Enter installment amount: ");
        double installment = sc.nextDouble();

        hostel.payInTwoInstallments(installment);


        // Scholarship account
        System.out.println("\nEnter Scholarship Account details:");

        System.out.print("Register Number: ");
        String reg3 = sc.next();

        System.out.print("Total Fee: ");
        double fee3 = sc.nextDouble();

        System.out.print("Amount Paid: ");
        double paid3 = sc.nextDouble();

        System.out.print("Scholarship percentage: ");
        double scholarship = sc.nextDouble();

        ScholarshipFeeAccount scholar =
                new ScholarshipFeeAccount(
                        reg3, fee3, paid3, scholarship
                );


        System.out.println("\n----- Fee Details -----");

        System.out.println(
                "Plain Account Due: Rs " + plain.getDue()
        );

        System.out.println(
                "Hostel Account Due: Rs " + hostel.getDue()
        );

        System.out.println(
                "Scholarship Account Effective Due: Rs "
                        + scholar.effectiveDue()
        );

        sc.close();
    }
}