package com.qpz.tank.designpattern;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author pengzhan.qian
 * @since 2025/5/29 16:36
 **/
public class DpTankFrame extends Frame {

    public static final int GAME_WIDTH = DpPropertyMgr.getIntProperty("gameWidth");
    public static final int GAME_HEIGHT = DpPropertyMgr.getIntProperty("gameHeight");
    // 批量子弹 使用容器  使用CopyOnWriteArrayList等并发集合  防止多线程下1个线程遍历一个线程删除导致报错  java.util.ConcurrentModificationException
    public List<DpBullet> bullets = new CopyOnWriteArrayList<>();
    // 敌方坦克
    public List<DpTank> tanks = new CopyOnWriteArrayList<>();
    // 初始化主战坦克
    public DpTank myTank = new DpTank(DpTank.INIT_X, DpTank.INIT_Y, false, DpTank.INIT_DIR, DpGroup.GOOD, this);
    // 批量爆炸
    public List<DpExplode> explodes = new CopyOnWriteArrayList<>();
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
        g.drawString("子弹的数量为" + bullets.size() + ", 主战坦克的坐标: (" + myTank.x + ", " + myTank.y + " ), 方向:" + myTank.dir.name(), 10, 60);
        g.drawString("敌方坦克数量: " + tanks.size(), 10, 80);
        g.drawString("爆炸的数量: " + tanks.size(), 10, 100);
        g.drawString("主战坦克的速度: " + myTank.speed, 10, 120);
        g.setColor(c);
        myTank.paint(g);
        if (!bullets.isEmpty()) {
            // 防止内存泄露
            for (DpBullet bullet : bullets) {
                bullet.paint(g);
            }
        }
        if (!tanks.isEmpty()) {
            for (int i = 0; i < tanks.size(); i++) {
                tanks.get(i).paint(g);
            }
        }
        if (!explodes.isEmpty()) {
            for (int i = 0; i < explodes.size(); i++) {
                explodes.get(i).paint(g);
            }
        }
        if (!bullets.isEmpty() && !tanks.isEmpty()) {
            for (int i = 0; i < bullets.size(); i++) {
                for (int j = 0; j < tanks.size(); j++) {
                    bullets.get(i).collideWith(tanks.get(j));
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
            // 1个建被按下去的时候调用
            //x = x + 10;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            String format = sdf.format(new Date());
            //System.out.println("datetime:" + format + ": key pressed: " + e.getKeyCode() + ", x: " + myTank.getX()
            // + ", y: " + myTank.getY());
            // repaint() 会默认调用1次 paint() 用户可以这样 但是敌人是自动移动的 所以不能都这么用
            // repaint();

            // 键按下去改变主战坦克方向
            setMainTankDir();

            // 键按下去会出现坦克移动音效
            //new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
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
