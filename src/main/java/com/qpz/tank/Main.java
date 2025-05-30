package com.qpz.tank;

import com.qpz.tank.enums.DirEnum;
import com.qpz.tank.model.Tank;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();
        //初始化敌方坦克
        for (int i = 0; i < 5; i++) {
            tf.tanks.add(new Tank(50 + i * 80, 200, DirEnum.DOWN, tf));
        }
        while (true) {
            Thread.sleep(50);
            tf.repaint();
        }
    }
}