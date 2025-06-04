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
    // 子弹的移动速度
    public static final int speed = DpPropertyMgr.getIntProperty("bulletSpeed");
    @Serial
    private static final long serialVersionUID = -9091245601031262535L;
    public Rectangle rect = new Rectangle();
    // 子弹的位置
    public int x;
    public int y;
    // 子弹的方向
    public DpDir dir;
    // 子弹是否存活
    public boolean living = true;
    // 持有坦克框的引用
    public DpTankFrame tf;
    public DpGroup group;

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

        // 子弹加入列表
        tf.bullets.add(this);
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
        if (this.group == tank.group) return;
        if (this.rect.intersects(tank.rect)) {
            tank.die();
            this.die();
            int ex = tank.x + DpTank.TANK_WIDTH / 2 - DpExplode.EXPLODE_WIDTH / 2;
            int ey = tank.y + DpTank.TANK_HEIGHT / 2 - DpExplode.EXPLODE_HEIGHT / 2;
            tf.explodes.add(new DpExplode(ex, ey, tf));
        }
    }

    private void die() {
        this.living = false;
    }
}
