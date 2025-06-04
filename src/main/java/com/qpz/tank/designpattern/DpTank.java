package com.qpz.tank.designpattern;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Random;

/**
 * @author pengzhan.qian=
 * @since 2025/5/30 11:08
 **/
public class DpTank implements Serializable {
    // 坦克的大小
    public static final int TANK_WIDTH = DpResourceMgr.goodTankU1.getWidth();
    public static final int TANK_HEIGHT = DpResourceMgr.goodTankU1.getHeight();
    @Serial
    private static final long serialVersionUID = -4327244887078026900L;
    Rectangle rect = new Rectangle();
    // 坦克的移动速度
    private int speed;
    // 坦克的位置
    private int x;
    private int y;
    // 坦克的方向
    private DpDirEnum dir;
    private DpTankFrame tf;
    // 坦克是否移动
    private boolean moving;
    // 坦克灭亡  默认存活
    private boolean living = true;
    private DpGroup group;
    private Random random = new Random();

    public DpTank(int x, int y, boolean moving, DpDirEnum dir, DpGroup group, DpTankFrame tf) {
        this.x = x;
        this.y = y;
        this.moving = moving;
        this.dir = dir;
        this.tf = tf;
        this.group = group;
        this.speed = DpPropertyMgr.getIntProperty("tankSpeed");

        rect.x = this.x;
        rect.y = this.y;
        rect.width = TANK_WIDTH;
        rect.height = TANK_HEIGHT;
    }

    public void paint(Graphics g) {
        if (!living) {
            tf.tanks.remove(this);
        }
        // 每大于2秒 刷新一下坦克
        boolean evenFlag = System.currentTimeMillis() / 1000 % 2 == 0;
        // 使用图片替代坦克
        switch (dir) {
            case LEFT -> {
                if (this.group == DpGroup.GOOD) {
                    g.drawImage(evenFlag ? DpResourceMgr.goodTankL1 : DpResourceMgr.goodTankL2, x, y, null);
                } else if (this.group == DpGroup.BAD) {
                    g.drawImage(evenFlag ? DpResourceMgr.badTankL1 : DpResourceMgr.badTankL2, x, y, null);
                }
            }
            case RIGHT -> {
                if (this.group == DpGroup.GOOD) {
                    g.drawImage(evenFlag ? DpResourceMgr.goodTankR1 : DpResourceMgr.goodTankR2, x, y, null);
                } else if (this.group == DpGroup.BAD) {
                    g.drawImage(evenFlag ? DpResourceMgr.badTankR1 : DpResourceMgr.badTankR2, x, y, null);
                }
            }
            case UP -> {
                if (this.group == DpGroup.GOOD) {
                    g.drawImage(evenFlag ? DpResourceMgr.goodTankU1 : DpResourceMgr.goodTankU2, x, y, null);
                } else if (this.group == DpGroup.BAD) {
                    g.drawImage(evenFlag ? DpResourceMgr.badTankU1 : DpResourceMgr.badTankU2, x, y, null);
                }
            }
            case DOWN -> {
                if (this.group == DpGroup.GOOD) {
                    g.drawImage(evenFlag ? DpResourceMgr.goodTankD1 : DpResourceMgr.goodTankD2, x, y, null);
                } else if (this.group == DpGroup.BAD) {
                    g.drawImage(evenFlag ? DpResourceMgr.badTankD1 : DpResourceMgr.badTankD2, x, y, null);
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
        if (this.group == DpGroup.BAD && random.nextInt(100) > 95) {
            this.fire();
        }
        // 增加敌方坦克移动的时候随机方向  并且增加概率变方向
        if (this.group == DpGroup.BAD && random.nextInt(100) > 95) {
            randomDir();
        }
        boundsCheck();
        //update rect
        rect.x = this.x;
        rect.y = this.y;
    }

    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > DpTankFrame.GAME_WIDTH - DpTank.TANK_WIDTH - 2) x = DpTankFrame.GAME_WIDTH - DpTank.TANK_WIDTH - 2;
        if (this.y > DpTankFrame.GAME_HEIGHT - DpTank.TANK_HEIGHT - 2)
            y = DpTankFrame.GAME_HEIGHT - DpTank.TANK_HEIGHT - 2;
    }

    private void randomDir() {
        this.dir = DpDirEnum.values()[random.nextInt(DpDirEnum.values().length)];
    }


    public void fire() {
        int bx = this.x + DpTank.TANK_WIDTH / 2 - DpBullet.BULLET_WIDTH / 2;
        int by = this.y + DpTank.TANK_HEIGHT / 2 - DpBullet.BULLET_HEIGHT / 2;
        tf.bullets.add(new DpBullet(bx, by, this.dir, this.group, this.tf));
        // 增加坦克开火音效
        if (this.group == DpGroup.GOOD) new Thread(() -> new DpAudio("audio/tank_fire.wav").play()).start();
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

    public DpDirEnum getDir() {
        return dir;
    }

    public void setDir(DpDirEnum dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public DpTankFrame getTf() {
        return tf;
    }

    public void setTf(DpTankFrame tf) {
        this.tf = tf;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
