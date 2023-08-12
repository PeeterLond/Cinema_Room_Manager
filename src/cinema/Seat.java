package cinema;


public class Seat {

    private int price = Cinema.FULL_PRICE;
    private boolean isBooked = false;
    private char availabilityChar = Cinema.SEAT_AVAILABLE;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public char getAvailabilityChar() {
        return availabilityChar;
    }

    public void setAvailabilityChar(char availabilityChar) {
        this.availabilityChar = availabilityChar;
    }
}
