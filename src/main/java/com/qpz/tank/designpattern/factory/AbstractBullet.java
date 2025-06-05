package com.qpz.tank.designpattern.factory;

import com.qpz.tank.designpattern.*;

import java.awt.*;

/**
 * 子弹抽象类
 *
 * @author pengzhan.qian
 * @since 2025/6/5 9:34
 **/
public abstract class AbstractBullet {
    /**
     * 创建子弹
     *
     * @param x     子弹坐标X
     * @param y     子弹坐标Y
     * @param dir   子弹方向
     * @param group 子弹群组
     * @param tf    坦克游戏引用
     * @return AbstractBullet
     */
    public abstract AbstractBullet createBullet(int x, int y, DpDir dir, DpGroup group, DpTankFrame tf);

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
