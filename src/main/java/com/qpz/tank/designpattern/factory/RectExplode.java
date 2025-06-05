package com.qpz.tank.designpattern.factory;

import com.qpz.tank.designpattern.*;

import java.awt.*;

/**
 * 矩形的爆炸图片 - 归属于产品族 - 矩形族
 * 替代 DpExplode 类
 *
 * @author pengzhan.qian
 * @since 2025/6/5 10:01
 **/
public class RectExplode extends AbstractExplode {

    public static final int EXPLODE_WIDTH = DpResourceMgr.explodes[0].getWidth();
    public static final int EXPLODE_HEIGHT = DpResourceMgr.explodes[0].getHeight();
    // 爆炸图片的位置
    public int x;
    public int y;
    public DpTankFrame tf;
    // 爆炸图片的进度
    private int step = 0;

    public RectExplode(int x, int y, DpTankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
        // 爆炸音效
        new Thread(() -> new DpAudio("audio/explode.wav").play()).start();
    }

    /**
     * 不断刷新爆炸
     *
     * @param g 画笔
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(DpResourceMgr.explodes[step++], x, y, null);
        die();
    }

    /**
     * 爆炸消亡
     */
    @Override
    public void die() {
        if (step >= DpResourceMgr.explodes.length) {
            step = 0;
            tf.explodes.remove(this);
        }
    }
}
