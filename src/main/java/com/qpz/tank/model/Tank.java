package com.qpz.tank.model;

import com.qpz.tank.*;
import com.qpz.tank.enums.DirEnum;
import com.qpz.tank.enums.Group;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * @author pengzhan.qian
 * @since 2025/5/30 11:08
 **/
public class Tank implements Serializable {
    // 坦克的大小
    public static final int TANK_WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int TANK_HEIGHT = ResourceMgr.goodTankU.getHeight();
    @Serial
    private static final long serialVersionUID = -4327244887078026900L;
    // 坦克的移动速度
    private static final int speed = 2;
    // 坦克的位置
    private int x;
    private int y;
    // 坦克的方向
    private DirEnum dir;
    private TankFrame tf;
    // 坦克是否移动
    private boolean moving = true;
    // 坦克灭亡  默认存活
    private boolean living = true;
    private Group group;
    private Random random = new Random();

    public Tank(int x, int y, DirEnum dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
    }

    public void paint(Graphics g) {
        if (!living) {
            tf.tanks.remove(this);
        }
        // 使用图片替代坦克
        switch (dir) {
            case LEFT -> {
                if (this.group == Group.GOOD) {
                    g.drawImage(ResourceMgr.goodTankL, x, y, null);
                } else if (this.group == Group.BAD) {
                    g.drawImage(ResourceMgr.badTankL, x, y, null);
                }
            }
            case RIGHT -> {
                if (this.group == Group.GOOD) {
                    g.drawImage(ResourceMgr.goodTankR, x, y, null);
                } else if (this.group == Group.BAD) {
                    g.drawImage(ResourceMgr.badTankR, x, y, null);
                }
            }
            case UP -> {
                if (this.group == Group.GOOD) {
                    g.drawImage(ResourceMgr.goodTankU, x, y, null);
                } else if (this.group == Group.BAD) {
                    g.drawImage(ResourceMgr.badTankU, x, y, null);
                }
            }
            case DOWN -> {
                if (this.group == Group.GOOD) {
                    g.drawImage(ResourceMgr.goodTankD, x, y, null);
                } else if (this.group == Group.BAD) {
                    g.drawImage(ResourceMgr.badTankD, x, y, null);
                }
            }
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
        // 增加了随机开火
        // 增加主坦克不参与随机  敌方坦克 5% 随机开火
        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            this.fire();
        }
        // 增加敌方坦克移动的时候随机方向  并且增加概率变方向
        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            randomDir();
        }
    }

    private void randomDir() {
        this.dir = DirEnum.values()[random.nextInt(DirEnum.values().length)];
    }


    public void fire() {
        int bx = this.x + Tank.TANK_WIDTH / 2 - Bullet.BULLET_WIDTH / 2;
        int by = this.y + Tank.TANK_HEIGHT / 2 - Bullet.BULLET_HEIGHT / 2;
        tf.bullets.add(new Bullet(bx, by, this.dir, this.group, this.tf));
        // 增加坦克开火音效
        if (this.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
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

    public TankFrame getTf() {
        return tf;
    }

    public void setTf(TankFrame tf) {
        this.tf = tf;
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
}
