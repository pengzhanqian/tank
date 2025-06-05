package com.qpz.tank.designpattern.factory;

import com.qpz.tank.designpattern.*;

import java.awt.*;

/**
 * 矩形的子弹 - 归属于产品族 - 矩形族
 * 替代 DpBullet
 *
 * @author pengzhan.qian
 * @since 2025/6/5 10:20
 **/
public class RectBullet extends AbstractBullet {
    // 子弹的大小
    public static final int BULLET_WIDTH = DpResourceMgr.bulletD.getWidth();
    public static final int BULLET_HEIGHT = DpResourceMgr.bulletD.getHeight();
    // 子弹的移动速度
    public static final int speed = DpPropertyMgr.getIntProperty("bulletSpeed");
    public Rectangle rect = new Rectangle();
    // 子弹的位置
    public int x;
    public int y;
    // 子弹的方向
    public DpDir dir;
    // 持有坦克框的引用
    public DpTankFrame tf;
    public DpGroup group;
    // 子弹是否存活
    private boolean living = true;

    public RectBullet(int x, int y, DpDir dir, DpGroup group, DpTankFrame tf) {
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

    /**
     * 不断刷新子弹位置和方向
     *
     * @param g 画笔
     */
    @Override
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

    /**
     * 子弹移动
     */
    @Override
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
     * 子弹和坦克的碰撞检测
     *
     * @param tank 抽象坦克
     */
    @Override
    public void collideWith(AbstractTank tank) {
        // 分组相同 则不用检测
        if (this.group == tank.group()) return;
        if (this.rect.intersects(tank.rect())) {
            tank.die();
            this.die();
            int ex = tank.getX() + tank.getWidth() / 2 - RectExplode.EXPLODE_WIDTH / 2;
            int ey = tank.getY() + tank.getHeight() / 2 - RectExplode.EXPLODE_HEIGHT / 2;
            tf.explodes.add(new RectExplode(ex, ey, tf));
        }
    }

    /**
     * 子弹消亡
     */
    @Override
    public void die() {
        this.living = false;
    }
}
