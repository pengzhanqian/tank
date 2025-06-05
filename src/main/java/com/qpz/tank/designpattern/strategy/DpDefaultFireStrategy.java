package com.qpz.tank.designpattern.strategy;

import com.qpz.tank.designpattern.DpAudio;
import com.qpz.tank.designpattern.DpGroup;
import com.qpz.tank.designpattern.factory.AbstractTank;
import com.qpz.tank.designpattern.factory.RectBullet;

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
    public void fire(AbstractTank t) {
        int bx = t.getX() + t.getWidth() / 2 - RectBullet.BULLET_WIDTH / 2;
        int by = t.getY() + t.getHeight() / 2 - RectBullet.BULLET_HEIGHT / 2;
        new RectBullet(bx, by, t.dir(), t.group(), t.dpTf());
        // 增加坦克开火音效
        if (t.group() == DpGroup.GOOD) new Thread(() -> new DpAudio("audio/tank_fire.wav").play()).start();

    }
}
