package blackjack;

import java.util.ArrayList;
import java.util.List;

final public class Player {
    final List<Card> playerCards;
    final String name;
    boolean stay;

    public Player(String name) {
        this.playerCards = new ArrayList<>();
        this.name = name;
    }

    public void addCard(Card card) {
        playerCards.add(card);
    }

    public int getTotalScore() {
        int total = 0;
        int aceCount = 0;
        for (Card card : playerCards) {
            total += card.getRankValue();
            if (card.getRank() == Rank.Ace) {
                aceCount++;
            }
            if (total > 21 && aceCount > 0) {
                total -= 10;
                aceCount--;
            }
        }
       

        return total;
    }

    public void stay() {
        this.stay = true;
    }
    public boolean isStaying(){
        return stay;
    }

    @Override
    public String toString(){
        return name+"'s hand:"+playerCards+" Total: "+getTotalScore();
    }

}
