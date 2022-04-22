package Assignment2Part1;

public class Seat {
    private SeatStatus status;
    private int seatNbr;

    public Seat(int seatNbr, SeatStatus status){
        this.seatNbr = seatNbr;
        this.status = status;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    public int getSeatNbr() {
        return seatNbr;
    }

    public void setSeatNbr(int seatNbr) {
        this.seatNbr = seatNbr;
    }

    @Override
    public String toString() {
        return String.format("SEAT: %s | STATUS: %s", this.seatNbr, this.status);
    }
}
