package com.qpz.tank;

import com.qpz.tank.enums.DirEnum;
import com.qpz.tank.enums.Group;
import com.qpz.tank.model.Tank;

import java.util.Objects;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();
        //初始化敌方坦克
        int initTankCount = Integer.parseInt((String) Objects.requireNonNull(PropertyMgr.getProperty("initTankCount")));
        for (int i = 0; i < initTankCount; i++) {
            tf.tanks.add(new Tank(50 + i * 80, 200, DirEnum.DOWN, Group.BAD, tf));
        }

        // 音效
        new Thread(() -> new Audio("audio/war1.wav").loop()).start();

        while (true) {
            Thread.sleep(50);
            tf.repaint();
        }
    }
}