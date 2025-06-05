package com.qpz.tank.designpattern;

import com.qpz.tank.designpattern.factory.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 使用CopyOnWriteArrayList等并发集合  防止多线程下1个线程遍历一个线程删除导致报错  java.util.ConcurrentModificationException
 *
 * @author pengzhan.qian
 * @since 2025/5/29 16:36
 **/
public class DpTankFrame extends Frame {

    public static final int GAME_WIDTH = DpPropertyMgr.getIntProperty("gameWidth");
    public static final int GAME_HEIGHT = DpPropertyMgr.getIntProperty("gameHeight");
    public RectFactory rf = new RectFactory();
    public RectTank myTank = (RectTank) rf.createTank(RectTank.INIT_X, RectTank.INIT_Y, false, RectTank.INIT_DIR, DpGroup.GOOD, this);
    public List<AbstractBullet> bullets = new CopyOnWriteArrayList<>();
    public List<AbstractTank> tanks = new CopyOnWriteArrayList<>();
    public List<AbstractExplode> explodes = new CopyOnWriteArrayList<>();
    public Image offScreenImage = null;
    public boolean endFlag = true;

    public DpTankFrame() throws HeadlessException {
        this.setVisible(true);
        // 单位 像素
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setResizable(false);
        this.setTitle("Design Pattern Tank War (坦克大战)");
        System.out.println("Design Pattern Tank War Window is init.");
        this.addWindowListener(new WindowAdapter() {
            // 关闭窗口
            public void windowClosing(WindowEvent e) {
                System.out.println("Design Pattern Tank War Window is closed");
                System.exit(0);
            }
        });
        this.addKeyListener(new MyKeyListener());

    }

    /**
     * 游戏中的概念 解决闪烁问题  双缓冲
     */
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量为" + bullets.size() + ", 主战坦克的坐标: (" + myTank.getX() + ", " + myTank.getY() + " ), 方向:" + myTank.dir().name(), 10, 60);
        g.drawString("敌方坦克数量: " + tanks.size(), 10, 80);
        g.drawString("爆炸的数量: " + tanks.size(), 10, 100);
        g.drawString("主战坦克的速度: " + myTank.speed, 10, 120);
        g.setColor(c);
        myTank.paint(g);
        if (!bullets.isEmpty()) {
            // 防止内存泄露
            for (AbstractBullet bullet : bullets) {
                bullet.paint(g);
            }
        }
        if (!tanks.isEmpty()) {
            for (AbstractTank tank : tanks) {
                tank.paint(g);
            }
        }
        if (!explodes.isEmpty()) {
            for (AbstractExplode explode : explodes) {
                explode.paint(g);
            }
        }
        if (!bullets.isEmpty() && !tanks.isEmpty()) {
            for (AbstractBullet bullet : bullets) {
                for (AbstractTank tank : tanks) {
                    bullet.collideWith(tank);
                }
            }
        }
    }

    public void end() {
        this.endFlag = false;
    }

    public void reInit() {
        this.endFlag = true;
        this.myTank.speed = DpPropertyMgr.getIntProperty("tankSpeed");
        this.bullets = new CopyOnWriteArrayList<>();
        this.myTank.reInit();
    }


    class MyKeyListener extends KeyAdapter {

        boolean bl = false;
        boolean bu = false;
        boolean br = false;
        boolean bd = false;

        @Override
        public void keyPressed(KeyEvent e) {
            // 确认是哪个键
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    //x -= 10;
                    bl = true;
                    break;
                case KeyEvent.VK_UP:
                    bu = true;
                    //y -= 10;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = true;
                    //x += 10;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = true;
                    //y += 10;
                    break;
            }

            // 键按下去改变主战坦克方向
            setMainTankDir();

            // 键按下去会出现坦克移动音效
            new Thread(() -> new DpAudio("audio/tank_move.wav").play()).start();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // 1个建被抬起来的时候调用
            // 确认是哪个键
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    //x -= 10;
                    bl = false;
                    break;
                case KeyEvent.VK_UP:
                    bu = false;
                    //y -= 10;
                    break;
                case KeyEvent.VK_RIGHT:
                    br = false;
                    //x += 10;
                    break;
                case KeyEvent.VK_DOWN:
                    bd = false;
                    //y += 10;
                    break;
                case KeyEvent.VK_CONTROL:
                    // 坦克发出一颗子弹
                    myTank.fire();
                    break;
            }

            // 键抬起去改变主战坦克方向
            setMainTankDir();
        }


        private void setMainTankDir() {
            if (!bl && !bu && !br && !bd) {
                myTank.moving = false;
            } else {
                myTank.moving = true;
                if (bl) myTank.dir = DpDir.LEFT;
                if (bu) myTank.dir = DpDir.UP;
                if (br) myTank.dir = DpDir.RIGHT;
                if (bd) myTank.dir = DpDir.DOWN;
            }
        }
    }
}
