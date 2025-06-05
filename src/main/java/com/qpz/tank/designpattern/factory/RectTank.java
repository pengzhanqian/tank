package com.qpz.tank.designpattern.factory;

import com.qpz.tank.designpattern.*;
import com.qpz.tank.designpattern.strategy.DpDefaultFireStrategy;
import com.qpz.tank.designpattern.strategy.DpFireStrategy;

import java.awt.*;
import java.util.Random;

/**
 * @author pengzhan.qian
 * @since 2025/6/5 10:32
 **/
public class RectTank extends AbstractTank {
    // 坦克的大小
    public static final int TANK_WIDTH = DpResourceMgr.goodTankU1.getWidth();
    public static final int TANK_HEIGHT = DpResourceMgr.goodTankU1.getHeight();
    public static final int INIT_X = 540 - TANK_WIDTH;
    public static final int INIT_Y = 960 - TANK_HEIGHT;
    public static final DpDir INIT_DIR = DpDir.UP;
    // 坦克的移动速度
    public int speed;
    // 坦克的位置
    public int x;
    public int y;
    // 坦克的方向
    public DpDir dir;
    public DpTankFrame tf;
    // 坦克是否移动
    public boolean moving;
    public DpGroup group;
    public DpFireStrategy fs;
    public Rectangle rect = new Rectangle();
    // 坦克灭亡  默认存活
    private boolean living = true;
    private Random random = new Random();

    public RectTank(int x, int y, boolean moving, DpDir dir, DpGroup group, DpTankFrame tf) {
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

        if (group == DpGroup.GOOD) {
            String goodFSName = DpPropertyMgr.getProperty("goodFS");
            try {
                fs = (DpFireStrategy) Class.forName(goodFSName).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (group == DpGroup.BAD) {
            String badFsName = DpPropertyMgr.getProperty("badFS");
            try {
                fs = (DpFireStrategy) Class.forName(badFsName).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            fs = new DpDefaultFireStrategy();
        }
    }

    @Override
    public DpGroup group() {
        return this.group;
    }

    @Override
    public Rectangle rect() {
        return this.rect;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public int getWidth() {
        return TANK_WIDTH;
    }

    @Override
    public int getHeight() {
        return TANK_HEIGHT;
    }

    @Override
    public DpDir dir() {
        return this.dir;
    }

    @Override
    public DpTankFrame dpTf() {
        return this.tf;
    }

    /**
     * 创建坦克
     *
     * @param x      坦克坐标X
     * @param y      坦克坐标Y
     * @param moving 是否一开始就移动
     * @param dir    坦克方向
     * @param group  坦克群组
     * @param tf     坦克游戏引用
     * @return AbstractTank
     */
    @Override
    public AbstractTank createTank(int x, int y, boolean moving, DpDir dir, DpGroup group, DpTankFrame tf) {
        return new RectTank(x, y, moving, dir, group, tf);
    }

    /**
     * 不断刷新坦克位置和方向
     *
     * @param g 画笔
     */
    @Override
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

    /**
     * 坦克移动
     */
    @Override
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

    /**
     * 坦克开火
     */
    @Override
    public void fire() {
        fs.fire(this);
    }

    /**
     * 坦克消亡
     */
    @Override
    public void die() {
        this.living = false;
    }

    @Override
    public void reInit() {
        this.x = INIT_X;
        this.y = INIT_Y;
        this.dir = INIT_DIR;
        this.moving = false;
        this.speed = DpPropertyMgr.getIntProperty("tankSpeed");
    }

    /**
     * 边界检测
     */
    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > DpTankFrame.GAME_WIDTH - getWidth() - 2) x = DpTankFrame.GAME_WIDTH - getWidth() - 2;
        if (this.y > DpTankFrame.GAME_HEIGHT - getHeight() - 2)
            y = DpTankFrame.GAME_HEIGHT - getHeight() - 2;
    }

    /**
     * 随机方向
     */
    private void randomDir() {
        this.dir = DpDir.values()[random.nextInt(DpDir.values().length)];
    }
}
