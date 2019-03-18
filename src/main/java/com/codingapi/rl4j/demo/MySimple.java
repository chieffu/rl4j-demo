package com.codingapi.rl4j.demo;

import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.gym.StepReply;
import org.deeplearning4j.rl4j.mdp.MDP;
import org.deeplearning4j.rl4j.space.ArrayObservationSpace;
import org.deeplearning4j.rl4j.space.DiscreteSpace;
import org.deeplearning4j.rl4j.space.ObservationSpace;

@Slf4j
public class MySimple implements MDP<MySimpleState, Integer, DiscreteSpace> {

    /**
     * 游戏结束的最大步数
     */
    final private int maxStep;

    /**
     * 最大分数
     */
    final private int maxReward;

    private MySimpleState mySimpleState;

    /**
     * 方向 left right
     */
    private DiscreteSpace actionSpace = new DiscreteSpace(2);

    /**
     * 对应 MySimpleState 的存储空间数据
     */
    private ObservationSpace<MySimpleState> observationSpace = new ArrayObservationSpace(new int[] {2});


    public MySimple(int maxStep,int maxReward) {
        this.maxStep = maxStep;
        this.maxReward = maxReward;
    }


    @Override
    public ObservationSpace<MySimpleState> getObservationSpace() {
        return observationSpace;
    }

    @Override
    public DiscreteSpace getActionSpace() {
        return actionSpace;
    }

    @Override
    public MySimpleState reset() {
        //重置参数
        return mySimpleState = new MySimpleState(0,0);
    }


    @Override
    public void close() {
        //释放游戏参数
    }

    @Override
    public StepReply<MySimpleState> step(Integer action) {
        //游戏执行一步 action 为动作
        int reward = (action==0?-1:1);

        //游戏的当前状态
        mySimpleState = new MySimpleState( mySimpleState.getReward()+reward,mySimpleState.getStep() + 1);

        return new StepReply<>(mySimpleState, reward, isDone(), null);
    }

    @Override
    public boolean isDone() {

        boolean res =   mySimpleState.getReward() == maxReward;
        if(res){
            log.info("step->{},reward->{}",mySimpleState.getStep(),mySimpleState.getReward());
        }
        return res;
    }


    @Override
    public MDP<MySimpleState, Integer, DiscreteSpace> newInstance() {
        return new MySimple(maxStep,maxReward);
    }
}
