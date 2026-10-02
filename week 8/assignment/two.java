import java.util.*;

interface ShippingType {
    double calculateCharge(double weight);
}

class StandardShipping implements ShippingType {

    @Override
    public double calculateCharge(double weight) {
        return 40 + (10 * weight);
    }
}

class ExpressShipping implements ShippingType {

    @Override
    public double calculateCharge(double weight) {
        return 80 + (15 * weight);
    }
}

class FragileShipping implements ShippingType {

    private ShippingType standardShipping;

    public FragileShipping() {
        standardShipping = new StandardShipping();
    }

    @Override
    public double calculateCharge(double weight) {
        return standardShipping.calculateCharge(weight) + 50;
    }
}

interface NotificationChannel {
    void notify(String parcelId, String status);
}

class SmsChannel implements NotificationChannel {

    @Override
    public void notify(String parcelId, String status) {
        System.out.println(
            "[SMS] " + parcelId + " is now " + status + "."
        );
    }
}

class EmailChannel implements NotificationChannel {

    @Override
    public void notify(String parcelId, String status) {
        System.out.println(
            "[Email] " + parcelId + " is now " + status + "."
        );
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

class Parcel {
    private String parcelId;
    private Customer customer;
    private double weight;
    private ShippingType shippingType;
    private String status;
    private List<NotificationChannel> channels;

    public Parcel(String parcelId,
                  Customer customer,
                  double weight,
                  ShippingType shippingType) {

        this.parcelId = parcelId;
        this.customer = customer;
        this.weight = weight;
        this.shippingType = shippingType;
        this.status = "BOOKED";
        this.channels = new ArrayList<>();
    }

    public String getParcelId() {
        return parcelId;
    }

    public String getStatus() {
        return status;
    }

    public double calculateCharge() {
        return shippingType.calculateCharge(weight);
    }

    public void subscribe(NotificationChannel channel) {
        channels.add(channel);
    }

    private void notifyChannels() {
        for (NotificationChannel channel : channels) {
            channel.notify(parcelId, status);
        }
    }

    public void changeStatus(String newStatus) {

        if (!isValidTransition(newStatus)) {
            System.out.println(
                "Invalid transition: " +
                status + " → " +
                newStatus +
                " is not allowed."
            );
            return;
        }

        status = newStatus;
        notifyChannels();
    }

    private boolean isValidTransition(String newStatus) {

        if (status.equals("BOOKED") &&
            newStatus.equals("PICKED_UP")) {
            return true;
        }

        if (status.equals("PICKED_UP") &&
            newStatus.equals("IN_TRANSIT")) {
            return true;
        }

        if (status.equals("IN_TRANSIT") &&
            newStatus.equals("OUT_FOR_DELIVERY")) {
            return true;
        }

        if (status.equals("OUT_FOR_DELIVERY") &&
            newStatus.equals("DELIVERED")) {
            return true;
        }

        return false;
    }

    public void cancel() {

        if (status.equals("BOOKED")) {
            status = "CANCELLED";
            notifyChannels();
        } else {
            System.out.println(
                "Cancellation failed: " +
                parcelId +
                " can be cancelled only while BOOKED."
            );
        }
    }
}

class ParcelService {

    public Parcel bookParcel(String parcelId,
                             Customer customer,
                             double weight,
                             ShippingType shippingType) {

        Parcel parcel =
                new Parcel(
                    parcelId,
                    customer,
                    weight,
                    shippingType
                );

        String type = shippingType.getClass()
                .getSimpleName()
                .replace("Shipping", "");

        System.out.printf(
            "Parcel %s booked (%s, %.0f kg). Charge: ₹%.2f%n",
            parcelId,
            type,
            weight,
            parcel.calculateCharge()
        );

        return parcel;
    }
}

public class two {

    public static void main(String[] args) {

        ParcelService service =
                new ParcelService();

        Customer customer =
                new Customer(101, "John");

        Parcel parcel =
                service.bookParcel(
                    "P101",
                    customer,
                    2,
                    new ExpressShipping()
                );

        parcel.subscribe(new SmsChannel());
        parcel.subscribe(new EmailChannel());

        parcel.changeStatus("BOOKED");

        parcel.changeStatus("PICKED_UP");

        parcel.cancel();

        parcel.changeStatus("IN_TRANSIT");

        parcel.changeStatus("DELIVERED");
    }
}