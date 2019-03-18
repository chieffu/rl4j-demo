package com.codingapi.rl4j.demo;

import lombok.Value;
import org.deeplearning4j.rl4j.space.Encodable;

@Value
public class MySimpleState implements Encodable {

    int reward;

    int step;

    public MySimpleState(int reward, int step) {
        this.reward = reward;
        this.step = step;
    }

    @Override
    public double[] toArray() {
        double[] ar = new double[2];
        ar[0] = reward;
        ar[1] = step;
        return ar;
    }


    public int getReward() {
        return reward;
    }

    public int getStep() {
        return step;
    }
}
