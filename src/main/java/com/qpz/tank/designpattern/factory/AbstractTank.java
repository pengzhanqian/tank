package com.qpz.tank.designpattern.factory;

import com.qpz.tank.designpattern.*;

import java.awt.*;

/**
 * 抽象坦克类
 *
 * @author pengzhan.qian
 * @since 2025/6/4 16:46
 **/
public abstract class AbstractTank {

    public abstract DpGroup group();

    public abstract Rectangle rect();

    public abstract int getX();

    public abstract int getY();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract DpDir dir();

    public abstract DpTankFrame dpTf();

    /**
     * 不断刷新坦克位置和方向
     *
     * @param g 画笔
     */
    public abstract void paint(Graphics g);

    /**
     * 坦克移动
     */
    public abstract void move();

    /**
     * 坦克开火
     */
    public abstract void fire();

    /**
     * 坦克消亡
     */
    public abstract void die();

    /**
     * 重新初始化盘面
     */
    public abstract void reInit();
}
