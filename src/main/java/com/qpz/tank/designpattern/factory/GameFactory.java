package com.qpz.tank.designpattern.factory;

/**
 * 坦克游戏的工厂
 *
 * @author pengzhan.qian
 * @since 2025/6/5 10:52
 **/
public abstract class GameFactory {

    public abstract AbstractTank createTank();
}
