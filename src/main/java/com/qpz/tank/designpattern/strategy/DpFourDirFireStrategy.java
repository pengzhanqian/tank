package com.qpz.tank.designpattern.strategy;

import com.qpz.tank.designpattern.*;
import com.qpz.tank.designpattern.factory.AbstractTank;
import com.qpz.tank.designpattern.factory.RectBullet;

/**
 * 4个方向同时开火
 *
 * @author pengzhan.qian
 * @since 2025/6/4 15:27
 **/
public class DpFourDirFireStrategy implements DpFireStrategy {
    /**
     * 开火
     *
     * @param t 坦克
     */
    @Override
    public void fire(AbstractTank t) {
        int bX = t.getX() + t.getWidth() / 2 - RectBullet.BULLET_WIDTH / 2;
        int bY = t.getY() + t.getHeight() / 2 - RectBullet.BULLET_HEIGHT / 2;
        DpDir[] dirs = DpDir.values();
        for (DpDir dir : dirs) {
            new RectBullet(bX, bY, dir, t.group(), t.dpTf());
        }
        if (t.group() == DpGroup.GOOD) new Thread(() -> new DpAudio("audio/tank_fire.wav").play()).start();
    }
}
