import java.util.*;

abstract class Vehicle {
    protected String vehicleId;
    protected String vehicleName;
    protected boolean available;

    public Vehicle(String vehicleId, String vehicleName) {
        this.vehicleId = vehicleId;
        this.vehicleName = vehicleName;
        this.available = true;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract double calculateCharge(int days);
}

class StandardCar extends Vehicle {

    public StandardCar(String vehicleId, String vehicleName) {
        super(vehicleId, vehicleName);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 50.0;
    }
}

class LuxuryCar extends Vehicle {

    public LuxuryCar(String vehicleId, String vehicleName) {
        super(vehicleId, vehicleName);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 100.0;
    }
}

class SUV extends Vehicle {

    public SUV(String vehicleId, String vehicleName) {
        super(vehicleId, vehicleName);
    }

    @Override
    public double calculateCharge(int days) {
        return days * 75.0;
    }
}

class Customer {
    private int customerId;
    private String name;

    public Customer(int customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Customer customer;
    private Vehicle vehicle;
    private int days;
    private double totalCharge;
    private boolean active;

    public Rental(Customer customer, Vehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
        this.totalCharge = vehicle.calculateCharge(days);
        this.active = true;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public boolean isActive() {
        return active;
    }

    public void closeRental() {
        active = false;
    }

    public int getDays() {
        return days;
    }
}

class RentalService {
    private List<Vehicle> vehicles;
    private List<Rental> rentals;

    public RentalService() {
        vehicles = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public Rental rentVehicle(Customer customer, Vehicle vehicle, int days) {

        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getVehicleName() +
                    " is currently unavailable.");
            return null;
        }

        Rental rental = new Rental(customer, vehicle, days);

        vehicle.setAvailable(false);
        rentals.add(rental);

        System.out.printf("%s rented for %d days. Total charge: $%.2f%n",
                vehicle.getVehicleName(),
                days,
                rental.getTotalCharge());

        return rental;
    }

    public void returnVehicle(Rental rental) {

        if (rental == null || !rental.isActive()) {
            System.out.println("Invalid or already completed rental.");
            return;
        }

        Vehicle vehicle = rental.getVehicle();

        rental.closeRental();
        vehicle.setAvailable(true);

        System.out.println(vehicle.getVehicleName() +
                " returned. Now available.");
    }
}

public class two{
    public static void main(String[] args) {

        RentalService service = new RentalService();

        Customer customer = new Customer(101, "John");

        Vehicle luxuryCar =
                new LuxuryCar("L001", "Luxury Car A");

        Vehicle standardCar =
                new StandardCar("S001", "Standard Car B");

        service.addVehicle(luxuryCar);
        service.addVehicle(standardCar);

        Rental luxuryRental =
                service.rentVehicle(customer, luxuryCar, 3);

        Rental standardRental =
                service.rentVehicle(customer, standardCar, 5);

        service.returnVehicle(luxuryRental);
    }
}