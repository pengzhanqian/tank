//package com.qpz.tank.designpattern;
//
//import java.awt.*;
//
///**
// * @author pengzhan.qian
// * @since 2025/6/3 9:50
// **/
//public class DpExplode {
//
//    public static final int EXPLODE_WIDTH = DpResourceMgr.explodes[0].getWidth();
//    public static final int EXPLODE_HEIGHT = DpResourceMgr.explodes[0].getHeight();
//    // 爆炸图片的位置
//    public int x;
//    public int y;
//    public DpTankFrame tf;
//    // 爆炸图片的进度
//    private int step = 0;
//
//    public DpExplode(int x, int y, DpTankFrame tf) {
//        this.x = x;
//        this.y = y;
//        this.tf = tf;
//        // 爆炸音效
//        new Thread(() -> new DpAudio("audio/explode.wav").play()).start();
//    }
//
//    public void paint(Graphics g) {
//        g.drawImage(DpResourceMgr.explodes[step++], x, y, null);
//        if (step >= DpResourceMgr.explodes.length) {
//            step = 0;
//            tf.explodes.remove(this);
//        }
//    }
//}
