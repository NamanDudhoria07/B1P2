import java.util.Scanner;

class ParkingSlot {

    String slotNo;
    int capacity;
    int occupiedCount;

    ParkingSlot(
            String slotNo,
            int capacity,
            int occupiedCount
    ) {

        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    void allot(String vehicleNo) {

        if (occupiedCount < capacity) {

            occupiedCount++;

            System.out.println(
                    vehicleNo +
                            " allotted to slot " +
                            slotNo
            );

        } else {

            System.out.println("Slot is full");
        }
    }
}

public class ParkingMain {

    static ParkingSlot findAvailableSlot(
            ParkingSlot[] slots
    ) {

        for (int i = 0; i < slots.length; i++) {

            if (slots[i].occupiedCount <
                    slots[i].capacity) {

                return slots[i];
            }
        }

        return null;
    }

    static void safeAllot(
            ParkingSlot[] slots,
            String vehicleNo
    ) {

        ParkingSlot slot =
                findAvailableSlot(slots);

        if (slot != null) {

            slot.allot(vehicleNo);

        } else {

            System.out.println(
                    "No slots available for " +
                            vehicleNo
            );
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ParkingSlot[] slots =
                new ParkingSlot[2];

        for (int i = 0; i < 2; i++) {

            System.out.println(
                    "\nEnter Slot " + (i + 1)
            );

            System.out.print("Slot Number: ");
            String slotNo = sc.next();

            System.out.print("Capacity: ");
            int capacity = sc.nextInt();

            System.out.print("Occupied Count: ");
            int occupied = sc.nextInt();

            slots[i] =
                    new ParkingSlot(
                            slotNo,
                            capacity,
                            occupied
                    );
        }

        System.out.print(
                "\nEnter vehicle number: "
        );

        String vehicleNo = sc.next();

        safeAllot(slots, vehicleNo);

        sc.close();
    }
}