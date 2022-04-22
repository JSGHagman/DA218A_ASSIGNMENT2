package Assignment2Part1;

import java.util.ArrayList;
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
        createSeats();
        setTakenSeats();
        setAvailableSeatsList();
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

    public void updateLogs(String s){
        logBook.add(s);
    }

    public void setAvailableSeatsList(){
        for(Seat s : openSeats) {
            logBook.add(s.toString());
        }
    }

    public void printLogs(){
        for (String s : logBook){
            ui.promptUser(s);
        }
    }

    public boolean bookSeat(Client client){
        boolean booked = false;
        for (Seat s : openSeats){
            if(s.getSeatNbr() == client.getNumberToBook()){
                if(s.getStatus()!=SeatStatus.OCCUPIED){
                    s.setStatus(SeatStatus.OCCUPIED);
                    String update = String.format("Client #%s BOOKED seat #%s", client.getClientID(), s.getSeatNbr());
                    updateLogs(update);
                    booked = true;
                }
            }else{
               String failedToBook = String.format("Unsuccessful attempt to book seat #%s by client #%s", s.getSeatNbr() , client.getClientID());
               updateLogs(failedToBook);
            }
        }
        return booked;
    }

    public int getAnOpenSeat() {
        int seat = 0;
        for (Seat s : openSeats){
            if(s.getStatus().equals(SeatStatus.AVAILABLE)){
                seat = s.getSeatNbr();
            }
        }
        return seat;
    }

    public boolean isAnyOpen(){
        boolean anyOpen = false;
        for(Seat s : openSeats){
            if(s.getStatus().equals(SeatStatus.AVAILABLE)){
                anyOpen = true;
            }
        }
        return anyOpen;
    }
}
