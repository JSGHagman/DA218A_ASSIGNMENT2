package Assignment2Part2;

public class Seat {
    private SeatStatus status;
    private int seatNbr;

    public Seat(int seatNbr, SeatStatus status){
        this.seatNbr = seatNbr;
        this.status = status;
    }

    /**
     * @return Enum - Status of the seat i.e. OCCUPIED/AVAILABLE
     */
    public SeatStatus getStatus() {
        return status;
    }

    /**
     * Sets status the seat i.e. OCCUPIED/AVAILABLE
     * @param status
     */
    public void setStatus(SeatStatus status) {
        this.status = status;
    }

    /**
     * @return int - the number of this seat
     */
    public int getSeatNbr() {
        return seatNbr;
    }

    public boolean isOpen(Seat s){
        boolean open = false;
        if(s.getStatus() == SeatStatus.AVAILABLE){
            open = true;
        }
        return open;
    }

    /**
     * @return String - Returns the number of the seat
     * and it's status i.e. OCCUPIED/AVAILABLE formatted as a string for this object
     */
    @Override
    public String toString() {
        return String.format("SEAT: %s | STATUS: %s", this.seatNbr, this.status);
    }
}
