package com.qpz.tank.net;

import com.qpz.tank.single.Audio;
import com.qpz.tank.single.TankFrame;

public class NetMain {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();

        // 音效
        new Thread(() -> new Audio("audio/war1.wav").loop()).start();

        while (true) {
            Thread.sleep(25);
            tf.repaint();
        }
    }
}