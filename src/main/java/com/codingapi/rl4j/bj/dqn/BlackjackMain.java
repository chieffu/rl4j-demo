package com.codingapi.rl4j.bj.dqn;

import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.rl4j.learning.sync.qlearning.QLearning;
import org.deeplearning4j.rl4j.learning.sync.qlearning.discrete.QLearningDiscreteDense;
import org.deeplearning4j.rl4j.network.dqn.DQNFactoryStdDense;
import org.deeplearning4j.rl4j.policy.DQNPolicy;
import org.deeplearning4j.rl4j.util.DataManager;
import org.nd4j.linalg.learning.config.Adam;

import java.io.IOException;

@Slf4j
public class BlackjackMain {


    public static QLearning.QLConfiguration QL_CONFIG =
            new QLearning.QLConfiguration(
                    123,   //Random seed
                    1000000,//Max step By epoch 批次下最大执行的步数
                    5000000, //Max step            总执行的部署
                    100000, //Max size of experience replay 记忆数据
                    1000,    //size of batches
                    1000,   //target update (hard) 每10次更新一次参数
                    0,     //num step noop warmup   步数从0开始
                    0.05,  //reward scaling
                    0.99,  //gamma
                    10.0,  //td-error clipping
                    0.1f,  //min epsilon
                    100,  //num step for eps greedy anneal
                    true   //double DQN
            );


    public static DQNFactoryStdDense.Configuration DQN_NET =
            DQNFactoryStdDense.Configuration.builder()
                    .l2(0.01)
                    .updater(new Adam(1e-2))
                    .numLayer(3)
                    .numHiddenNodes(32)
                    .build();


    public static void running() throws IOException {

        //record the training data in rl4j-data in a new folder
        DataManager manager = new DataManager(true);

        //define the mdp from toy (toy length)
        BlackjackEnv mdp = new BlackjackEnv();

        //define the training method
        QLearningDiscreteDense<BlackjackState> dql = new QLearningDiscreteDense<>(mdp, DQN_NET, QL_CONFIG, manager);

        //start the training
        dql.train();

        //get the final policy
        DQNPolicy<BlackjackState> policy = dql.getPolicy();

        //serialize and save (serialization showcase, but not required)
        policy.save("blackjack.policy");

        for (int i = 0; i < 10; i++) {
            mdp.reset();

            double reward = policy.play(mdp);
            log.info("reward-->{}", reward);
        }

        //useless on toy but good practice!
        mdp.close();

    }

    public static void testing() throws IOException {

        //load the previous agent
        DQNPolicy<BlackjackState> policy = DQNPolicy.load("blackjack.policy");

        //define the mdp from toy (toy length)
        BlackjackEnv mdp = new BlackjackEnv();

        for (int i = 0; i < 10; i++) {
            mdp.reset();
            double reward = policy.play(mdp);
            System.out.println(reward);
        }
    }

    public static void main(String[] args) throws IOException {
        running();
//        testing();
    }
}
