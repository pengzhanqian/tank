package com.qpz.tank.designpattern.factory;

import com.qpz.tank.designpattern.*;

/**
 * 坦克游戏的工厂
 *
 * @author pengzhan.qian
 * @since 2025/6/5 10:52
 **/
public abstract class GameFactory {

    public abstract AbstractTank createTank(int x, int y, boolean moving, DpDir dir, DpGroup group, DpTankFrame tf);

    public abstract AbstractBullet createBullet(int x, int y, DpDir dir, DpGroup group, DpTankFrame tf);

    public abstract AbstractExplode createExplode(int x, int y, DpTankFrame dpTf);
}
