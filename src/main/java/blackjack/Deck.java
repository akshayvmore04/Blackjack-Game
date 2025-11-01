package blackjack;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>(52);
        for(Suit suit: Suit.values()){
            for(Rank rank: Rank.values()){
                cards.add(new Card(suit, rank));
            }
        }

    }

    public void printDeck(){
        for(Card card: cards){
           System.out.println(card.toString());
        }
    }
    public void shuffleDeck(int numberoftimes){
        while(numberoftimes > 0){
        int randomno1 = (int)(Math.random()*52);
        int randomno2 = (int)(Math.random()*52);
        Card tempCard = cards.get(randomno1);
        cards.set(randomno1, cards.get(randomno2));
        cards.set(randomno2, tempCard);
        numberoftimes--;
        }
        
    }

    public Card dealCard(){
       return cards.remove(0);
    }

    
}
