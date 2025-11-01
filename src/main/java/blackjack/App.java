package blackjack;

import java.util.Scanner;

public class App {
    final public Scanner sc;

    public static void main(String[] args) throws Exception {
        String playagain;
        do {

            App app = new App();
            app.game();
            System.out.println("Do you wanna play again..?\nY/N:");
            playagain = app.sc.nextLine().toLowerCase();
            if (!playagain.equals("y")) {
                System.out.println("Thank You Good Bye..!");
            }

        } while (playagain.equals("y"));

    }

    public App() {
        this.sc = new Scanner(System.in);
    }

    public void game() throws Exception {
        BlackJackGame blackJackGame = new BlackJackGame();
        blackJackGame.start();
        while (!blackJackGame.isGameEnded()) {
            System.out.println("1.Hit 2.Stay");
            int Choice = Integer.parseInt(sc.nextLine());
            switch (Choice) {
                case 1 -> blackJackGame.hit();
                case 2 -> blackJackGame.stay();
                default -> throw new Exception("Invalid choice");

            }
        }
        blackJackGame.dealerPlays();
        Player winner = blackJackGame.determineWinner();
        if (winner == null) {
            System.out.println("It's Tie");
            return;
        }
        System.out.println(winner.name + " Has Won!");
    }

}
