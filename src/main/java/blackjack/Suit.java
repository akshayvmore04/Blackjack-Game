package blackjack;

public enum Suit {
    Hearts, Diamons, Clubs, Spades;

    @Override
    public String toString() {
        switch (this) {
            case Hearts -> {
                return "H";
            }
            case Diamons -> {
                return "D";
            }
            case Clubs -> {
                return "C";
            }
        case Spades -> {
            return "S";
            }

            default -> throw new AssertionError();
        }
    }
}
