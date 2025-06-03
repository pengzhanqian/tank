package com.qpz.tank.dp;

import javax.swing.*;

public class DpMain {
    public static void main(String[] args) throws InterruptedException {
        DpTankFrame dpTf = new DpTankFrame();
        //初始化敌方坦克
        int initTankCount = DpPropertyMgr.getIntProperty("initTankCount");
        for (int i = 0; i < initTankCount; i++) {
            dpTf.tanks.add(new DpTank(50 + i * 80, 200, true, DpDirEnum.DOWN, DpGroup.BAD, dpTf));
        }

        // 音效
        new Thread(() -> new DpAudio("audio/war1.wav").loop()).start();

        boolean endFlag = true;
        boolean tankSpeedAddFlag = false;
        while (endFlag) {
            Thread.sleep(25);
            dpTf.repaint();
            if (dpTf.tanks.size() < 5 && !tankSpeedAddFlag) {
                dpTf.myTank.setSpeed(dpTf.myTank.getSpeed() + 1);
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
                endFlag = false;
                if (choice == 0) {
                    for (int i = 0; i < initTankCount; i++) {
                        dpTf.tanks.add(new DpTank(50 + i * 80, 200, true, DpDirEnum.DOWN, DpGroup.BAD, dpTf));
                    }
                    endFlag = true;
                    dpTf.myTank.setSpeed(DpPropertyMgr.getIntProperty("tankSpeed"));
                } else {
                    System.exit(0);
                }

            }
        }
    }
}