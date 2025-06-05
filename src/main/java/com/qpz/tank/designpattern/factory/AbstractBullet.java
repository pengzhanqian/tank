package com.qpz.tank.designpattern.factory;

import java.awt.*;

/**
 * 子弹抽象类
 *
 * @author pengzhan.qian
 * @since 2025/6/5 9:34
 **/
public abstract class AbstractBullet {
    /**
     * 不断刷新子弹位置和方向
     *
     * @param g 画笔
     */
    public abstract void paint(Graphics g);

    /**
     * 子弹移动
     */
    public abstract void move();

    /**
     * 子弹和坦克的碰撞检测
     *
     * @param tank 抽象坦克
     */
    public abstract void collideWith(AbstractTank tank);

    /**
     * 子弹消亡
     */
    public abstract void die();


}
