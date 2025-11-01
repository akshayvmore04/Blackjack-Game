package blackjack;

public class Card {
    Suit suit;
    Rank rank;

    Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
    public Rank getRank(){
        return rank;
    }

    public int getRankValue(){
        return rank.value;
    }

    @Override
    public String toString() {
        return rank + "" + suit;
    }
}
