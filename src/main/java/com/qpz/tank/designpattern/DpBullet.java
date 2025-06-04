package com.qpz.tank.designpattern;


import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

/**
 * 子弹类
 *
 * @author pengzhan.qian
 * @since 2025/5/30 14:19
 **/
public class DpBullet implements Serializable {

    // 子弹的大小
    public static final int BULLET_WIDTH = DpResourceMgr.bulletD.getWidth();
    public static final int BULLET_HEIGHT = DpResourceMgr.bulletD.getHeight();
    @Serial
    private static final long serialVersionUID = -9091245601031262535L;
    // 子弹的移动速度
    private static final int speed = DpPropertyMgr.getIntProperty("bulletSpeed");
    Rectangle rect = new Rectangle();
    // 子弹的位置
    private int x;
    private int y;
    // 子弹的方向
    private DpDir dir;
    // 子弹是否存活
    private boolean living = true;
    // 持有坦克框的引用
    private DpTankFrame tf;
    private DpGroup group;

    public DpBullet(int x, int y, DpDir dir, DpGroup group, DpTankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
        rect.x = this.x;
        rect.y = this.y;
        rect.width = BULLET_WIDTH;
        rect.height = BULLET_HEIGHT;
    }

    public void paint(Graphics g) {
        if (!living) {
            tf.bullets.remove(this);
        }
        // 使用图片替代子弹
        switch (dir) {
            case LEFT -> g.drawImage(DpResourceMgr.bulletL, x, y, null);
            case RIGHT -> g.drawImage(DpResourceMgr.bulletR, x, y, null);
            case UP -> g.drawImage(DpResourceMgr.bulletU, x, y, null);
            case DOWN -> g.drawImage(DpResourceMgr.bulletD, x, y, null);
        }
        // 调用移动方法
        move();
    }

    public void move() {
        if (!living) {
            return;
        }
        // 每次paint都改变1次方向  封装成移动方法
        switch (dir) {
            case LEFT -> x -= speed;
            case UP -> y -= speed;
            case RIGHT -> x += speed;
            case DOWN -> y += speed;
        }

        if (x < 0 || y < 0 || x > DpTankFrame.GAME_WIDTH || y > DpTankFrame.GAME_HEIGHT) {
            living = false;
        }

        //update rect
        rect.x = this.x;
        rect.y = this.y;
    }

    /**
     * 简单实现的碰撞检测
     * 本身是子弹
     *
     * @param tank 坦克
     */
    public void collideWith(DpTank tank) {
        // 分组相同 则不用检测
        if (this.group == tank.getGroup()) return;
        if (this.rect.intersects(tank.rect)) {
            tank.die();
            this.die();
            int ex = tank.getX() + DpTank.TANK_WIDTH / 2 - DpExplode.EXPLODE_WIDTH / 2;
            int ey = tank.getY() + DpTank.TANK_HEIGHT / 2 - DpExplode.EXPLODE_HEIGHT / 2;
            tf.explodes.add(new DpExplode(ex, ey, tf));
        }
    }

    private void die() {
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

    public DpDir getDir() {
        return dir;
    }

    public void setDir(DpDir dir) {
        this.dir = dir;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public DpGroup getGroup() {
        return group;
    }

    public void setGroup(DpGroup group) {
        this.group = group;
    }

    public DpTankFrame getTf() {
        return tf;
    }

    public void setTf(DpTankFrame tf) {
        this.tf = tf;
    }
}
