package com.qpz.tank.designpattern.strategy;

import com.qpz.tank.designpattern.*;

/**
 * 默认策略 单向开火
 *
 * @author pengzhan.qian
 * @since 2025/6/4 14:48
 **/
public class DpDefaultFireStrategy implements DpFireStrategy {
    /**
     * 开火
     *
     * @param t 坦克
     */
    @Override
    public void fire(DpTank t) {
        int bx = t.x + DpTank.TANK_WIDTH / 2 - DpBullet.BULLET_WIDTH / 2;
        int by = t.y + DpTank.TANK_HEIGHT / 2 - DpBullet.BULLET_HEIGHT / 2;
        t.tf.bullets.add(new DpBullet(bx, by, t.dir, t.group, t.tf));
        // 增加坦克开火音效
        if (t.group == DpGroup.GOOD) new Thread(() -> new DpAudio("audio/tank_fire.wav").play()).start();

    }
}
