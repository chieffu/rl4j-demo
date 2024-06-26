package com.codingapi.rl4j.bj;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class Blackjack {

   public static final Action[] BET_ACTIONS = new Action[]{Action.BET_BIG, Action.BET_MEDIUM, Action.BET_SMALL};
   public static final Action[] STAND_ACTION = {Action.STAND};
   public static final Action[] FIRST_ROUND_ACTIONS  = new Action[]{Action.STAND, Action.HIT, Action.DOUBLE_DOWN, Action.SPLIT};
   public static final Action[] COMMON_ACTIONS = new Action[]{Action.STAND, Action.HIT};
   Shoe shoe ;
   Player player;
   Dealer dealer;

   public Blackjack(){
      this.shoe = new Shoe();
      this.player = new Player();
      this.dealer = new Dealer();
   }
   public Action[] getAvailableActions() {
      if(isNewRound()){
         return BET_ACTIONS;
      }else if(player.isDown()){
         return STAND_ACTION;
      }else if(isFirstRound()){
         return FIRST_ROUND_ACTIONS;
      }else{
         return COMMON_ACTIONS;
      }
   }

   private boolean isFirstRound() {
      return player.getHand().size()==2&& player.getSplit()==null;
   }

   public boolean isNewRound(){
      return player.getHand().isEmpty();
   }
   public void nextRound(){
      player.reset();
      dealer.reset();
   }

   public boolean isOver(){
      return shoe.isOver();
   }

   public void reset(){
      shoe.reset();
      nextRound();
   }

   public double getScore() {
      if(!dealer.isDown()){
         return 0;
      }
      if(player.isSplit()){
         return calculateBenefit(player,dealer)+calculateBenefit(player.getSplit(),dealer);
      }else {
         if (player.isBlackjack() && dealer.isBlackjack()) return 0;
         if (player.isBlackjack()) return 1.5 * player.getBet();
         if (dealer.isBlackjack()) return -player.getBet();
         return calculateBenefit(player,dealer);
      }
   }

   private double calculateBenefit(Player player, Dealer dealer) {
      double bets =  player.getBet();
      if (player.isBusted()) return -bets;
      if (dealer.isBusted() ||player.getHandValue()>dealer.getHandValue()) return bets;
      else if(player.getHandValue()<dealer.getHandValue()) return -bets;
      return 0;
   }

   public double takeAction(Integer actionIndex) {
      Action action = Action.values()[actionIndex];
      Action[] availableActions = getAvailableActions();
      if(Arrays.stream(availableActions).allMatch(a->a!=action)){
         return -20;
      }
      double score = 0;
      switch (action) {
         case BET_BIG:
            player.bet(10);
            player.hit(shoe.drawCard());
            player.hit(shoe.drawCard());
            dealer.hit(shoe.drawCard());
            break;
         case BET_MEDIUM:
            player.bet(1);
            player.hit(shoe.drawCard());
            player.hit(shoe.drawCard());
            dealer.hit(shoe.drawCard());
            break;
         case BET_SMALL:
            player.bet(0.1);
            player.hit(shoe.drawCard());
            player.hit(shoe.drawCard());
            dealer.hit(shoe.drawCard());
            break;
         case SPLIT:
            boolean s = player.split();
            if(s){
               player.getSplit().hit(shoe.drawCard());
               if(player.getSplit().isDown()){
                  player.getSplit().stand();
               }
               player.hit(shoe.drawCard());
               if(player.isDown()){
                  while (dealer.shouldHit()) {
                     dealer.hit(shoe.drawCard());
                  }
                  score = getScore();
                  nextRound();
               }
            }
            break;
         case DOUBLE_DOWN:
            player.doubleDown(shoe.drawCard());
            while (dealer.shouldHit()) {
               dealer.hit(shoe.drawCard());
            }
            score = getScore();
            nextRound();
            break;
         case HIT:
            player.hit(shoe.drawCard());
            if(player.isDown()){
               while (dealer.shouldHit()) {
                  dealer.hit(shoe.drawCard());
               }
               score = getScore();
               nextRound();
            }
            break;
         case STAND:
            player.stand();
            if(player.isDown()) {
               while (dealer.shouldHit()) {
                  dealer.hit(shoe.drawCard());
               }
               score = getScore();
               nextRound();
            }
            break;

      }
      return score;
   }
}
