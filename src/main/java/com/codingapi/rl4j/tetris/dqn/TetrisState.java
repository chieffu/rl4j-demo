package com.codingapi.rl4j.tetris.dqn;

import org.deeplearning4j.rl4j.space.Encodable;

public class TetrisState implements Encodable {

    private int score;

    private double[] data;

    public int getScore() {
        return score;
    }

    public TetrisState(int score,double[] data) {
        this.score = score;
        this.data =data;
    }

    @Override
    public double[] toArray() {
        return data;
    }
}
