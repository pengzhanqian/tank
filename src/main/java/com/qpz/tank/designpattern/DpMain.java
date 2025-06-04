package com.qpz.tank.designpattern;

import javax.swing.*;

public class DpMain {
    public static void main(String[] args) throws InterruptedException {
        DpTankFrame dpTf = new DpTankFrame();
        initBadTanks(dpTf);

        // 音效
        new Thread(() -> new DpAudio("audio/war1.wav").loop()).start();

        boolean tankSpeedAddFlag = false;
        while (dpTf.endFlag) {
            Thread.sleep(25);
            dpTf.repaint();
            if (dpTf.tanks.size() < 5 && !tankSpeedAddFlag) {
                dpTf.myTank.speed = dpTf.myTank.speed + 1;
                tankSpeedAddFlag = true;
            }
            if (dpTf.tanks.isEmpty()) {
                int choice = JOptionPane.showOptionDialog(
                        dpTf,
                        "恭喜获胜！是否继续游戏？",
                        "游戏结束",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"继续游戏", "结束游戏"},
                        "继续游戏"
                );
                dpTf.end();
                if (choice == 0) {
                    initBadTanks(dpTf);
                    dpTf.reInit();
                } else {
                    System.exit(0);
                }

            }
        }
    }

    /**
     * 初始化敌方坦克
     *
     * @param dpTf
     */
    private static void initBadTanks(DpTankFrame dpTf) {
        //初始化敌方坦克
        int initTankCount = DpPropertyMgr.getIntProperty("initTankCount");
        for (int i = 0; i < initTankCount; i++) {
            dpTf.tanks.add(new DpTank(50 + i * 80, 200, true, DpDir.DOWN, DpGroup.BAD, dpTf));
        }
    }
}