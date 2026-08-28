import java.util.Scanner;

class HostelRoom {
    String roomNo;
    int beds;
    int occupied;

    HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    void allot(String name) {

        if (occupied < beds) {
            occupied++;

            System.out.println(
                    name + " allotted to room " + roomNo
            );
        }
    }
}

public class HostelRoomMain {

    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {

        for (int i = 0; i < rooms.length; i++) {

            if (rooms[i].occupied < rooms[i].beds) {
                return rooms[i];
            }
        }

        return null;
    }

    static void safeAllot(
            HostelRoom[] rooms,
            String studentName
    ) {

        HostelRoom room = findAvailableRoom(rooms);

        if (room != null) {
            room.allot(studentName);
        } else {
            System.out.println(
                    "No rooms available for " + studentName
            );
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        HostelRoom[] rooms = new HostelRoom[2];

        for (int i = 0; i < 2; i++) {

            System.out.println("Enter Room " + (i + 1) + " details:");

            System.out.print("Room Number: ");
            String roomNo = sc.next();

            System.out.print("Number of beds: ");
            int beds = sc.nextInt();

            System.out.print("Number of occupied beds: ");
            int occupied = sc.nextInt();

            rooms[i] =
                    new HostelRoom(roomNo, beds, occupied);
        }

        System.out.print("\nEnter student name: ");
        String studentName = sc.next();

        safeAllot(rooms, studentName);

        sc.close();
    }
}