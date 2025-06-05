package com.qpz.tank.designpattern.factory;

import com.qpz.tank.designpattern.DpTankFrame;

import java.awt.*;

/**
 * 爆炸抽象类
 *
 * @author pengzhan.qian
 * @since 2025/6/5 9:30
 **/
public abstract class AbstractExplode {

    /**
     * 创建爆炸
     *
     * @param x    爆炸坐标X
     * @param y    爆炸坐标Y
     * @param dpTf 坦克游戏引用
     * @return AbstractExplode 爆炸
     */
    public abstract AbstractExplode createExplode(int x, int y, DpTankFrame dpTf);

    /**
     * 不断刷新爆炸
     *
     * @param g 画笔
     */
    public abstract void paint(Graphics g);


    /**
     * 爆炸消亡
     */
    public abstract void die();
}
