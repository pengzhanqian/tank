package com.qpz.tank;

import com.qpz.tank.enums.DirEnum;
import com.qpz.tank.enums.Group;
import com.qpz.tank.model.Bullet;
import com.qpz.tank.model.Tank;

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
public class TankFrame extends Frame {

    //    public static final int WIDTH = 2560;
//    public static final int HEIGHT = 1600;
    public static final int GAME_WIDTH = 1080;
    public static final int GAME_HEIGHT = 960;
    // 初始化子弹
    //public Bullet b = new Bullet(210, 255, DirEnum.DOWN);
    // 批量子弹 使用容器  使用CopyOnWriteArrayList等并发集合  防止多线程下1个线程遍历一个线程删除导致报错  java.util.ConcurrentModificationException
    public List<Bullet> bullets = new CopyOnWriteArrayList<>();
    // 敌方坦克
    public List<Tank> tanks = new CopyOnWriteArrayList<>();
    // 初始化主战坦克
    public Tank myTank = new Tank(200, 200, DirEnum.DOWN, Group.GOOD, this);
    // 批量爆炸
    public List<Explode> explodes = new CopyOnWriteArrayList<>();
    Image offScreenImage = null;


    public TankFrame() throws HeadlessException {
        this.setVisible(true);
        // 单位 像素
        this.setSize(GAME_WIDTH, GAME_HEIGHT);
        this.setResizable(false);
        this.setTitle("Tank War (坦克大战)");
        System.out.println("Tank War Window is init.");
        this.addWindowListener(new WindowAdapter() {
            // 关闭窗口
            public void windowClosing(WindowEvent e) {
                System.out.println("Tank War Window is closed");
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
            offScreenImage = this.createImage(WIDTH, HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, WIDTH, HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量为" + bullets.size() + ", 主战坦克的坐标: (" + myTank.getX() + ", " + myTank.getY() + " ), 方向:" + myTank.getDir().name(), 10, 60);
        g.drawString("敌方坦克数量: " + tanks.size(), 10, 80);
        g.drawString("集中爆炸的数量: " + tanks.size(), 10, 100);
        g.setColor(c);
        myTank.paint(g);
        if (!bullets.isEmpty()) {
            // 防止内存泄露
            for (Bullet bullet : bullets) {
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
            System.out.println("datetime:" + format + ": key pressed: " + e.getKeyCode() + ", x: " + myTank.getX() + ", y: " + myTank.getY());
            // repaint() 会默认调用1次 paint() 用户可以这样 但是敌人是自动移动的 所以不能都这么用
            // repaint();

            // 键按下去改变主战坦克方向
            setMainTankDir();

            // 键按下去会出现坦克移动音效
            new Thread(() -> new Audio("audio/tank_move.wav").play()).start();
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
                case KeyEvent.VK_SPACE:
                    // 坦克发出一颗子弹
                    myTank.fire();
                    break;
            }

            // 键抬起去改变主战坦克方向
            setMainTankDir();
        }


        private void setMainTankDir() {
            if (!bl && !bu && !br && !bd) {
                myTank.setMoving(false);
            } else {
                myTank.setMoving(true);
                if (bl) myTank.setDir(DirEnum.LEFT);
                if (bu) myTank.setDir(DirEnum.UP);
                if (br) myTank.setDir(DirEnum.RIGHT);
                if (bd) myTank.setDir(DirEnum.DOWN);
            }
        }
    }
}
