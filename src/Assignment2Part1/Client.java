package Assignment2Part1;

import java.util.Random;

public class Client implements Runnable{
    private int clientID;
    private SeatManager seatManager;
    private int numberToBook;
    private int numberBooked;
    private Random random = new Random();

    public Client(int id, SeatManager mngr){
        this.clientID = id;
        this.seatManager = mngr;
        numberBooked = 0;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }

    public int getNumberToBook() {
        return numberToBook;
    }

    @Override
    public void run() {
        if(seatManager.isAnyOpen()){
            if(numberBooked < 1){
                numberToBook = seatManager.getAnOpenSeat();
                boolean success = seatManager.bookSeat(this);
                if(success){
                    numberBooked = 1;
                }
            }
        }
    }

    @Override
    public String toString() {
        return String.format("CLIENT ID: %s", this.clientID);
    }
}
