package com.qpz.tank.designpattern.strategy;

import com.qpz.tank.designpattern.factory.AbstractTank;

/**
 * 开火策略
 *
 * @author pengzhan.qian
 * @since 2025/6/4 14:45
 **/
public interface DpFireStrategy {


    /**
     * 开火
     *
     * @param t 坦克
     */
    void fire(AbstractTank t);
}
