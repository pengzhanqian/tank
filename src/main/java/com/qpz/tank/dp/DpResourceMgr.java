package com.qpz.tank.dp;

import com.qpz.tank.single.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * 资源管理类
 *
 * @author pengzhan.qian
 * @since 2025/5/30 22:01
 **/
public class DpResourceMgr {

    public static BufferedImage goodTankL;
    public static BufferedImage goodTankU;
    public static BufferedImage goodTankR;
    public static BufferedImage goodTankD;
    public static BufferedImage badTankL;
    public static BufferedImage badTankU;
    public static BufferedImage badTankR;
    public static BufferedImage badTankD;
    public static BufferedImage bulletL;
    public static BufferedImage bulletU;
    public static BufferedImage bulletR;
    public static BufferedImage bulletD;
    public static BufferedImage[] explodes = new BufferedImage[16];

    static {
        try {
//            tankL = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankL.gif")));
//            tankU = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankU.gif")));
//            tankR = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankR.gif")));
//            tankD = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankD.gif")));
//            tankU = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png")));
//            tankL = ImageUtil.rotateImage(tankU, -90);
//            tankR = ImageUtil.rotateImage(tankU, 90);
//            tankD = ImageUtil.rotateImage(tankU, 180);

            goodTankU = ImageIO.read(Objects.requireNonNull(DpResourceMgr.class.getClassLoader().getResourceAsStream("images/GoodTank1.png")));
            goodTankL = ImageUtil.rotateImage(goodTankU, -90);
            goodTankR = ImageUtil.rotateImage(goodTankU, 90);
            goodTankD = ImageUtil.rotateImage(goodTankU, 180);

            badTankU = ImageIO.read(Objects.requireNonNull(DpResourceMgr.class.getClassLoader().getResourceAsStream("images/BadTank1.png")));
            badTankL = ImageUtil.rotateImage(badTankU, -90);
            badTankR = ImageUtil.rotateImage(badTankU, 90);
            badTankD = ImageUtil.rotateImage(badTankU, 180);

//            bulletL = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletL.gif")));
//            bulletU = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.gif")));
//            bulletR = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletR.gif")));
//            bulletD = ImageIO.read(Objects.requireNonNull(ResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletD.gif")));

            bulletU = ImageIO.read(Objects.requireNonNull(DpResourceMgr.class.getClassLoader().getResourceAsStream("images/bulletU.png")));
            bulletL = ImageUtil.rotateImage(bulletU, -90);
            bulletR = ImageUtil.rotateImage(bulletU, 90);
            bulletD = ImageUtil.rotateImage(bulletU, 180);

            for (int i = 0; i < explodes.length; i++) {
                explodes[i] = ImageIO.read(Objects.requireNonNull(DpResourceMgr.class.getClassLoader().getResourceAsStream("images/e" + (i + 1) + ".gif")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DpResourceMgr() {
    }

    public static DpResourceMgr getInstance() {
        return DpResourceMgrHolder.INSTANCE;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> System.out.println(DpResourceMgr.getInstance().hashCode())).start();
        }
    }

    private static class DpResourceMgrHolder {
        private static final DpResourceMgr INSTANCE = new DpResourceMgr();
    }
}
