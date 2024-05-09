package com.codingapi.rl4j.bj.dqn;

import com.codingapi.rl4j.bj.Action;
import com.codingapi.rl4j.bj.Blackjack;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class BlackjackEnv implements MDP<BlackjackState, Integer, DiscreteSpace> {
    Blackjack blackjack = new Blackjack();

    @Override
    public ObservationSpace<BlackjackState> getObservationSpace() {
        return new ObservationSpace<BlackjackState>(){

            @Override
            public String getName() {
                return "blackjack-observationSpace";
            }

            @Override
            public int[] getShape() {
                return new int[]{19};
            }

            @Override
            public INDArray getLow() {
                return Nd4j.zeros(getShape());
           }

            @Override
            public INDArray getHigh() {
                float[] f = new float[19];
                for(int i=0;i<13;i++){
                    f[i]=32;
                }
                f[13]=30;
                f[14]=30;
                f[15]=30;
                f[16]=30;
                f[17]=10;
                f[18]=20;
                return Nd4j.create(f).reshape(getShape());
            }
        };
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return new DiscreteSpace(Action.values().length);
    }

    @Override
    public BlackjackState reset() {
         blackjack.reset();
         return new BlackjackState(blackjack,0.0);
    }

    @Override
    public void close() {

    }

    @Override
    public StepReply<BlackjackState> step(Integer action) {
        double score = blackjack.takeAction(action);
        BlackjackState state = new BlackjackState(blackjack,score);
        return new StepReply<>(state, state.getScore(), isDone(), null);
    }

    @Override
    public boolean isDone() {
        return blackjack.isOver();
    }

    @Override
    public MDP<BlackjackState, Integer, DiscreteSpace> newInstance() {
        return new BlackjackEnv();
    }
}
