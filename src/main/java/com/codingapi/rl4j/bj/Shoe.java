package com.codingapi.rl4j.bj;

import lombok.Getter;

import java.util.*;

/**
 * Created by Zosit on 11/22/2017.
 * This
 *
 */
 @Getter
public class Shoe {

  private List<Card> shoe;

  private int[] cardCount;

  int cut;

  public Shoe() {
    reset();
  }
  public void reset() {
    List<Card> newShoe = new LinkedList<>();
    int deckSize =8;
    // creates 6 decks and shuffles them together before returning the shoe
    for (int i = 0; i < deckSize; i++) {
      for (Card.Suit s : Card.Suit.values()) {
        for (Card.Rank r : Card.Rank.values()) {
          newShoe.add(new Card(s, r));
        }
      }
    }
    Collections.shuffle(newShoe);
    this.shoe = newShoe;
    cut = (int)(Math.random()*(180-60))+60;
    cardCount = new int[13];
    Arrays.fill(cardCount, 4*deckSize);
  }

  // remove a card from index 0 of the shoe (since it's randomized) and return it
  public Card drawCard() {
    Card card= shoe.remove(0);
    cardCount[card.getRank().getNum()-1]--;
    return card;
  }

  public boolean isOver(){
    return shoe.size()<cut;
  }

  public int getShoeSize() {
    return this.shoe.size();
  }
}
