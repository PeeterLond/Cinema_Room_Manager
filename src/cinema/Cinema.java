package cinema;

import java.util.Scanner;

public class Cinema {

    public static final int SMALL_ROOM_CAP = 60;
    public static final int START_ROW = 1;
    public static final int START_COLUMN = 1;
    public static final int FULL_PRICE = 10;
    public static final int REDUCED_PRICE = 8;
    public static final char SEAT_AVAILABLE = 'S';
    public static final char SEAT_BOOKED = 'B';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Seat[][] cinemaRoom = createCinemaRoom(scanner);
        menuSelect(scanner, cinemaRoom);

    }

    private static void menuSelect(Scanner scanner, Seat[][] cinemaroom) {
        boolean isOn = true;
        while (isOn) {
            CinemaMenu.printMenu();
            String input = scanner.nextLine();
            CinemaMenu menuItemFromInput = CinemaMenu.getMenuItemFromInput(input);

            switch (menuItemFromInput) {
                case SHOW_SEATS -> printCinemaRoom(cinemaroom);
                case BUY_TICKETS -> bookAndPrintTicket(scanner, cinemaroom);
                case STATISTICS -> showStatistics(cinemaroom);
                case EXIT -> isOn = false;
            }
        }
    }

    private static void printCinemaRoom(Seat[][] cinemaroom) {
        printCinemaRoomHeader(cinemaroom);
        printCinemaRoomSeats(cinemaroom);

    }

    private static void printCinemaRoomHeader(Seat[][] cinemaroom) {
        System.out.println();
        System.out.print("Cinema:\n  ");
        for (int i = START_COLUMN; i < cinemaroom[START_ROW].length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    private static void printCinemaRoomSeats(Seat[][] cinemaroom) {
        for (int r = START_ROW; r < cinemaroom.length; r++) {
            System.out.print(r + " ");
            for (int c = START_COLUMN; c < cinemaroom[r].length; c++) {
                System.out.print(cinemaroom[r][c].getAvailabilityChar() + " ");

            }
            System.out.println();
        }
    }

    private static Seat[][] createCinemaRoom(Scanner scanner) {
        int row = getRowFromInput(scanner);
        int seats = getSeatsFromInput(scanner);

        Seat[][] cinemaRoom = new Seat[row + START_ROW][seats + START_COLUMN];

        boolean isLargeCinemaRoom = row * seats > SMALL_ROOM_CAP;
        int firstReducedRowIndex = (row / 2) + START_ROW;

        for (int r = START_ROW; r < cinemaRoom.length; r++) {
            for (int c = START_COLUMN; c < cinemaRoom[r].length; c++) {
                Seat seat = new Seat();
                if (isLargeCinemaRoom && r >= firstReducedRowIndex) {
                    seat.setPrice(REDUCED_PRICE);
                }

                cinemaRoom[r][c] = seat;
            }
        }
        return cinemaRoom;
    }

    private static int getSeatsFromInput(Scanner scanner) {
        System.out.println("Enter the number of seats in each row:");
        return Integer.parseInt(scanner.nextLine());
    }

    private static int getRowFromInput(Scanner scanner) {
        System.out.println("Enter the number of rows:");
        return Integer.parseInt(scanner.nextLine());
    }

    private static void bookAndPrintTicket(Scanner scanner, Seat[][] cinemaroom) {
        while (true) {
            int price = 0;
            try {
                price = bookAndGetTicketPrice(scanner, cinemaroom);
                System.out.println("Ticket price: $" + price);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static int bookAndGetTicketPrice(Scanner scanner, Seat[][] cinemaroom) throws Exception {
        System.out.println();
        int rowNr = getRowNumberFromInput(scanner);
        int seatNr = getSeatNumberFromInput(scanner);
        validateCorrectSeatInput(rowNr, seatNr, cinemaroom);
        Seat seat = cinemaroom[rowNr][seatNr];
        validateTicketIsAvailable(seat);
        System.out.println();
        seat.setAvailabilityChar(SEAT_BOOKED);
        seat.setBooked(true);
        return seat.getPrice();
    }

    private static int getRowNumberFromInput(Scanner scanner){
        System.out.println("Enter a row number:");
        return Integer.parseInt(scanner.nextLine());
    }

    private static int getSeatNumberFromInput(Scanner scanner){
        System.out.println("Enter a seat number in that row:");
        return Integer.parseInt(scanner.nextLine());
    }

    private static void validateCorrectSeatInput(int rowNr, int seatNr, Seat[][] cinemaroom) throws Exception {
        if (rowNr > (cinemaroom.length - START_ROW) || seatNr > (cinemaroom[START_ROW].length - START_COLUMN)) {
            throw new Exception("Wrong Input!");
        }
    }

    private static void validateTicketIsAvailable(Seat seat) throws Exception {
        if (seat.isBooked()) {
            throw new Exception("\nThat ticket has already been purchased!");
        }
    }
    private static void showStatistics(Seat[][]cinemaRoom){
        printPurchasedTickets(cinemaRoom);
        printCurrentIncome(cinemaRoom);
        printTotalIncome(cinemaRoom);
    }

    private static void printPurchasedTickets(Seat[][]cinemaRoom){
        int counter = 0;
        int nrOfSeats=(cinemaRoom.length - START_ROW) * (cinemaRoom[START_ROW].length - START_COLUMN);
        for(int row = START_ROW; row<cinemaRoom.length; row++){
            for(int col = START_COLUMN; col<cinemaRoom[row].length; col++){
                if(cinemaRoom[row][col].isBooked()){
                    counter++;
                }
            }
        }
        System.out.println("Number of purchased tickets: " + counter);
        double percentage=((double) counter/nrOfSeats) * 100;
        System.out.println("Percentage: " + String.format("%.2f",percentage) + "%");
    }

    private static void printCurrentIncome(Seat[][]cinemaRoom){
        int sum = 0;
        for(int row = START_ROW; row<cinemaRoom.length; row++){
            for(int col = START_COLUMN; col<cinemaRoom[row].length; col++){
                if(cinemaRoom[row][col].isBooked()){
                    sum += cinemaRoom[row][col].getPrice();
                }
            }
        }
        System.out.println("Current Income: $"+sum);
    }

    private static void printTotalIncome(Seat[][]cinemaRoom){
        int totalIncome = 0;
        for(int row = 1; row < cinemaRoom.length; row++){
            for(int seat = 1; seat < cinemaRoom[row].length; seat++){
                totalIncome += cinemaRoom[row][seat].getPrice();
            }
        }
        System.out.println("Total income: $" + totalIncome);
    }

}
