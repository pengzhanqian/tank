package com.qpz.tank.model;

import com.qpz.tank.ResourceMgr;
import com.qpz.tank.TankFrame;
import com.qpz.tank.enums.DirEnum;
import com.qpz.tank.enums.Group;

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

    // 子弹的大小
    public static final int width = ResourceMgr.bulletD.getWidth();
    public static final int height = ResourceMgr.bulletD.getHeight();
    @Serial
    private static final long serialVersionUID = -9091245601031262535L;
    // 子弹的移动速度
    private static final int speed = 6;
    // 子弹的位置
    private int x;
    private int y;
    // 子弹的方向
    private DirEnum dir;
    // 子弹是否存活
    private boolean living = true;
    // 持有坦克框的引用
    private TankFrame tf;
    private Group group = Group.BAD;

    public Bullet(int x, int y, DirEnum dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
    }

    public void paint(Graphics g) {
        if (!living) {
            tf.bullets.remove(this);
        }
//        Color c = g.getColor();
//        // 红色子弹
//        g.setColor(Color.RED);
//        // 画一个圆形表示子弹
//        g.fillOval(x + 20, y + 20, width, height);
//        g.setColor(c);

        // 使用图片替代子弹
        switch (dir) {
            case LEFT -> g.drawImage(ResourceMgr.bulletL, x, y, null);
            case RIGHT -> g.drawImage(ResourceMgr.bulletR, x, y, null);
            case UP -> g.drawImage(ResourceMgr.bulletU, x, y, null);
            case DOWN -> g.drawImage(ResourceMgr.bulletD, x, y, null);
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

        if (x < 0 || y < 0 || x > TankFrame.WIDTH || y > TankFrame.HEIGHT) {
            living = false;
        }
    }

    /**
     * 简单实现的碰撞检测
     * 本身是子弹
     *
     * @param tank 坦克
     */
    public void collideWith(Tank tank) {
        // 分组相同 则不用检测
        if (this.group == tank.getGroup()) return;

        // TODO Rectangle 对象每次检测都new1个太多了  作为坦克或者子弹的属性去处理
        Rectangle rectangle = new Rectangle(this.x, this.y, Bullet.width, Bullet.height);
        Rectangle tankRectangle = new Rectangle(tank.getX(), tank.getY(), Tank.width, Tank.height);
        if (rectangle.intersects(tankRectangle)) {
            tank.die();
            this.die();
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

    public DirEnum getDir() {
        return dir;
    }

    public void setDir(DirEnum dir) {
        this.dir = dir;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public TankFrame getTf() {
        return tf;
    }

    public void setTf(TankFrame tf) {
        this.tf = tf;
    }
}
