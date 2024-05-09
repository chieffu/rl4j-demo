package com.codingapi.rl4j.bj;

/**
 * Created by Zosit on 11/22/2017.
 * This class acts as a card in a typical BlackJack deck
 */
public class Card implements Comparable<Card> {

    public enum Suit {
        DIAMONDS, HEARTS, SPADES, CLUBS
    }

    public enum Rank {
        ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
        EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

        private final int num;

        Rank(int value) {
            this.num = value;
        }

        public int getValue() {
            return num>10?10:num;
        }

        public int getNum(){
            return num;
        }
    }

    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public boolean isFace() {
        return rank == Rank.TEN || rank == Rank.JACK || rank == Rank.QUEEN || rank == Rank.KING;
    }

    public boolean isAce() {
        return rank == Rank.ACE;
    }

    public Suit getSuit() {
        return this.suit;
    }

    public Rank getRank() {
        return this.rank;
    }

    public int getValue() {
        return this.rank.getValue();
    }

    public int getNum(){
        return this.rank.getNum();
    }

    public int compareTo(Card card2) {
        return this.rank.getValue() - card2.rank.getValue();
    }
}
