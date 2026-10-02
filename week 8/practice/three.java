import java.util.*;
import java.time.*;

abstract class Room {
    protected int roomNumber;
    protected String roomName;

    public Room(int roomNumber, String roomName) {
        this.roomNumber = roomNumber;
        this.roomName = roomName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getRoomName() {
        return roomName;
    }

    public abstract double calculatePrice(long days);
}

class StandardRoom extends Room {

    public StandardRoom(int roomNumber, String roomName) {
        super(roomNumber, roomName);
    }

    @Override
    public double calculatePrice(long days) {
        return days * 150.0;
    }
}

class DeluxeRoom extends Room {

    public DeluxeRoom(int roomNumber, String roomName) {
        super(roomNumber, roomName);
    }

    @Override
    public double calculatePrice(long days) {
        return days * 200.0;
    }
}

class SuiteRoom extends Room {

    public SuiteRoom(int roomNumber, String roomName) {
        super(roomNumber, roomName);
    }

    @Override
    public double calculatePrice(long days) {
        return days * 300.0;
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

class Reservation {
    private Room room;
    private Customer customer;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private String status;

    public Reservation(Room room, Customer customer,
                        LocalDate startDate, LocalDate endDate) {
        this.room = room;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;

        long days = java.time.temporal.ChronoUnit.DAYS.between(
                startDate, endDate);

        this.price = room.calculatePrice(days);
        this.status = "ACTIVE";
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void cancel() {
        status = "CANCELLED";
    }

    public boolean overlaps(LocalDate start, LocalDate end) {
        return start.isBefore(endDate) && end.isAfter(startDate);
    }
}

class BookingManager {
    private List<Room> rooms;
    private List<Reservation> reservations;

    public BookingManager() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Reservation bookRoom(Customer customer, Room room,
                                LocalDate startDate,
                                LocalDate endDate) {

        for (Reservation reservation : reservations) {

            if (reservation.getRoom() == room &&
                reservation.getStatus().equals("ACTIVE") &&
                reservation.overlaps(startDate, endDate)) {

                System.out.println(
                    "Booking failed: " + room.getRoomName() +
                    " is not available for " +
                    startDate + " to " + endDate + "."
                );

                return null;
            }
        }

        Reservation reservation =
                new Reservation(room, customer, startDate, endDate);

        reservations.add(reservation);

        System.out.printf(
            "%s booked from %s to %s. Total price: $%.2f%n",
            room.getRoomName(),
            startDate,
            endDate,
            reservation.getPrice()
        );

        return reservation;
    }

    public void cancelReservation(Reservation reservation,
                                   LocalDate cancellationDeadline,
                                   LocalDate currentDate) {

        if (reservation == null) {
            return;
        }

        if (!reservation.getStatus().equals("ACTIVE")) {
            System.out.println("Reservation is already cancelled.");
            return;
        }

        if (currentDate.isBefore(cancellationDeadline)) {
            reservation.cancel();

            System.out.println(
                "Reservation for " +
                reservation.getRoom().getRoomName() +
                " cancelled successfully."
            );
        } else {
            System.out.println(
                "Cancellation failed: cancellation deadline has passed."
            );
        }
    }
}

public class three {

    public static void main(String[] args) {

        BookingManager manager = new BookingManager();

        Customer customer =
                new Customer(101, "John");

        Room deluxeRoom =
                new DeluxeRoom(101, "Deluxe Room 101");

        Room standardRoom =
                new StandardRoom(205, "Standard Room 205");

        manager.addRoom(deluxeRoom);
        manager.addRoom(standardRoom);

        Reservation reservation1 =
                manager.bookRoom(
                    customer,
                    deluxeRoom,
                    LocalDate.of(2024, 12, 1),
                    LocalDate.of(2024, 12, 5)
                );

        Reservation reservation2 =
                manager.bookRoom(
                    customer,
                    standardRoom,
                    LocalDate.of(2024, 12, 3),
                    LocalDate.of(2024, 12, 7)
                );

        Reservation reservation3 =
                manager.bookRoom(
                    customer,
                    deluxeRoom,
                    LocalDate.of(2024, 12, 3),
                    LocalDate.of(2024, 12, 7)
                );

        manager.cancelReservation(
                reservation1,
                LocalDate.of(2024, 11, 25),
                LocalDate.of(2024, 11, 20)
        );
    }
}