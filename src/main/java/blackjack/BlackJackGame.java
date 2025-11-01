package blackjack;

final public class BlackJackGame {
    final Player player;
    final Player dealer;
    final Deck deck;

    public BlackJackGame() {
        this.player = new Player("Player1");
        this.dealer = new Player("Dealer");
        this.deck = new Deck();
        deck.shuffleDeck(312);
    }

    public void start() {
        player.addCard(deck.dealCard());
        player.addCard(deck.dealCard());
        System.out.println(player);
        dealer.addCard(deck.dealCard());
        System.out.println(dealer);
    }

    public void hit() {
        player.addCard(deck.dealCard());
        System.out.println(player);
    }

    public void stay() {
        player.stay();
    }

    public boolean isGameEnded() {
        if (player.getTotalScore() >= 21 || player.stay) {
            return true;
        }                
        return false;
    }

    public void dealerPlays() {
        while (isGameEnded()) {
            if (player.getTotalScore() < 21 && dealer.getTotalScore() <= 21) {

                if (dealer.getTotalScore() < 17) {
                    dealer.addCard(deck.dealCard());
                    System.out.println(dealer);
                    continue;
                }
                if (dealer.getTotalScore() >= 17) {
                    dealer.stay();
                    return;
                }
            }
            return;
        }

    }

    public Player determineWinner() {
        if (player.getTotalScore() > 21) {
            return dealer;
        }
        if (dealer.getTotalScore() > 21) {
            return player;
        }
        if (player.getTotalScore() == dealer.getTotalScore()) {
            return null;
        }
        if (player.getTotalScore() < dealer.getTotalScore()) {
            return dealer;
        }

        return player;

    }
}
