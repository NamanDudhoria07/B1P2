import java.util.*;

interface CreditPolicy {
    int getCreditLimit();
    String getTypeName();
}

class RegularPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 24;
    }

    public String getTypeName() {
        return "Regular";
    }
}

class HonorsPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 28;
    }

    public String getTypeName() {
        return "Honors";
    }
}

class ExchangePolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 20;
    }

    public String getTypeName() {
        return "Exchange";
    }
}

class Student {
    private String name;
    private int currentCredits;
    private CreditPolicy policy;
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student(String name, int currentCredits, CreditPolicy policy) {
        this.name = name;
        this.currentCredits = currentCredits;
        this.policy = policy;
    }

    public String getName() {
        return name;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public int getCreditLimit() {
        return policy.getCreditLimit();
    }

    public String getTypeName() {
        return policy.getTypeName();
    }

    public boolean canTake(int credits) {
        return currentCredits + credits <= getCreditLimit();
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        currentCredits += enrollment.getElective().getCredits();
    }

    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
        currentCredits -= enrollment.getElective().getCredits();
    }

    public boolean isEnrolledIn(Elective elective) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getElective() == elective) {
                return true;
            }
        }
        return false;
    }
}

class Enrollment {
    private Student student;
    private Elective elective;

    public Enrollment(Student student, Elective elective) {
        this.student = student;
        this.elective = elective;
    }

    public Student getStudent() {
        return student;
    }

    public Elective getElective() {
        return elective;
    }
}

class Elective {
    private String name;
    private int credits;
    private int capacity;

    private List<Enrollment> enrolledStudents = new ArrayList<>();
    private Queue<Student> waitlist = new LinkedList<>();

    public Elective(String name, int credits, int capacity) {
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    public boolean isEnrolled(Student student) {
        for (Enrollment enrollment : enrolledStudents) {
            if (enrollment.getStudent() == student) {
                return true;
            }
        }
        return false;
    }

    public boolean isWaitlisted(Student student) {
        return waitlist.contains(student);
    }

    public void enroll(Student student) {
        Enrollment enrollment = new Enrollment(student, this);
        enrolledStudents.add(enrollment);
        student.addEnrollment(enrollment);
    }

    public void addToWaitlist(Student student) {
        waitlist.offer(student);
    }

    public Student removeFirstWaitlisted() {
        return waitlist.poll();
    }

    public int getWaitlistPosition(Student student) {
        int position = 1;

        for (Student s : waitlist) {
            if (s == student) {
                return position;
            }
            position++;
        }

        return -1;
    }

    public Enrollment removeStudent(Student student) {
        Iterator<Enrollment> iterator = enrolledStudents.iterator();

        while (iterator.hasNext()) {
            Enrollment enrollment = iterator.next();

            if (enrollment.getStudent() == student) {
                iterator.remove();
                student.removeEnrollment(enrollment);
                return enrollment;
            }
        }

        return null;
    }
}

class EnrollmentService {

    public void enroll(Student student, Elective elective) {

        if (student.isEnrolledIn(elective) || elective.isEnrolled(student)) {
            System.out.println("Enrollment failed: " + student.getName()
                    + " is already enrolled in " + elective.getName() + ".");
            return;
        }

        if (elective.isWaitlisted(student)) {
            System.out.println("Enrollment failed: " + student.getName()
                    + " is already on the waitlist for " + elective.getName() + ".");
            return;
        }

        // Credit limit is checked BEFORE seat availability
        if (!student.canTake(elective.getCredits())) {
            System.out.println("Enrollment failed: " + student.getName()
                    + " would exceed the " + student.getTypeName()
                    + " credit limit (" + (student.getCurrentCredits()
                    + elective.getCredits()) + "/"
                    + student.getCreditLimit() + ").");
            return;
        }

        if (!elective.isFull()) {
            elective.enroll(student);

            System.out.println(student.getName() + " enrolled in "
                    + elective.getName() + " (credits: "
                    + student.getCurrentCredits() + "/"
                    + student.getCreditLimit() + ").");
        } else {
            System.out.println(elective.getName() + " is full.");

            elective.addToWaitlist(student);

            System.out.println(student.getName()
                    + " added to waitlist (position "
                    + elective.getWaitlistPosition(student) + ").");
        }
    }

    public void drop(Student student, Elective elective) {

        Enrollment enrollment = elective.removeStudent(student);

        if (enrollment == null) {
            System.out.println("Drop failed: " + student.getName()
                    + " is not enrolled in " + elective.getName() + ".");
            return;
        }

        System.out.println(student.getName() + " dropped "
                + elective.getName() + " (credits: "
                + student.getCurrentCredits() + "/"
                + student.getCreditLimit() + ").");

        promoteNext(elective);
    }

    private void promoteNext(Elective elective) {

        while (!elective.isFull()) {

            Student student = elective.removeFirstWaitlisted();

            if (student == null) {
                return;
            }

            // Credit limit is checked again during promotion
            if (student.canTake(elective.getCredits())) {

                elective.enroll(student);

                System.out.println(student.getName()
                        + " promoted from waitlist and enrolled in "
                        + elective.getName() + " (credits: "
                        + student.getCurrentCredits() + "/"
                        + student.getCreditLimit() + ").");

                return;

            } else {

                System.out.println(student.getName()
                        + " could not be promoted because the "
                        + student.getTypeName()
                        + " credit limit would be exceeded.");

                // Continue to the next eligible student
            }
        }
    }
}

public class four{
    public static void main(String[] args) {

        EnrollmentService service = new EnrollmentService();

        Elective cloudComputing =
                new Elective("Cloud Computing", 4, 2);

        Student asha =
                new Student("Asha", 20, new RegularPolicy());

        Student ravi =
                new Student("Ravi", 22, new HonorsPolicy());

        Student neha =
                new Student("Neha", 12, new ExchangePolicy());

        Student kiran =
                new Student("Kiran", 22, new RegularPolicy());

        service.enroll(asha, cloudComputing);
        service.enroll(ravi, cloudComputing);
        service.enroll(neha, cloudComputing);
        service.enroll(kiran, cloudComputing);

        service.drop(asha, cloudComputing);
    }
}