package com.qpz.tank;

import java.awt.*;

/**
 * @author pengzhan.qian
 * @since 2025/6/3 9:50
 **/
public class Explode {

    public static final int EXPLODE_WIDTH = ResourceMgr.explodes[0].getWidth();
    public static final int EXPLODE_HEIGHT = ResourceMgr.explodes[0].getHeight();
    // 爆炸图片的位置
    private int x;
    private int y;
    private TankFrame tf = null;

    // 爆炸图片的进度
    private int step = 0;

    public Explode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
        // 爆炸音效
        new Thread(() -> new Audio("audio/explode.wav").play()).start();
    }

    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.explodes[step++], x, y, null);
        if (step >= ResourceMgr.explodes.length) tf.explodes.remove(this);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public TankFrame getTf() {
        return tf;
    }

    public void setTf(TankFrame tf) {
        this.tf = tf;
    }

}
