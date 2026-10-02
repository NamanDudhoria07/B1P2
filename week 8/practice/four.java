import java.util.*;
import java.time.*;

abstract class Employee {
    protected int employeeId;
    protected String name;

    public Employee(int employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract boolean isLeaveAllowed(LocalDate startDate, LocalDate endDate);
}

class FullTimeEmployee extends Employee {

    public FullTimeEmployee(int employeeId, String name) {
        super(employeeId, name);
    }

    @Override
    public boolean isLeaveAllowed(LocalDate startDate, LocalDate endDate) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(
                startDate, endDate) + 1;

        return days <= 30;
    }
}

class PartTimeEmployee extends Employee {

    public PartTimeEmployee(int employeeId, String name) {
        super(employeeId, name);
    }

    @Override
    public boolean isLeaveAllowed(LocalDate startDate, LocalDate endDate) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(
                startDate, endDate) + 1;

        return days <= 15;
    }
}

class ContractEmployee extends Employee {

    public ContractEmployee(int employeeId, String name) {
        super(employeeId, name);
    }

    @Override
    public boolean isLeaveAllowed(LocalDate startDate, LocalDate endDate) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(
                startDate, endDate) + 1;

        return days <= 10;
    }
}

class LeaveRequest {
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;

    public LeaveRequest(Employee employee,
                        LocalDate startDate,
                        LocalDate endDate) {

        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = "Pending";
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getStatus() {
        return status;
    }

    public void approve() {
        if (status.equals("Pending")) {
            status = "Approved";

            System.out.println(
                "Leave request for " +
                employee.getName() +
                " approved. Status: Approved."
            );
        } else {
            System.out.println(
                "Cannot approve: request is already " +
                status + "."
            );
        }
    }

    public void reject() {
        if (status.equals("Pending")) {
            status = "Rejected";

            System.out.println(
                "Leave request for " +
                employee.getName() +
                " rejected. Status: Rejected."
            );
        } else {
            System.out.println(
                "Cannot reject: " +
                status +
                " request cannot change."
            );
        }
    }

    public void changeToPending() {
        if (!status.equals("Pending")) {
            System.out.println(
                "Cannot change status: " +
                status +
                " request cannot revert to Pending."
            );
        }
    }
}

class ApprovalService {

    public LeaveRequest submitRequest(Employee employee,
                                      LocalDate startDate,
                                      LocalDate endDate) {

        if (!employee.isLeaveAllowed(startDate, endDate)) {
            System.out.println(
                "Leave request rejected due to employee leave policy."
            );
            return null;
        }

        LeaveRequest request =
                new LeaveRequest(employee, startDate, endDate);

        System.out.println(
            "Leave request submitted by " +
            employee.getName() +
            " for " +
            startDate +
            " to " +
            endDate +
            ". Status: Pending."
        );

        return request;
    }

    public void approveRequest(LeaveRequest request) {
        if (request != null) {
            request.approve();
        }
    }

    public void rejectRequest(LeaveRequest request) {
        if (request != null) {
            request.reject();
        }
    }

    public void changeToPending(LeaveRequest request) {
        if (request != null) {
            request.changeToPending();
        }
    }
}

public class four {

    public static void main(String[] args) {

        ApprovalService service = new ApprovalService();

        Employee john =
                new FullTimeEmployee(101, "John Doe");

        LeaveRequest johnRequest =
                service.submitRequest(
                    john,
                    LocalDate.of(2024, 10, 10),
                    LocalDate.of(2024, 10, 12)
                );

        service.approveRequest(johnRequest);

        Employee jane =
                new PartTimeEmployee(102, "Jane Smith");

        LeaveRequest janeRequest =
                service.submitRequest(
                    jane,
                    LocalDate.of(2024, 11, 1),
                    LocalDate.of(2024, 11, 5)
                );

        service.changeToPending(johnRequest);
    }
}