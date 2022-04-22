package Assignment2Part1;


import java.util.Scanner;

public class UserInterface {
    private SeatManager manager;
    private Scanner input = new Scanner(System.in);

    public UserInterface(){
        menu();
    }

    public void menu(){
        int choice = -1;
        while(choice != 0){
            System.out.println("***MENU***");
            System.out.println("1. Start threads");
            System.out.println("2. Print Logs");
            System.out.println("0. EXIT");
            choice = input.nextInt();
            if(choice == 1){
                this.manager = new SeatManager(this);
            }if(choice == 2){
                manager.printLogs();
            }
        }
    }

    public void promptUser(String message){
        System.out.println(message);
    }


}
