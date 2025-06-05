package com.qpz.tank.designpattern.factory;

import com.qpz.tank.designpattern.*;

/**
 * @author pengzhan.qian
 * @since 2025/6/5 11:10
 **/
public class RectFactory extends GameFactory {
    @Override
    public AbstractTank createTank(int x, int y, boolean moving, DpDir dir, DpGroup group, DpTankFrame tf) {
        return new RectTank(x, y, moving, dir, group, tf);
    }

    @Override
    public AbstractBullet createBullet(int x, int y, DpDir dir, DpGroup group, DpTankFrame tf) {
        return new RectBullet(x, y, dir, group, tf);
    }

    @Override
    public AbstractExplode createExplode(int x, int y, DpTankFrame dpTf) {
        return new RectExplode(x, y, dpTf);
    }
}
