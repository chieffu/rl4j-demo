package com.codingapi.rl4j.demo;

import org.deeplearning4j.rl4j.learning.sync.qlearning.QLearning;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.deeplearning4j.rl4j.policy.DQNPolicy;
import org.deeplearning4j.rl4j.util.DataManager;
import org.nd4j.linalg.learning.config.Adam;

import java.io.IOException;

/**
 * @author rubenfiszel (ruben.fiszel@epfl.ch) on 8/11/16.
 * <p>
 * main example for toy DQN
 */
public class MyTest {


    public static QLearning.QLConfiguration TOY_QL =
        new QLearning.QLConfiguration(
            123,   //Random seed
            1000,//Max step By epoch 批次下最大执行的步数
            500, //Max step            总执行的部署
            1000, //Max size of experience replay 记忆数据
            100,    //size of batches
            100,   //target update (hard) 每10次更新一次参数
            0,     //num step noop warmup   步数从0开始
            0.05,  //reward scaling
            0.99,  //gamma
            10.0,  //td-error clipping
            0.1f,  //min epsilon
            100,  //num step for eps greedy anneal
            true   //double DQN
        );



    public static DQNFactoryStdDense.Configuration TOY_NET =
        DQNFactoryStdDense.Configuration.builder()
            .l2(0.01)
            .updater(new Adam(1e-2))
            .numLayer(3)
            .numHiddenNodes(16)
            .build();

    public static void main(String[] args) throws IOException {
//        learning();
//        testing();
          running();
    }

    public static void learning() throws IOException {

        //record the training data in rl4j-data in a new folder
        DataManager manager = new DataManager();

        //define the mdp from toy (toy length)
        MySimple mdp = new MySimple(20,5);

        //define the training method
        QLearningDiscreteDense<MySimpleState> dql = new QLearningDiscreteDense<MySimpleState>(mdp, TOY_NET, TOY_QL, manager);

        //get the final policy
        DQNPolicy<MySimpleState> pol = dql.getPolicy();

        //start the training
        dql.train();

        //serialize and save (serialization showcase, but not required)
        pol.save("my_simple.policy");

        //useless on toy but good practice!
        mdp.close();

    }



    public static void running() throws IOException {

        //record the training data in rl4j-data in a new folder
        DataManager manager = new DataManager();

        //define the mdp from toy (toy length)
        MySimple mdp = new MySimple(20,5);

        //define the training method
        QLearningDiscreteDense<MySimpleState> dql = new QLearningDiscreteDense<MySimpleState>(mdp, TOY_NET, TOY_QL, manager);

        //start the training
        dql.train();

        //get the final policy
        DQNPolicy<MySimpleState> policy = dql.getPolicy();

        for (int i = 0; i < 10; i++) {
            mdp.reset();
            double reward = policy.play(mdp);
            System.out.println(reward);
        }

        //useless on toy but good practice!
        mdp.close();

    }

    public static void testing() throws IOException{

        //load the previous agent
        DQNPolicy<MySimpleState> policy = DQNPolicy.load("my_simple.policy");

        //define the mdp from toy (toy length)
        MySimple mdp = new MySimple(20,5);

        for (int i = 0; i < 10; i++) {
            mdp.reset();
            double reward = policy.play(mdp);
            System.out.println(reward);
        }
    }


}
