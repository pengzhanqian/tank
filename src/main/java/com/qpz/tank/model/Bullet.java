package com.qpz.tank.model;

import com.qpz.tank.TankFrame;
import com.qpz.tank.enums.DirEnum;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * 子弹类
 *
 * @author pengzhan.qian
 * @since 2025/5/30 14:19
 **/
public class Bullet implements Serializable {

    @Serial
    private static final long serialVersionUID = -9091245601031262535L;
    // 子弹的大小
    private static final int width = 30;
    private static final int height = 30;
    // 子弹的移动速度
    private static final int speed = 10;
    // 子弹的位置
    private int x;
    private int y;
    // 子弹的方向
    private DirEnum dir;
    // 子弹是否存活
    private boolean live = true;
    // 持有坦克框的引用
    private TankFrame tf;

    public Bullet(int x, int y, DirEnum dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        if (!live) {
            tf.bullets.remove(this);
        }
        Color c = g.getColor();
        // 红色子弹
        g.setColor(Color.RED);
        // 画一个圆形表示子弹
        g.fillOval(x, y, width, height);
        g.setColor(c);
        // 调用移动方法
        move();
    }

    public void move() {
        // 每次paint都改变1次方向  封装成移动方法
        switch (dir) {
            case LEFT -> x -= speed;
            case UP -> y -= speed;
            case RIGHT -> x += speed;
            case DOWN -> y += speed;
        }

        if (x < 0 || y < 0 || x > TankFrame.WIDTH || y > TankFrame.HEIGHT) {
            live = false;
        }
    }
}
