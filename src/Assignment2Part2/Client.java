package Assignment2Part2;

import java.util.Random;

/**
 * Class used setting some info about the client.
 * Runs a thread where it attempts to book a seat.
 */

public class Client implements Runnable {
    private int clientID;
    private SeatManager seatManager;
    private int numberToBook;
    private int numberBooked;
    private Random random = new Random();

    public Client(int id, SeatManager mngr) {
        this.clientID = id;
        this.seatManager = mngr;
        numberBooked = 0;
        int max = random.nextInt(1000000);
        for (int i = 0; i < max; i++){
        //busy spin
        }
    }

    /**
     * @return int - The id of the client.
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * @return int - The number of the seat that the client wants to book.
     */
    public int getNumberToBook() {
        return numberToBook;
    }


    /**
     * Will set what seat the client wants to book
     * @param numberToBook
     */
    public void setNumberToBook(int numberToBook) {
        this.numberToBook = numberToBook;
    }

    /**
     * This method is ran by each client.
     * Will first check if any seats are open if there isn't any open it will just die immediately
     * If there is an open seat, it will try to book a seat while the client has not booked one yet.
     * Will then get any random seat (given that seat has not already been booked).
     * Will then try to book that seat, and if it is successful, it will exit the thread and the thread will die.
     *
     */
    @Override
    public void run() {
        if(seatManager.isAnyOpen()){
            while(numberBooked < 1) {
                boolean success = seatManager.bookSeat(this);
                if (success) {
                    numberBooked = 1;
                } if (!seatManager.isAnyOpen()){
                     numberBooked = 1;
                }
            }
        }
        seatManager.threadsAreDead();
    }

    /**
     * @return String - STring formatted with ClientID
     */
    @Override
    public String toString() {
        return String.format("CLIENT ID: %s", this.clientID);
    }
}
