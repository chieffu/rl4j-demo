package com.codingapi.rl4j.bj.dqn;

import com.codingapi.rl4j.bj.Blackjack;
import org.deeplearning4j.rl4j.space.Encodable;

public class BlackjackState implements Encodable {

    private double score;

    private double[] data;

    public double getScore() {
        return score;
    }

    public BlackjackState(Blackjack blackjack,double score) {
        this.score =  score;
        //前13位数牌 中间4位 闲点数包括拆牌的1手，后2位庄牌点数,最后一位下注量

        double[] state = new double[13+4+1+1];
        int i=0;
        int[] cardCount=blackjack.getShoe().getCardCount();
        for(;i<cardCount.length;i++){
            state[i]=cardCount[i];
        }
        state[i++]=blackjack.getPlayer().getHandValue();
        state[i++]=blackjack.getPlayer().getHandMinValue();
        state[i++]=blackjack.getPlayer().getSplit()==null?0:blackjack.getPlayer().getSplit().getHandValue();
        state[i++]=blackjack.getPlayer().getSplit()==null?0:blackjack.getPlayer().getSplit().getHandMinValue();
        state[i++]=blackjack.getDealer().getFirstCardValue();
        state[i++]=blackjack.getPlayer().getBet();
        this.data = state;
    }

    /**
     * 状态编码
     * @return
     */
    @Override
    public double[] toArray() {
       return data;
    }
}
