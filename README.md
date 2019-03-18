# rl4j-demo

## 介绍
框架采用了deeplearning4j rl4j框架。

## 游戏介绍


| - - - - - |    
  1 2 3 4 5 
  
  
如上图所示，游戏开始时是在最左侧的位置，当向右移动一格时就得1分，向左移动就减1分。训练如何快速得到最高分。

```

20:01:09.091 [main] INFO com.codingapi.rl4j.demo.MyTest - reward-->5.0
20:01:09.091 [main] INFO com.codingapi.rl4j.demo.MyGame - step->5,reward->5
20:01:09.092 [main] INFO com.codingapi.rl4j.demo.MyGame - step->5,reward->5
20:01:09.092 [main] INFO com.codingapi.rl4j.demo.MyTest - reward-->5.0
20:01:09.092 [main] INFO com.codingapi.rl4j.demo.MyGame - step->5,reward->5
20:01:09.092 [main] INFO com.codingapi.rl4j.demo.MyGame - step->5,reward->5
20:01:09.092 [main] INFO com.codingapi.rl4j.demo.MyTest - reward-->5.0
20:01:09.093 [main] INFO com.codingapi.rl4j.demo.MyGame - step->5,reward->5
20:01:09.093 [main] INFO com.codingapi.rl4j.demo.MyGame - step->5,reward->5
20:01:09.093 [main] INFO com.codingapi.rl4j.demo.MyTest - reward-->5.0
20:01:09.093 [main] INFO com.codingapi.rl4j.demo.MyGame - step->5,reward->5
20:01:09.093 [main] INFO com.codingapi.rl4j.demo.MyGame - step->5,reward->5

```

没有提供可视化的界面直接看打印结果，step->步数,reward->得分




DQN 原理介绍
https://rubenfiszel.github.io/posts/rl4j/2016-08-24-Reinforcement-Learning-and-DQN.html


更多Rl4J 见
https://github.com/deeplearning4j/dl4j-examples/tree/master/rl4j-examples




