package com.qpz.tank.model;

import com.qpz.tank.ResourceMgr;
import com.qpz.tank.TankFrame;
import com.qpz.tank.enums.DirEnum;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * @author pengzhan.qian
 * @since 2025/5/30 11:08
 **/
public class Tank implements Serializable {
    // 子弹的大小
    public static final int width = ResourceMgr.tankD.getWidth();
    public static final int height = ResourceMgr.tankD.getHeight();
    @Serial
    private static final long serialVersionUID = -4327244887078026900L;
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
    // 坦克灭亡  默认存活
    private boolean living = true;

    private Random random = new Random();

    public Tank(int x, int y, DirEnum dir, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
    }

    public void paint(Graphics g) {
//        Color c = g.getColor();
//        g.setColor(Color.WHITE);
//        // 画一个矩形表示坦克
//        g.fillRect(x, y, width, height);
//        g.setColor(c);
        if (!living) {
            tf.tanks.remove(this);
        }
        // 使用图片替代坦克
        switch (dir) {
            case LEFT -> g.drawImage(ResourceMgr.tankL, x, y, null);
            case RIGHT -> g.drawImage(ResourceMgr.tankR, x, y, null);
            case UP -> g.drawImage(ResourceMgr.tankU, x, y, null);
            case DOWN -> g.drawImage(ResourceMgr.tankD, x, y, null);
        }

        // 调用移动方法
        move();
    }

    public void move() {
        if (!this.living) return;
        if (!this.moving) return;
        // 每次paint都改变1次方向  封装成移动方法
        switch (dir) {
            case LEFT -> x -= speed;
            case UP -> y -= speed;
            case RIGHT -> x += speed;
            case DOWN -> y += speed;
        }

        if (random.nextInt(10) > 8) {
            this.fire();
        }
    }


    public void fire() {
        int bx = this.x + Tank.width / 2 - Bullet.width / 2;
        int by = this.y + Tank.height / 2 - Bullet.height / 2;
        tf.bullets.add(new Bullet(bx, by, this.dir, this.tf));
    }

    public void die() {
        this.living = false;
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
}
