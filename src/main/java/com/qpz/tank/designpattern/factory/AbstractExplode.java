package com.qpz.tank.designpattern.factory;

import java.awt.*;

/**
 * 爆炸抽象类
 *
 * @author pengzhan.qian
 * @since 2025/6/5 9:30
 **/
public abstract class AbstractExplode {


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
