package blackjack;

public enum Rank {
    Ace(11), Two(2), Three(3), Four(4), Five(5),
    Six(6), Seven(7), Eight(8), Nine(9), Ten(10), J(10), Q(10), K(10);

    public int value;

    Rank(int value) {
        this.value = value;
    }
  

    @Override
    public String toString() {
        switch (this) {
            case Ace -> {
                return "A";
            }
            case Two -> {
                return "2";
            }
            case Three -> {
                return "3";
            }
            case Four -> {
                return "4";
            }
            case Five -> {
                return "5";
            }
            case Six -> {
                return "6";
            }
            case Seven -> {
                return "7";
            }
            case Eight -> {
                return "8";
            }
            case Nine -> {
                return "9";
            }
            case Ten -> {
                return "10";
            }
            case J -> {
                return "J";
            }
            case Q -> {
                return "Q";
            }
            case K -> {
                return "K";
            }
            default -> throw new AssertionError();
        }
    }

}
