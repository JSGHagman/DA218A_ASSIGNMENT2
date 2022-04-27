package Assignment2Part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SeatManager {
    private final int occupiedSeats = 10;
    private final int availableSeats = 5;
    private final int maxClients = 15;
    private Seat seat;
    private ArrayList <Seat> allSeats;
    private ArrayList <Seat> openSeats;
    private ArrayList <String> logBook;
    private Random rand = new Random();
    private UserInterface ui;
    private int threadsAlive = 0;

    /**
     * Constructor for this class
     * Will create an ArrayList for Seat-Objects
     * Calls two methods, one for creating the Seat-Objects and one for randomizing which seats are occupied or not.
     */
    public SeatManager(UserInterface ui){
        this.ui = ui;
        allSeats = new ArrayList();
        openSeats = new ArrayList();
        logBook = new ArrayList();
        Collections.synchronizedList(openSeats);
        createSeats();
        setTakenSeats();
        for(int i = 1; i <= maxClients; i++){
            new Thread(new Client(i,this)).start();
        }

    }


    /**
     * Creates 15 objects of Seats and sets everyone to AVAILABLE.
     * Adds every seat to an ArrayList of Seat-Objects in this class
     */
    private void createSeats(){
        for (int i = 1; i <= occupiedSeats + availableSeats; i++){
            seat = new Seat(i, SeatStatus.AVAILABLE);
            allSeats.add(seat);
            openSeats.add(seat);
            String init = String.format("Seat #%s has been created and set to AVAILABLE", seat.getSeatNbr());
            updateLogs(init);
        }
    }

    /**
     * Loop to count to the same number as occupied seats.
     * For every new count:
     * Gets a random number
     * Gets the seat with that number
     * Sets that seat as OCCUPIED
     * Will remove the seat from the list of open seats.
     * Update logs.
     * If the random seat is already been set to occupied it will try again with a new random seat.
     * Randomizes which ten seats are OCCUPIED and which five seats are AVAILABLE
     */
    private void setTakenSeats(){
        int id = -1;
        for (int i = 1; i <= occupiedSeats; i++){
            id = rand.nextInt(1,15);
            seat = allSeats.get(id);
            if(seat.getStatus().equals(SeatStatus.AVAILABLE)){
                seat.setStatus(SeatStatus.OCCUPIED);
                openSeats.remove(seat);
                String taken = String.format("Seat #%s has been set to OCCUPIED", seat.getSeatNbr());
                updateLogs(taken);
            }else{
                i--;
            }
        }
    }

    /**
     * Adds strings to the logbook
     * @param s
     */
    public void updateLogs(String s){
        logBook.add(s);
    }


    /**
     * Prints all logs to the user
     */
    public void printLogs(){
        for (String s : logBook){
            ui.promptUser(s);
        }
    }


    /**
     * Loops through all open seats
     * @param client
     * @return boolean - true if the client manages to secure a seat
     */
    public synchronized boolean bookSeat(Client client){
        boolean booked = false;
        if(isAnyOpen()){
            client.setNumberToBook(getAnOpenSeat());
            for (Seat s : openSeats){
                if(s.getSeatNbr() == client.getNumberToBook()){
                    s.setStatus(SeatStatus.OCCUPIED);
                    String update = String.format("Client #%s BOOKED seat #%s", client.getClientID(), s.getSeatNbr());
                    updateLogs(update);
                    booked = true;
                }else{
                    String failedToBook = String.format("Unsuccessful attempt to book seat #%s by client #%s", s.getSeatNbr() , client.getClientID());
                    updateLogs(failedToBook);
                }
            }
        }
        return booked;
    }

    /**
     * Loops through all opens seats
     * @return int - the id of the open seat.
     */
    public int getAnOpenSeat() {
        int seat = 0;
        for (Seat s : openSeats){
            if(s.getStatus().equals(SeatStatus.AVAILABLE)){
                seat = s.getSeatNbr();
            }
        }
        return seat;
    }

    /**
     * Will check if there are any open seats
     * @return boolean - true if there are any available seats.
     */
    public boolean isAnyOpen(){
        boolean anyOpen = false;
        for(Seat s : openSeats){
                if(s.isOpen(s)){
                    anyOpen = true;
                }
        }
        return anyOpen;
    }


    /**
     * Will let the user know when all threads are dead.
     */
    public void threadsAreDead(){
        threadsAlive ++;
        if(threadsAlive >= 15){
            ui.promptUser("All threads are done");
        }
    }
}
