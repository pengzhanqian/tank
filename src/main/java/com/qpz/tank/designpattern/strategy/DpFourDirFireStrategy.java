package com.qpz.tank.designpattern.strategy;

import com.qpz.tank.designpattern.*;

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
    public void fire(DpTank t) {
        int bX = t.x + DpTank.TANK_WIDTH / 2 - DpBullet.BULLET_WIDTH / 2;
        int bY = t.y + DpTank.TANK_HEIGHT / 2 - DpBullet.BULLET_HEIGHT / 2;

        DpDir[] dirs = DpDir.values();
        for (DpDir dir : dirs) {
            new DpBullet(bX, bY, dir, t.group, t.tf);
        }

        if (t.group == DpGroup.GOOD) new Thread(() -> new DpAudio("audio/tank_fire.wav").play()).start();
    }
}
