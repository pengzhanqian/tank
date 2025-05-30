package com.qpz.tank.model;

import com.qpz.tank.TankFrame;
import com.qpz.tank.enums.DirEnum;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * @author pengzhan.qian
 * @since 2025/5/30 11:08
 **/
public class Tank implements Serializable {
    @Serial
    private static final long serialVersionUID = -4327244887078026900L;
    // 子弹的大小
    private static final int width = 50;
    private static final int height = 50;
    // 坦克的移动速度
    private static final int speed = 5;
    // 坦克的位置
    private int x;
    private int y;
    // 坦克的方向
    private DirEnum dir;
    private TankFrame tf = null;
    // 坦克是否移动
    private boolean moving = false;

    public Tank(int x, int y, DirEnum dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        // 画一个矩形表示坦克
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        g.setColor(c);
        // 调用移动方法
        move();
    }

    public void move() {
        if (!this.moving) return;
        // 每次paint都改变1次方向  封装成移动方法
        switch (dir) {
            case LEFT -> x -= speed;
            case UP -> y -= speed;
            case RIGHT -> x += speed;
            case DOWN -> y += speed;
        }
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

    public DirEnum getDir() {
        return dir;
    }

    public void setDir(DirEnum dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void fire() {
        tf.bullets.add(new Bullet(this.x, this.y, this.dir, this.tf));
    }
}
